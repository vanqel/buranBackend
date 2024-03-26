package com.gamestore.gamestorebackendkotlin.auth.services.token

import com.gamestore.gamestorebackendkotlin.auth.config.SecurityProperties
import com.gamestore.gamestorebackendkotlin.auth.filter.TokenProps
import com.gamestore.gamestorebackendkotlin.auth.utils.JwtUtils
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Duration
import java.time.Instant
import java.util.Date

@Component
class TokenProviderImpl(
    private val securityProperties: SecurityProperties,
    private val jwtUtils: JwtUtils,
) : TokenProvider {
    companion object : Logging

    @Qualifier("JwtKey")
    @Autowired
    private val signInKey: Key? = null

    override fun createAccessToken(authentication: Authentication): String {
        val authClaims =
            authentication.authorities.map {
                it.toString()
            }

        val expiration =
            Date.from(
                Instant.now().plus(Duration.ofMinutes(securityProperties.expirationTime)),
            )

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(TokenProps.TYPE, TokenProps.ACCESS_TOKEN)
            .claim("auth", authClaims)
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun createRefreshToken(authentication: Authentication): String {
        val expiration =
            Date.from(
                Instant.now().plus(Duration.ofMinutes(securityProperties.expirationTimeRefresh)),
            )

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(TokenProps.TYPE, TokenProps.REFRESH_TOKEN)
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun updatedAccessToken(token: String): String {
        val claims = jwtUtils.getBody(token)
        val expiration =
            Date.from(
                Instant.now().plus(Duration.ofMinutes(securityProperties.expirationTime)),
            )
        return Jwts.builder()
            .setSubject(claims.subject)
            .addClaims(claims)
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun updatedRefreshToken(token: String): String {
        val claims = jwtUtils.getBody(token)
        val expiration =
            Date.from(
                Instant.now().plus(Duration.ofMinutes(securityProperties.expirationTimeRefresh)),
            )
        return Jwts.builder()
            .setSubject(claims.subject)
            .addClaims(claims)
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }
}
