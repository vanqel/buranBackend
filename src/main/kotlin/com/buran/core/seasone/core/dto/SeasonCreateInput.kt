package com.buran.core.seasone.core.dto

import java.time.LocalDate

data class SeasonCreateInput(
    val dateStart: LocalDate,
    val dateEnd: LocalDate
)
