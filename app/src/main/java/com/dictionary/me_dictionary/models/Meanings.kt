package com.dictionary.me_dictionary.models

data class Meanings (
    var partOfSpeech: String? = null,
    var definitions: List<Definitions>? = null
)