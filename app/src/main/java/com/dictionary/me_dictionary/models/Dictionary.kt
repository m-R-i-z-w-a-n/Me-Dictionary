package com.dictionary.me_dictionary.models

data class Dictionary (
    var word: String? = null,
    var phonetic: String? = null,
    var phonetics: List<Phonetics>? = null,
    var origin: String? = null,
    var meanings: List<Meanings>? = null
)