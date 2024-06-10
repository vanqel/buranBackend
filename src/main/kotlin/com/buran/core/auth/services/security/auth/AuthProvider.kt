package com.buran.core.auth.services.security.auth

import org.springframework.security.core.Authentication

interface AuthProvider {
    fun getAuthentication(token: String): Authentication?
}
