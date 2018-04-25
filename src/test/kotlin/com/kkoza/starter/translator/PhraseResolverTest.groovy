package com.kkoza.starter.translator

import spock.lang.Specification
import spock.lang.Unroll

class PhraseResolverTest extends Specification {

    def resolver = new PhraseResolver()

    @Unroll
    def 'should seperate emojis'() {
        when:
        def divided = resolver.convertEmojisInPhraseToEnglish(inputPhrase)

        then:
        divided == expected

        where:
        inputPhrase                              | expected
        "elo delo😃😃 ale😃 jest 😃😃 smiesznie" | "elo delo 😃 😃 ale 😃 jest 😃 😃 smiesznie"
        "elo⚽⚽co tam mi ⚽⚽ powiesz⚽ "            | "elo ⚽ ⚽ co tam mi ⚽ ⚽ powiesz ⚽"
    }
}
