package com.don.demo.common.configuration

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.h2.H2ConnectionOption
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Profile("h2")
@Configuration
class H2R2dbcConfiguration : AbstractR2dbcConfiguration() {
    override fun connectionFactory(): ConnectionFactory =
        H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .inMemory("testdb")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .username("sa")
                .build()
        )

    @Bean
    fun h2DbInitializer(): ConnectionFactoryInitializer {
        val resourceDatabasePopulator = ResourceDatabasePopulator()
        resourceDatabasePopulator.addScript(ClassPathResource("schema-h2.sql"))

        val initializer = ConnectionFactoryInitializer()
        initializer.setDatabasePopulator(resourceDatabasePopulator)
        initializer.setConnectionFactory(connectionFactory())
        return initializer
    }
}