package dev.study.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "simulation")
data class SimulationConfig(
    val count: Int = 1000,
    val chunkSize: Int = 100,
    val campaignCount: Int = 10,
)
