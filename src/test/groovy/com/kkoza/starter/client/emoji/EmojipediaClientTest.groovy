package com.kkoza.starter.client.emoji

import com.kkoza.starter.BaseIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Ignore

class EmojipediaClientTest extends BaseIntegrationTest {

    @Autowired
    EmojiClient emojiClient

    def "get english phrase for grapes emoji"() {
        given:
        def grapesEmoji = '🍇'
        stubEmojipediaForRedirect('%F0%9F%8D%87', 'grapes') // 🍇 is %F0%9F%8D%87 in bytes
        stubEmojipedia(200, 'grapes', 'grapes.html')

        expect:
        with(emojiClient.getEnglishSentenceFromEmoji(grapesEmoji).block()) {
            emoji == '🍇'
            english == 'Grapes'
            description == 'Grapes that are commonly found as red grapes (purple-colored) or ' +
                    'white grapes (green-colored). Used to create wine.'
        }
    }

    @Ignore //TODO: how to handle exceptions
    def 'should throw EmojipediaClientException when emojipedia returns status 5xx or 4xx'() {
    }

}
