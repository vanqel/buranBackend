package com.gamestore.gamestorebackendkotlin.extensions

import com.gamestore.gamestorebackendkotlin.auth.config.SecurityProperties
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse

fun HttpServletResponse.setTokens(
    access: String,
    refresh: String,
) {
    this.addHeader(SecurityProperties.REFRESH_HEADER_STRING, refresh)
    this.addHeader(SecurityProperties.HEADER_STRING, SecurityProperties.TOKEN_PREFIX_ACCESS + access)

    this.addCookie(Cookie(SecurityProperties.ACCESS_COOKIE_STRING, access))
    this.addCookie(Cookie(SecurityProperties.REFRESH_COOKIE_STRING, refresh))
}
