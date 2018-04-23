package com.kkoza.starter.translator

import com.kkoza.starter.client.translator.GoogleTranslateClient
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class TranslateHandler(
        private val googleTranslateClient: GoogleTranslateClient
) {

    fun translate(request: ServerRequest): Mono<ServerResponse> {
        val phrase = request.queryParam("phrase").orElse(null)
        val countryCodeParam = request.queryParam("countryCode").orElse("pl")
        val countryCode = CountryCode.valueOf(countryCodeParam.toUpperCase())
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(googleTranslateClient.translate(phrase, countryCode))
    }


}