package com.buran.core.seasone.core.dto

import java.time.LocalDate

data class SeasonOutput(
    val id: Long,
    private val dateStart: LocalDate,
    private val dateEnd: LocalDate
)
