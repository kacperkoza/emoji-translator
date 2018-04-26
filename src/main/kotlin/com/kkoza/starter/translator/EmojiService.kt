package com.kkoza.starter.translator

import com.kkoza.starter.client.translator.googletranslate.GoogleTranslateClient
import com.kkoza.starter.translator.provider.EmojiTranslationProvider
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class EmojiService(
        private val translateClient: GoogleTranslateClient,
        private val phraseResolver: EmojiSentenceSeparator,
        emojiTranslationProviders: List<EmojiTranslationProvider>
) {

    private val providers = Flux.fromIterable(emojiTranslationProviders)

    fun getTranslationFor(sentence: String, countryCode: CountryCode): Flux<String> {
        return Flux
                .fromIterable(phraseResolver.seperateEmojisWithSpaces(sentence))
                .concatMap(emojiToEnglish())
                .concatMap(translateToLanguage(countryCode))
                .doOnNext { println(it) }
    }

    private fun emojiToEnglish(): (String) -> Flux<String> {
        return {
            providers
                    .flatMap { provider ->
                        Mono.fromCallable {
                            provider.translateToEnglish(it)?.replace("_", " ") ?: it
                        }
                    }
                    .concatMap { Mono.just("$it ") }
                    .take(1)
        }
    }

    private fun translateToLanguage(countryCode: CountryCode): (String) -> Mono<String> {
        return {
            if (countryCode != CountryCode.EN) translateClient.translate(it, countryCode) else it.toMono()
        }
    }

}
