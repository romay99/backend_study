package dev.study.batch

import arrow.core.Either
import dev.study.event.impression.ImpressionEvent
import net.datafaker.Faker
import org.springframework.batch.item.ItemReader
import java.time.Instant

open class ImpressionEventReader(
    private val totalCount: Int,
    private val campaignCount: Int,
) : ItemReader<ImpressionEvent> {
    private val faker = Faker()

    private val sequence =
        generateSequence {
            ImpressionEvent(
                campaignId = faker.random().nextInt(1, campaignCount + 1).toLong(),
                timestamp = Instant.now(),
                requestId = faker.internet().uuid(),
                sessionId = faker.internet().uuid(),
                userAgent = faker.internet().userAgent(),
                referrer = faker.internet().url(),
            )
        }.take(totalCount).iterator()

    override fun read(): ImpressionEvent? =
        Either
            .catch { sequence.next() }
            .getOrNull()
}
