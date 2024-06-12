package com.buran.core.seasone.core.dto

import java.time.LocalDate

data class SeasonOutput(
    val dateStart: LocalDate,
    val dateEnd: LocalDate,
    val title: String
)
