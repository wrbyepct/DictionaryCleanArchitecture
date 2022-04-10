package com.example.dictionaryapp.feature_dict.data.remote.dto

data class PhoneticDto(
    val audio: String,
    val license: LicenseX,
    val sourceUrl: String,
    val text: String
) {

}