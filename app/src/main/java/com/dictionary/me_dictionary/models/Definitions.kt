package com.dictionary.me_dictionary.models

data class Definitions(
    var definition: String? = null,
    var example: String? = null,
    var synonyms: List<String>? = null,
    var antonyms: List<String>? = null
)