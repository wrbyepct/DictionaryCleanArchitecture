package com.example.dictionaryapp.feature_dict.domain.use_cases

import com.example.dictionaryapp.core.utils.Response
import com.example.dictionaryapp.feature_dict.domain.model.WordInfo
import com.example.dictionaryapp.feature_dict.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo(
    private val repository: WordInfoRepository
) {
    operator fun invoke(word: String): Flow<Response<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow{ }
        }
        return repository.getWordInfo(word)
    }
}