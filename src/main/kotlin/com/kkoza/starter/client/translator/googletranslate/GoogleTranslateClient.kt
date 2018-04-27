package com.kkoza.starter.client.translator.googletranslate

import com.kkoza.starter.client.translator.TranslatorClient
import com.kkoza.starter.translator.CountryCode
import com.sun.istack.internal.logging.Logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class GoogleTranslateClient(
        @Qualifier("googleTranslateWebClient") private val webClient: WebClient,
        @Value("\${googleTranslateClient.url}") private val url: String
) : TranslatorClient {

    companion object {
        private val logger = Logger.getLogger(GoogleTranslateClient::class.java)
        private const val QUERY = "q"
        private const val SOURCE_LANG = "sl"
        private const val TARGET_LANG = "tl"
        private const val UNKNOWN = "client=gtx"
        private const val UNKNOWN_TWO = "dt=t"
        private const val USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
    }

    override fun translate(sentence: String, targetLanguage: CountryCode, sourceLanguage: CountryCode): Mono<String> {
        return webClient.get()
                .uri("$url/translate_a/single" +
                        "?$UNKNOWN" +
                        "&$SOURCE_LANG=${sourceLanguage.language}" +
                        "&$TARGET_LANG=${targetLanguage.language}" +
                        "&$UNKNOWN_TWO" +
                        "&$QUERY=${sentence.trim()}")
                .headers(addUserAgent())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(is5xxServerError(), googleTranslateClientException(sentence, targetLanguage, sourceLanguage))
                .bodyToMono(String::class.java)
                .map(subStringTranslation())

    }

    private fun addUserAgent(): (HttpHeaders) -> Unit {
        return {
            it.add(HttpHeaders.USER_AGENT, USER_AGENT_VALUE)
        }
    }

    private fun is5xxServerError(): (HttpStatus) -> Boolean = { it.is5xxServerError }

    private fun googleTranslateClientException(sentence: String, targetLanguage: CountryCode, sourceLanguage: CountryCode): (ClientResponse) -> Mono<Throwable> {
        return {
            logger.warning("Google translate server error")
            Mono.error(GoogleTranslateClientException(sentence, targetLanguage, sourceLanguage))
        }
    }

    private fun subStringTranslation(): (String) -> String {
        return {
            val startIndex = it.indexOf("\"") + 1
            val endIndex = it.indexOf("\",")
            it.substring(startIndex, endIndex)
        }
    }

}