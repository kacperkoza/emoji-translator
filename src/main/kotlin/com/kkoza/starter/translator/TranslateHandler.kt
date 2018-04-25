package com.kkoza.starter.translator

import com.kkoza.starter.client.translator.googletranslate.GoogleTranslateClient
import com.sun.istack.internal.logging.Logger
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

    companion object {
        private val logger = Logger.getLogger(GoogleTranslateClient::class.java)
    }

    fun translate(request: ServerRequest): Mono<ServerResponse> {
        val phrase = request.queryParam("phrase").orElse(null)
        val countryCodeParam = request.queryParam("countryCode").orElse("pl")
        val countryCode = CountryCode.valueOf(countryCodeParam.toUpperCase())
        logger.info("Get emoji for phrase = $phrase and countryCode = $countryCode")
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(googleTranslateClient.translate(phrase, countryCode))
    }

}