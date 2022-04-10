package com.example.dictionaryapp.feature_dict.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.feature_dict.domain.model.Meaning
import com.example.dictionaryapp.feature_dict.domain.model.WordInfo

@Entity(tableName = "word_table")
data class WordInfoEntity(
    @PrimaryKey
    val id: Long? = null,
    val meanings: List<Meaning>,
    val phonetic: String?,
    val word: String
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word,
            phonetic = phonetic
        )
    }
}
