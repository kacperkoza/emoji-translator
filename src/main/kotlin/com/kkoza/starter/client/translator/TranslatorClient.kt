package com.kkoza.starter.client.translator

import com.kkoza.starter.translator.CountryCode
import reactor.core.publisher.Mono

interface TranslatorClient {

    fun translate(
            sentence: String,
            targetLanguage: CountryCode = CountryCode.PL,
            sourceLanguage: CountryCode = CountryCode.EN
    ): Mono<String>

}