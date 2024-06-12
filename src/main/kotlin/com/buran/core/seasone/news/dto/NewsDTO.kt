package com.buran.core.seasone.news.dto

import java.time.LocalDateTime

data class NewsDTO(
    val title: String,
    val text: String,
    val image: List<String?>,
    val date: LocalDateTime
)