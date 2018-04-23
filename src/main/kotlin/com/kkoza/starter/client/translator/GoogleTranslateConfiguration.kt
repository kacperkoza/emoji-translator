package com.kkoza.starter.client.translator

import io.netty.channel.ChannelOption
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.ipc.netty.resources.PoolResources

@Configuration
class GoogleTranslateConfiguration {

    @Bean
    fun googleTranslateWebClient(
            @Value("\${googleTranslateClient.connectionTimeout}") connectionTimeout: Int,
            @Value("\${googleTranslateClient.socketTimeout}") socketTimeout: Int,
            @Value("\${googleTranslateClient.maxConnectionsPerRoute}") maxConnectionsPerRoute: Int,
            @Value("\${googleTranslateClient.maxConnections}") maxConnections: Int,
            @Value("\${googleTranslateClient.url}") url: String

    ): WebClient {
        val connector = ReactorClientHttpConnector({
            it
                    .option(ChannelOption.SO_TIMEOUT, socketTimeout)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                    .poolResources(PoolResources.fixed("pool", maxConnections))
        })
        return WebClient.builder()
                .baseUrl("https://translate.googleapis.com/translate_a/single")
                .clientConnector(connector)
                .build()
    }


}