package com.gamestore.gamestorebackendkotlin.auth.utils

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtUtils {
    @Qualifier("JwtKey")
    @Autowired
    private val secretKey: Key? = null

    fun getBody(token: String): Claims = getJwsClaims(token).body

    fun getSubject(token: String): String = getJwsClaims(token).body.subject

    @Throws(ValidationError::class)
    fun getJwsClaims(token: String): Jws<Claims> {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
        } catch (error: ValidationError) {
            throw ValidationError(ValidationProps.VALIDATION_MSG_TOKEN)
        }
    }
}
