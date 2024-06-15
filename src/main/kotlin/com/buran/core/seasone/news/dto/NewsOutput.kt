package com.buran.core.seasone.news.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class NewsOutput (
    val id: Long,
    val title: String,
    val text: String,
    val photos: String?,
    val date: LocalDate
)
