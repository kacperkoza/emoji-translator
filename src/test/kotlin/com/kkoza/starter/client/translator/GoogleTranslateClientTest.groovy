package com.kkoza.starter.client.translator

import com.kkoza.starter.BaseIntegrationTest
import com.kkoza.starter.client.translator.googletranslate.GoogleTranslateClientException
import com.kkoza.starter.translator.CountryCode
import org.junit.Ignore
import org.springframework.beans.factory.annotation.Autowired

class GoogleTranslateClientTest extends BaseIntegrationTest {

    @Autowired
    TranslatorClient client

    def 'should return translation for given phrase and country codes'() {
        given:
        def phrase = "tv"
        stubGoogleTranslate(200, phrase, "tv.json")

        expect:
        client.translate(phrase, CountryCode.PL, CountryCode.EN).block() == 'telewizja'
    }

    @Ignore //TODO: throw exception (?)
    def 'should throw google translate client exception when service return 500 status'() {
        given:
        stubGoogleTranslate(500, 'phrase', "tv.json")

        when:
        client.translate('phrase', CountryCode.PL, CountryCode.EN).block()

        then:
        thrown(GoogleTranslateClientException.class)
    }

}