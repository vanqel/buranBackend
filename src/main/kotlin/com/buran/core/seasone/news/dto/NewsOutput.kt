package com.buran.core.seasone.news.dto

data class NewsOutput (
    val title: String,
    val text: String,
    val photos: List<String?>
)
