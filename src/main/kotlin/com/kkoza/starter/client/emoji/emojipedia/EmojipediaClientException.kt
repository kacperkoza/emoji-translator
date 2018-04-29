package com.kkoza.starter.client.emoji.emojipedia

class EmojipediaClientException(
        emoji: String,
        statusCode: Int,
        body: String?

) : RuntimeException("Problem with emijepdia client. Emoji = $emoji, status code = $statusCode, body = $body")