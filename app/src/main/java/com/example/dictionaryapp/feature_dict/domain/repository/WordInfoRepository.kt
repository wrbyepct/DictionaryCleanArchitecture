package com.example.dictionaryapp.feature_dict.domain.repository

import com.example.dictionaryapp.core.utils.Response
import com.example.dictionaryapp.feature_dict.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Response<List<WordInfo>>>

}