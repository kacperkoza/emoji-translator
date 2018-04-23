package com.kkoza.starter

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = ["integration"])
class BaseIntegrationTest extends Specification {

    @Autowired
    WebTestClient webTestClient

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate

    @Rule
    public WireMockRule translateGoogleRule = new WireMockRule(8089)


    def stub
}