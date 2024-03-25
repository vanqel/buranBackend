package com.gamestore.gamestorebackendkotlin.balance.config

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.validation.annotation.Validated
@Validated
@ConfigurationProperties(prefix = "robokassa")
class RobokassaProps
    @ConstructorBinding
    constructor(
        @field:NotBlank
        @field:Size(min = 64)
        val login: String,
        @field:Positive
        val password1: String,
        @field:Positive
        val password2: String,
    )


