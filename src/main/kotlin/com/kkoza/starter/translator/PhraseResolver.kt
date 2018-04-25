package com.kkoza.starter.translator

import com.vdurmont.emoji.EmojiManager
import org.springframework.stereotype.Component

@Component
class PhraseResolver {

    fun convertEmojisInPhraseToEnglish(phrase: String): String {
        val words = phrase.split(" ")
        val builder = StringBuilder()
        words.forEach { word ->
            var emojiFound = false
            for (i in 0 until word.length) {
                var i = if (emojiFound) {
                    emojiFound = false
                    val new = i + 1
                    if (new == word.length - 2 || new == word.length - 1) continue else new
                } else {
                    i
                }
                if (indexExceededLength(i, word)) continue

                val byte = word.getByte(i)
                if (isEmoji(byte)) {
                    builder.appendWithLeadingSpaceForNonZeroIndex(i, byte)
                    continue
                } else {
                    if (word.isLastIndex(i)) {
                        builder.append(byte)
                        continue
                    }
                    val twoBytes = word.getTwoBytes(i)
                    if (isEmoji(twoBytes)) {
                        builder.appendWithLeadingSpaceForNonZeroIndex(i, twoBytes)
                        emojiFound = true
                    } else {
                        builder.append(word[i])
                    }
                }
            }
            builder.append(" ")
        }
        return builder.trim().toString()
    }

    private fun indexExceededLength(i: Int, word: String) = i > word.length - 1

    private fun isEmoji(string: String) = EmojiManager.isEmoji(string)

    private fun String.getByte(index: Int) = this.substring(index, index + 1)

    private fun String.getTwoBytes(index: Int) = this.substring(index, index + 2)

    private fun String.isLastIndex(index: Int) = this.length - 1 == index

    private fun StringBuilder.appendWithLeadingSpaceForNonZeroIndex(index: Int, string: String) {
        if (index == 0) {
            this.append(string)
        } else {
            this.append(" $string")
        }
    }

}
