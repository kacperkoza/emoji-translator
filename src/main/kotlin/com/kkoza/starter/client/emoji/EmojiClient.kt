package com.kkoza.starter.client.emoji

import com.kkoza.starter.translator.Emoji
import reactor.core.publisher.Mono

interface EmojiClient {

    fun getEnglishSentenceFromEmoji(emoji: String): Mono<Emoji>

}