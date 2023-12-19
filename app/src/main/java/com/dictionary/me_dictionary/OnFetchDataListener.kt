package com.dictionary.me_dictionary

import com.dictionary.me_dictionary.models.Dictionary

interface OnFetchDataListener {
    fun onFetchData(dictionary: Dictionary, message: String?)
    fun onError(message: String?)
}