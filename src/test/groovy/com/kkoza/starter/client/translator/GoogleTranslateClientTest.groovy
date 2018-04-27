package com.kkoza.starter.client.translator

import com.kkoza.starter.BaseIntegrationTest
import com.kkoza.starter.client.translator.googletranslate.GoogleTranslateClientException
import com.kkoza.starter.translator.CountryCode
import org.springframework.beans.factory.annotation.Autowired

class GoogleTranslateClientTest extends BaseIntegrationTest {

    @Autowired
    TranslatorClient client

    def 'should return translation for given phrase and country codes'() {
        given:
        stubGoogleTranslate(200, 'tv', "tv.json")
        Thread.sleep(1000)

        expect:
        client.translate('tv', CountryCode.PL, CountryCode.EN).block() == 'telewizja'
    }

    def 'should throw "GoogleTranslateClientException" when service return 500 status'() {
        given:
        stubGoogleTranslate(500, 'tv', 'error.json')

        when:
        client.translate('tv', CountryCode.PL, CountryCode.EN).block()

        then:
        thrown(GoogleTranslateClientException.class)
    }

}