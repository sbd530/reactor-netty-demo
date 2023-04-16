package com.don.demo.common.netty

import com.don.demo.business.AuthorizationRepository
import com.don.demo.common.SERVER_HOST
import com.don.demo.common.SERVER_PORT
import io.netty.handler.codec.LineBasedFrameDecoder
import io.netty.handler.logging.LogLevel
import mu.KotlinLogging
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.netty.tcp.TcpServer
import reactor.netty.transport.logging.AdvancedByteBufFormat
import kotlin.text.Charsets.UTF_8

private val logger = KotlinLogging.logger {}

@Component
class DemoServer(
    private val authorizationRepository: AuthorizationRepository
) {
    fun start() {
        val tcpServer = createTcpServer()
        /**
         * warmup() : Initialize and load the event loop groups,
         * the native transport libraries and the native libraries for the security.
         */
        tcpServer.warmup().block()

        /**
         * bindNow() : Starts the server in a blocking fashion and waits for it to finish initializing.
         */
        val server = tcpServer.bindNow()

        server.onDispose().block()
    }

    private fun createTcpServer() = TcpServer.create()
        .host(SERVER_HOST)
        .port(SERVER_PORT)
        .wiretap("hex-dump-logger", LogLevel.INFO, AdvancedByteBufFormat.HEX_DUMP)
        .doOnBind { logger.info { "Server bind" } }
        .doOnConnection { connection ->
            connection.addHandlerLast(LineBasedFrameDecoder(1024))
            connection.onReadIdle(50_000L) {
                logger.warn { "read time out" }
                connection.dispose()
            }
        }
        .handle { inbound, outbound ->
            inbound.receive()
                .asString(UTF_8)
                .map {
                    "Server received message: $it"
                }
                .flatMap { outbound.sendString(Mono.just(it), UTF_8) }
                .then()
        }
}