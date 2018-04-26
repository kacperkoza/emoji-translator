package com.kkoza.starter.translator.provider

import emoji4j.EmojiUtils
import org.springframework.stereotype.Component

@Component
class Emoji4jProvider : EmojiTranslationProvider {

    override fun translateToEnglish(emoji: String): String? {
        val emojiAliases: List<String>? = EmojiUtils.getEmoji(emoji)?.aliases
        return emojiAliases?.get(0)
    }

}