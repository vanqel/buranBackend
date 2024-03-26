package com.gamestore.gamestorebackendkotlin.extensions

import org.springframework.http.MediaType

fun MediaType.isImage() = this == MediaType.IMAGE_JPEG || this == MediaType.IMAGE_PNG
