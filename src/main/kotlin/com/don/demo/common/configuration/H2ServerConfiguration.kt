package com.don.demo.common.configuration

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
@Profile("local")
class H2ServerConfiguration {
    @Value("\${h2.console.port}")
    private lateinit var port: String
    private lateinit var h2WebServer: org.h2.tools.Server

    @EventListener(ContextRefreshedEvent::class)
    fun start() {
        h2WebServer = org.h2.tools.Server.createWebServer("-webPort", port).start()
        logger.info { "H2 Console running on localhost:$port" }
    }
    @EventListener(ContextClosedEvent::class)
    fun stop() {
        h2WebServer.stop()
        logger.info { "H2 Console stopped" }
    }
}