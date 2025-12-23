package dev.study.config

import dev.study.batch.ImpressionEventReader
import dev.study.batch.ImpressionEventWriter
import dev.study.event.impression.ImpressionEvent
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableConfigurationProperties(SimulationConfig::class)
class BatchConfiguration(
    private val config: SimulationConfig,
) {
    companion object {
        private const val STEP_NAME = "impressionEventStep"
        private const val JOB_NAME = "impressionEventJob"
    }

    @Bean
    @StepScope
    fun impressionEventReader(): ImpressionEventReader =
        ImpressionEventReader(
            totalCount = config.count,
            campaignCount = config.campaignCount,
        )

    @Bean
    @StepScope
    fun impressionEventWriter(kafkaTemplate: KafkaTemplate<String, ImpressionEvent>): ImpressionEventWriter =
        ImpressionEventWriter(kafkaTemplate)

    @Bean
    fun impressionEventStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        reader: ImpressionEventReader,
        writer: ImpressionEventWriter,
    ): Step =
        StepBuilder(STEP_NAME, jobRepository)
            .chunk<ImpressionEvent, ImpressionEvent>(config.chunkSize, transactionManager)
            .reader(reader)
            .writer(writer)
            .build()

    @Bean
    fun impressionEventJob(
        jobRepository: JobRepository,
        step: Step,
    ): Job =
        JobBuilder(JOB_NAME, jobRepository)
            .incrementer(RunIdIncrementer())
            .start(step)
            .build()
}
