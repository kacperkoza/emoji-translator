package com.kkoza.starter.translator

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunctions.route

@Configuration
class EmojiRouter {

    @Bean
    fun translateRouter(translateHandler: TranslateHandler) = route(
            GET("/translator"),
            HandlerFunction { translateHandler.translate(it) })

}


class MissingPhraseException : RuntimeException("Phrase needs to be provided")