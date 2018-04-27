package com.kkoza.starter.client.emoji

import com.kkoza.starter.BaseIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Ignore

class EmojipediaClientTest extends BaseIntegrationTest {

    @Autowired
    EmojiClient emojiClient

    def "get english phrase for grapes emoji"() {
        given:
        def grapes = 'gra'
        stubEmojipedia(200, grapes, 'grapes.html')

        expect:
        emojiClient.getEnglishSentenceFromEmoji(grapes).block() == 'Grapes'
    }

    @Ignore //TODO throw exception ?
    def 'should throw EmojipediaClientException when emojipedia returns status 500'() {

    }

}
