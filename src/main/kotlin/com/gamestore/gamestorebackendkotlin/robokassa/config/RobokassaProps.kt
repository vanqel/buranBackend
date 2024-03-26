package com.gamestore.gamestorebackendkotlin.robokassa.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "robokassa")
class RobokassaProps
    @ConstructorBinding
    constructor(
        @field:NotBlank
        val login: String,
        @field:NotBlank
        val password1: String,
        @field:NotBlank
        val password2: String,
        @field:NotBlank
        val isTest: String,
    )
