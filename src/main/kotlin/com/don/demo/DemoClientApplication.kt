package com.don.demo

import com.don.demo.common.SERVER_HOST
import com.don.demo.common.SERVER_PORT
import reactor.core.publisher.Mono
import reactor.netty.tcp.TcpClient
import kotlin.text.Charsets.UTF_8


fun main() {
    DemoClient().start()
}

class DemoClient {
    fun start() {
        val tcpClient = TcpClient.create()
            .host(SERVER_HOST)
            .port(SERVER_PORT)
            .handle { inbound, outbound ->
                outbound.sendString(Mono.just("payload"), UTF_8)

                inbound.receive()
                    .asString(UTF_8)
                    .doOnNext {
                        println("Client received: it")
                    }
                    .then()
            }

        tcpClient.warmup().block()

        val connection = tcpClient.connectNow()


        connection.onDispose().block()
    }
}
