package com.don.demo.business

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table(name = "authorizations")
class Authorization(
    @Id
    val id: Long,
    val currency: String,
    val amount: BigDecimal
)