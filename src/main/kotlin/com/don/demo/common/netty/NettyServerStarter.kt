package com.don.demo.common.netty

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class NettyServerStarter(
    private val demoServer: DemoServer
) : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        demoServer.start()
    }
}