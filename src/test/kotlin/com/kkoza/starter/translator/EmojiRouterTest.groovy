package com.kkoza.starter.translator

import com.kkoza.starter.BaseIntegrationTest
import org.springframework.http.MediaType

class EmojiRouterTest extends BaseIntegrationTest {

    def 'should translate 🍇 to polish'() {
        given:
        stubGoogleTranslate(200, 'grapes', 'grapes.json')
        def emoji = "🍇"
        def countryCode = 'pl'

        expect:
        webTestClient.get().uri("/translator?phrase=$emoji&countryCode=$countryCode")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).consumeWith { it -> assert it.body == 'winogrona' }
    }

}
