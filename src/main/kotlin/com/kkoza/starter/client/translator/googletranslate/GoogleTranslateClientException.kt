package com.kkoza.starter.client.translator.googletranslate

import com.kkoza.starter.translator.CountryCode

class GoogleTranslateClientException(sentence: String,
                                     targetLanguage: CountryCode,
                                     sourceLanguage: CountryCode
) : RuntimeException("There is a problem with google translate client " +
        "phrase = $sentence, " +
        "target language = $targetLanguage, " +
        "source language = $sourceLanguage")