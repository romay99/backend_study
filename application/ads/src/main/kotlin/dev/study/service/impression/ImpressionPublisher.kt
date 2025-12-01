package dev.study.service.impression

import dev.study.event.impression.ImpressionEvent
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ImpressionPublisher(
    private val kafkaTemplate: KafkaTemplate<String, ImpressionEvent>,
) {
    private val queue: BlockingQueue<ImpressionEvent> = ArrayBlockingQueue(QUEUE_CAPACITY)
    private val running = AtomicBoolean(false)
    private lateinit var batchProcessor: Thread

    companion object {
        private const val QUEUE_CAPACITY = 10_000
        private const val BATCH_SIZE = 1_000
        private const val BATCH_TIMEOUT_MS = 5_000L
        private const val POLL_TIMEOUT_MS = 100L
        private const val SHUTDOWN_TIMEOUT_MS = 10_000L
        private const val TOPIC = "ads.v1.banner.impressions"
    }

    @PostConstruct
    fun start() {
        running.set(true)
        batchProcessor = Thread({ processBatches() }, "impression-batch-processor")
        batchProcessor.start()
    }

    @PreDestroy
    fun stop() {
        running.set(false)
        batchProcessor.interrupt()
        try {
            batchProcessor.join(SHUTDOWN_TIMEOUT_MS)
        } catch (_: InterruptedException) {
        }
        flushRemaining()
    }

    fun publish(event: ImpressionEvent) {
        queue.offer(event)
    }

    private fun processBatches() {
        val batch = ArrayList<ImpressionEvent>(BATCH_SIZE)
        var lastSendTime = System.currentTimeMillis()

        while (running.get() || !queue.isEmpty()) {
            try {
                val event = queue.poll(POLL_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                if (event != null) {
                    batch.add(event)
                }

                val now = System.currentTimeMillis()
                val timeoutReached = (now - lastSendTime) >= BATCH_TIMEOUT_MS
                val batchFull = batch.size >= BATCH_SIZE

                if (batch.isNotEmpty() && (batchFull || timeoutReached)) {
                    sendBatch(batch)
                    batch.clear()
                    lastSendTime = now
                }
            } catch (_: InterruptedException) {
                if (!running.get()) break
            } catch (_: Exception) {
            }
        }

        if (batch.isNotEmpty()) {
            sendBatch(batch)
        }
    }

    private fun sendBatch(batch: List<ImpressionEvent>) {
        try {
            batch.forEach { event ->
                kafkaTemplate.send(TOPIC, event.campaignId.toString(), event)
            }
        } catch (_: Exception) {
        }
    }

    private fun flushRemaining() {
        val remaining = mutableListOf<ImpressionEvent>()
        queue.drainTo(remaining)
        if (remaining.isNotEmpty()) {
            sendBatch(remaining)
        }
    }
}
