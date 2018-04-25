package com.kkoza.starter.client.emoji.emojipedia

import com.kkoza.starter.client.emoji.EmojiClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class EmojipediaClient(
        @Qualifier("emojipediaWebClient") private val webClient: WebClient,
        @Value("\${emojipediaClient.url}") private val url: String
) : EmojiClient {

    override fun getEnglishSentenceFromEmoji(emoji: String): Mono<String> {
        return webClient.get()
                .uri { it.path("/$emoji/").build() }
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .flatMap { it.bodyToMono(String::class.java) }
                .map { fetchEmojiDesc(it) }
    }

    private fun fetchEmojiDesc(it: String): String {
        return it
    }

}