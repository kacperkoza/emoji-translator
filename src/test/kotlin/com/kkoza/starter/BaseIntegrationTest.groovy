package com.kkoza.starter

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = ["integration"])
class BaseIntegrationTest extends Specification {

    @Autowired
    WebTestClient webTestClient

    @Rule
    public WireMockRule translateGoogleRule = new WireMockRule(8089)

    @Rule
    public WireMockRule emojipediaRule = new WireMockRule(8091)

    def stubGoogleTranslate() {
        stubGoogleTranslate(200, "tv", "tv.json")
    }

    def stubGoogleTranslate(int statusCode, String phrase, String bodyFile) {
        translateGoogleRule.stubFor(
                get(urlPathMatching("/translate_a/single"))
                        .withHeader(HttpHeaders.USER_AGENT, matching(".*"))
                        .withQueryParam('q', equalTo(phrase))
                        .withQueryParam('sl', equalTo('en'))
                        .withQueryParam('tl', equalTo('pl'))
                        .withQueryParam('client', equalTo('gtx'))
                        .withQueryParam('dt', equalTo('t'))
                        .willReturn(
                        aResponse()
                                .withStatus(statusCode)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("google-translate/$bodyFile"))
        )
    }

    def stubEmojipedia(int statusCode, String phrase, String bodyFile) {
        translateGoogleRule.stubFor(
                get(urlPathMatching("/$phrase/"))
                        .willReturn(
                        aResponse()
                                .withStatus(statusCode)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                                .withBodyFile("emojipedia/$bodyFile"))
        )
    }

}