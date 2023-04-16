package com.don.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@EnableR2dbcRepositories
@SpringBootApplication
class ReactorNettyDemoApplication

fun main(args: Array<String>) {
//    BlockHound.install()
    runApplication<ReactorNettyDemoApplication>(*args)
}
