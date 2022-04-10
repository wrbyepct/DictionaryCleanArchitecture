package com.example.dictionaryapp.feature_dict.data.remote.dto

import com.example.dictionaryapp.feature_dict.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.feature_dict.domain.model.WordInfo

data class WordInfoDto(
    val license: License,
    val meanings: List<MeaningDto>,
    val phonetic: String?,
    val phonetics: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            word = word,
            phonetic = phonetic
        )
    }
}