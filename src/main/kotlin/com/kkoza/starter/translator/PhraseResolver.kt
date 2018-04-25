package com.kkoza.starter.translator

import com.vdurmont.emoji.EmojiManager
import emoji4j.EmojiUtils
import org.springframework.stereotype.Component

@Component
class PhraseResolver {

    fun convertEmojisInPhraseToEnglish(phrase: String): String {
        val words = phrase.split(" ")
        val builder = StringBuilder()
        words.forEachIndexed { index, word ->
            var emojiFound = false
            for (i in 0 until word.length) {
                var i = if (emojiFound) {
                    emojiFound = false
                    val new = i + 1
                    if (new == word.length - 2 || new == word.length - 1) continue else new
                } else {
                    i
                }
                if (i > word.length - 1) continue
                val twoBytes = if (i + 2 <= word.length) word.substring(i, i + 2) else word.substring(i - 2, i - 1)
                if (EmojiManager.isEmoji(twoBytes)) {
                    if (i == 0) builder.append(twoBytes) else builder.append(" $twoBytes")
                    emojiFound = true
                } else {
                    builder.append(word[i])
                }

            }
            builder.append(" ")
        }
        return builder.trim().toString()
    }
}