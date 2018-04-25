package com.kkoza.starter.translator

import com.sun.istack.internal.logging.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class TranslateHandler(
        private val emojiService: EmojiService
) {

    companion object {
        private val logger = Logger.getLogger(TranslateHandler::class.java)
    }

    fun translate(request: ServerRequest): Mono<ServerResponse> {
        val phrase = readPhrase(request)
        val countryCode = readCountryCode(request)
        logger.info("Get emoji for phrase = $phrase and countryCode = $countryCode")
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(emojiService.getTranslationFor(phrase, countryCode))
    }

    private fun readCountryCode(request: ServerRequest): CountryCode {
        val countryCodeParam = request.queryParam("countryCode").orElse("pl")
        return CountryCode.valueOf(countryCodeParam.toUpperCase())
    }

    private fun readPhrase(request: ServerRequest) =
            request.queryParam("phrase").orElse(null)

}