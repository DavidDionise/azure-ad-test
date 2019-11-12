package com.example.azureadtest

import com.microsoft.aad.adal4j.AuthenticationCallback
import com.microsoft.aad.adal4j.AuthenticationContext
import com.microsoft.aad.adal4j.AuthenticationResult
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.concurrent.Executors

val TENANT_ID = System.getenv("TENANT_ID")
val CLIENT_ID = System.getenv("CLIENT_ID")

@Component
class Handler {
    fun auth(
        request: ServerRequest
    ): Mono<ServerResponse> {
        val username = request.queryParam("username").orElseThrow {
            Throwable("Must include a username")
        }
        val password = request.queryParam("password").orElseThrow {
            Throwable("Must include a password")
        }
        val authority = "https://login.microsoftonline.com/$TENANT_ID/"
        val service = Executors.newFixedThreadPool(1)
        val context = AuthenticationContext(authority, true, service)
        return Mono.just(
            context.acquireToken(
                CLIENT_ID,
                CLIENT_ID,
                username,
                password,
                object : AuthenticationCallback<AuthenticationResult> {
                    override fun onFailure(exc: Throwable?) {
                        println(">> ERROR: $exc")
                    }

                    override fun onSuccess(result: AuthenticationResult?) {
                        println(">> SUCCESS: ${result?.accessToken}")
                    }
                }
            ).get()
        )
            .flatMap { authResult ->
                ServerResponse
                    .ok()
                    .syncBody(Auth(accessToken = authResult.accessToken))
            }
    }
}