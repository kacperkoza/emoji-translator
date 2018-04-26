package com.kkoza.starter.translator.provider

interface EmojiTranslationProvider {

    fun translateToEnglish(emoji: String): String?

}