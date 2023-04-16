package com.don.demo.business

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface AuthorizationRepository : ReactiveCrudRepository<Authorization, Long> {
    fun findByCurrency(currency: String): Flux<Authorization>
}