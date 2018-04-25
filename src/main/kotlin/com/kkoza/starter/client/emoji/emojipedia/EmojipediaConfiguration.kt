package com.kkoza.starter.client.emoji.emojipedia

import io.netty.channel.ChannelOption
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.ipc.netty.resources.PoolResources

@Configuration
class EmojipediaConfiguration {

    @Bean
    fun emojipediaWebClient(
            @Value("\${emojipediaClient.connectionTimeout}") connectionTimeout: Int,
            @Value("\${emojipediaClient.socketTimeout}") socketTimeout: Int,
            @Value("\${emojipediaClient.maxConnectionsPerRoute}") maxConnectionsPerRoute: Int,
            @Value("\${emojipediaClient.maxConnections}") maxConnections: Int,
            @Value("\${emojipediaClient.url}") url: String

    ): WebClient {
        val connector = ReactorClientHttpConnector({
            it
                    .option(ChannelOption.SO_TIMEOUT, socketTimeout)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                    .poolResources(PoolResources.fixed("pool", maxConnections))
        })
        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(connector)
                .build()
    }


}