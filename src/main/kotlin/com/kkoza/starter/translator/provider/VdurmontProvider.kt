package com.kkoza.starter.translator.provider

import com.vdurmont.emoji.EmojiManager
import org.springframework.stereotype.Component

@Component
class VdurmontProvider : EmojiTranslationProvider {

    override fun translateToEnglish(emoji: String): String? {
        val emojiAliases: List<String>?  = EmojiManager.getForAlias(emoji)?.aliases
        return emojiAliases?.get(0)
    }

}