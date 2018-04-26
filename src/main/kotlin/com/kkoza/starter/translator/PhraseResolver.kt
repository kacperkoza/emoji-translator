package com.kkoza.starter.translator

import com.vdurmont.emoji.EmojiManager
import org.springframework.stereotype.Component

@Component
class PhraseResolver {

    fun convertEmojisInPhraseToEnglish(phrase: String): String {
        val words = phrase.split(" ")
        val builder = StringBuilder()
        words.forEach { word ->
            var i = 0
            while (i < word.length) {
                val byte = word.getByte(i)
                if (isEmoji(byte)) {
                    builder.appendWithLeadingSpaceForNonZeroIndex(i, byte)
                    i++
                    continue
                } else if (word.isLastIndex(i)) {
                    builder.append(byte)
                    break
                } else {
                    val twoBytes = word.getTwoBytes(i)
                    if (isEmoji(twoBytes)) {
                        builder.appendWithLeadingSpaceForNonZeroIndex(i, twoBytes)
                        i += 2
                    } else {
                        if (builder.isLastEmoji(i)) {
                            builder.appendWithLeadingSpace(word[i].toString())
                        } else {
                            builder.append(word[i])
                        }
                        i++
                    }
                }
            }
            builder.append(" ")
        }
        return builder.trim().toString()
    }

    private fun StringBuilder.isLastEmoji(i: Int): Boolean {
        if (i in 0..2) return false
        return EmojiManager.isEmoji(this.substring(this.length - 2, this.length)) //for 2 byte
                || EmojiManager.isEmoji(this.substring(this.length - 1, this.length)) // for 1 byte

    }

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

    private fun StringBuilder.appendWithLeadingSpace(string: String) {
        this.append(" $string")
    }

}


