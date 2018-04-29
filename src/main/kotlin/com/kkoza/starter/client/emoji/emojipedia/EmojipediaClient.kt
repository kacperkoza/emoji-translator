package com.kkoza.starter.client.emoji.emojipedia

import com.kkoza.starter.client.emoji.EmojiClient
import com.kkoza.starter.translator.Emoji
import com.sun.istack.internal.logging.Logger
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class EmojipediaClient(
        @Qualifier("emojipediaWebClient") private val webClient: WebClient
) : EmojiClient {

    companion object {
        private val logger = Logger.getLogger(EmojipediaClient::class.java)
    }

    override fun getEnglishSentenceFromEmoji(emoji: String): Mono<Emoji> {
        logger.info("Get emoji translation for $emoji")
        val location = webClient.get()
                .uri { it.path("/$emoji").build() }
                .exchange()
                .map(getLocationHeader())


        return location.flatMap { location ->
            webClient.get()
                    .uri { it.path("/$location").build() }
                    .exchange()
                    .flatMap { it.bodyToMono(String::class.java) }
                    .map(parseEmojiFromHtml(emoji, location))
        }
    }

    private fun getLocationHeader(): (ClientResponse) -> String {
        return {
            it.headers().asHttpHeaders()[HttpHeaders.LOCATION]!!.first()
        }
    }

    private fun parseEmojiFromHtml(emoji: String, location: String): (String) -> Emoji {
        return {
            val document = Jsoup.parse(it)
            val element = document.select(".description > p").first()
            Emoji(emoji, location.mapToText(), element.text())
        }
    }

    private fun String.mapToText(): String {
        val replacedDashes = this.replace("-", " ").replace("/", "")
        return replacedDashes.substring(0, 1).toUpperCase() + replacedDashes.substring(1)
    }

}


