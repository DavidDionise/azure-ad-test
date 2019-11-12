package com.example.azureadtest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class Router(
    val handler: Handler
) {
    @Bean
    fun routes(): RouterFunction<ServerResponse> = router {
        GET("/auth", handler::auth)
    }
}