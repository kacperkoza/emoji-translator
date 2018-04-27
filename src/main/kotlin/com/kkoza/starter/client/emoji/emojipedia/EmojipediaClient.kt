package com.kkoza.starter.client.emoji.emojipedia

import com.kkoza.starter.client.emoji.EmojiClient
import com.sun.istack.internal.logging.Logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class EmojipediaClient(
        @Qualifier("emojipediaWebClient") private val webClient: WebClient,
        @Value("\${emojipediaClient.url}") private val url: String
) : EmojiClient {

    companion object {
        private val logger = Logger.getLogger(EmojipediaClient::class.java)
    }

    override fun getEnglishSentenceFromEmoji(emoji: String): Mono<String> {
        logger.info("Get emoji translation for ${emoji}")
        return webClient.get()
                .uri("https://emojipedia.org/${emoji[0]}${emoji[1]}/")
                .exchange()
                .flatMap {
                    it.bodyToMono(String::class.java) }
    }

}