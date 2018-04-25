package com.kkoza.starter.translator

import com.kkoza.starter.client.emoji.EmojiClient
import com.kkoza.starter.client.translator.googletranslate.GoogleTranslateClient
import emoji4j.EmojiUtils
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class EmojiService(
        private val translateClient: GoogleTranslateClient,
        private val emojiClient: EmojiClient,
        private val phraseResolver: PhraseResolver
) {

    fun getTranslationFor(sentence: String, countryCode: CountryCode): Mono<String> {
        val emoji = phraseResolver.convertEmojisInPhraseToEnglish(sentence)
        return emojiClient.getEnglishSentenceFromEmoji(sentence)
    }

}