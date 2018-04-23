package com.kkoza.starter.client.translator

import com.kkoza.starter.BaseIntegrationTest
import com.kkoza.starter.translator.CountryCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

import static com.github.tomakehurst.wiremock.client.WireMock.*

class GoogleTranslateClientTest extends BaseIntegrationTest {

    @Autowired
    TranslatorClient client

    def 'should return translation for given phrase and country codes'() {
        def phrase = "tv"
        given:
        translateGoogleRule.stubFor(
                get(urlPathMatching("/translate_a/single"))
                        .withHeader(HttpHeaders.USER_AGENT, matching(".*"))
                        .withQueryParam('q', equalTo('tv'))
                        .withQueryParam('sl', equalTo('en'))
                        .withQueryParam('tl', equalTo('pl'))
                        .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                                .withBody('[[["telewizja","tv",null,null,2]],null,"en"]'))
        )

        expect:
        client.translate(phrase, CountryCode.PL, CountryCode.EN).block() == 'telewizja'

    }
}
