package com.kkoza.starter.client.emoji.emojipedia

class EmojipediaClientException(emoji: String) : RuntimeException("Problem with emijepdia client. Emoji = $emoji")