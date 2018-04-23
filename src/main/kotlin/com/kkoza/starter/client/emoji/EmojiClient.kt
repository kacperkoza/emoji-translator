package com.kkoza.starter.client.emoji

import reactor.core.publisher.Mono

interface EmojiClient {

    fun getEnglishSentenceFromEmoji(emoji: String): Mono<String>

}