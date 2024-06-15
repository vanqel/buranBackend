package com.buran.core.auth.config


import com.buran.core.auth.filter.JWTAuthorizationFilter
import com.buran.core.auth.services.security.app.AppAuthenticationManager
import com.buran.core.auth.services.security.auth.AuthProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebConfig(
    val authenticationManager: AppAuthenticationManager,
    val authProvider: AuthProvider,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors { }
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
//            .authorizeHttpRequests {
//                it
//                    .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
//                    .requestMatchers(HttpMethod.PUT).authenticated()
//                    .requestMatchers(HttpMethod.DELETE).authenticated()
//                    .requestMatchers(
//                        HttpMethod.GET,
//                        "/api/auth/me",
//                        "/api/token/validate",
//                        "/api/token/validateUser"
//                    ).authenticated()
//                    .requestMatchers(
//                        HttpMethod.GET,
//                        "/api/storage",
//                        "/api/seasons/news",
//                        "/api/seasons/news/{id_news}",
//                        "/api/seasons",
//                        "/api/seasons/matches",
//                        "/api/seasons/matches/{id_match}",
//                        "/api/seasons/matches/{id_match}/result",
//                        "/api/players",
//                        "/api/players/archived",
//                        "/api/players/{playerId}",
//                        "/api/seasons/stats",
//                        "/api/seasons/stats/match/{match_id}",
//                        "/api/seasons/stats/players",
//                        "/api/seasons/stats/{playerId}",
//                        "/api/seasons/stats/{playerId}/{idMatch}"
//                    ).permitAll()
//
//                    .anyRequest().authenticated()
//
//            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.GET, "/api/auth/me", "/api/token/validate", "/api/token/validateUser")
                    .authenticated()
                    .requestMatchers(HttpMethod.POST, "/api/auth/login")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .addFilter(
                JWTAuthorizationFilter(
                    authenticationManager,
                    authProvider,
                ),
            )
            .logout {
                it
                    .addLogoutHandler(SecurityContextLogoutHandler())
                    .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                    .permitAll()
            }
            .build()
    }
}
