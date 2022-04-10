package com.example.dictionaryapp.feature_dict.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.utils.Response
import com.example.dictionaryapp.feature_dict.domain.use_cases.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WordViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchText: State<String> = _searchQuery

    private val _wordState = mutableStateOf(WordInfoState())
    val wordState: State<WordInfoState> = _wordState

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent: SharedFlow<UIEvent> = _uiEvent.asSharedFlow()

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        // Cancel the current job
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500L)
            /*
            Because one single flow can emit value multiple times
            and you want to respond to every single one, so you...
             */
            getWordInfo(query)
                .onEach { response ->
                    when (response) {
                        is Response.Loading -> {
                            _wordState.value = wordState.value.copy(
                                isLoading = true,
                                wordInfoItems = response.data ?: emptyList()
                            )
                        }
                        is Response.Error -> {
                            _wordState.value = WordInfoState(
                                isLoading = false,
                                wordInfoItems = response.data ?: emptyList()
                            )
                            _uiEvent.emit(UIEvent.ShowSnackBar(response.message ?: "Unknown error"))

                        }
                        is Response.Success -> {
                            _wordState.value = WordInfoState(
                                isLoading = false,
                                wordInfoItems = response.data ?: emptyList()
                            )
                        }
                    }
                }.launchIn(this)
        }

    }
}