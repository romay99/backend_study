package dev.study.batch

import dev.study.event.impression.Constants.IMPRESSION_EVENT_TOPIC
import dev.study.event.impression.ImpressionEvent
import org.slf4j.LoggerFactory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.kafka.core.KafkaTemplate

open class ImpressionEventWriter(
    private val kafkaTemplate: KafkaTemplate<String, ImpressionEvent>,
) : ItemWriter<ImpressionEvent> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun write(chunk: Chunk<out ImpressionEvent>) {
        val futures =
            chunk.items.map { event ->
                kafkaTemplate.send(IMPRESSION_EVENT_TOPIC, event.campaignId.toString(), event)
            }

        futures.forEach { future ->
            try {
                future.get()
            } catch (e: Exception) {
                logger.error("Failed to send impression event", e)
                throw e
            }
        }
    }
}
