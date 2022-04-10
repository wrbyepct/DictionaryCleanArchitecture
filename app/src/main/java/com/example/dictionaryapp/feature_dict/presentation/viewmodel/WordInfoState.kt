package com.example.dictionaryapp.feature_dict.presentation.viewmodel

import com.example.dictionaryapp.feature_dict.domain.model.WordInfo

data class WordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)
