package com.kkoza.starter.translator

import spock.lang.Specification
import spock.lang.Unroll

class EmojiSentenceSeparatorTest extends Specification {

    def separator = new EmojiSentenceSeparator()

    @Unroll
    def 'should seperate emojis close to other emoji/text with space'() {
        when:
        def divided = separator.seperateEmojisWithSpaces(inputPhrase)

        then:
        divided == expected

        where:
        inputPhrase                             | expected
        "elo😃delo😃😃 ale jest😃 😃 smiesznie"  | ["elo", "😃", "delo", "😃", "😃", "ale", "jest", "😃", "😃", "smiesznie"]
        "elo⚽⚽co tam mi ⚽⚽ powiesz⚽ "       | ["elo", "⚽", "⚽", "co", "tam", "mi", "⚽", "⚽", "powiesz", "⚽"]
    }

}
