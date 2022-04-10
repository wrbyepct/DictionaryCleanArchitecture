package com.example.dictionaryapp.feature_dict.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dictionaryapp.feature_dict.presentation.components.WordInfoItem
import com.example.dictionaryapp.feature_dict.presentation.viewmodel.WordViewModel
import com.example.dictionaryapp.ui.theme.DictionaryAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryAppTheme {
                val viewModel: WordViewModel = hiltViewModel()
                val wordState = viewModel.wordState.value
                val scaffoldState = rememberScaffoldState()

                // Dealing with UI event (SnackBar)
                LaunchedEffect(key1 = true) {
                    viewModel.uiEvent.collectLatest {
                        when (it) {
                            is WordViewModel.UIEvent.ShowSnackBar -> {
                                scaffoldState.snackbarHostState.showSnackbar(it.message)
                            }
                        }
                    }
                }
                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {

                            TextField(
                                value = viewModel.searchText.value,
                                onValueChange = viewModel::onSearch,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder =  {
                                    Text(text = "Search...")
                                }

                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(wordState.wordInfoItems.size) { i ->
                                    val wordInfo = wordState.wordInfoItems[i]
                                    // If not the first item
                                    if ( i > 0) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    WordInfoItem(wordInfo = wordInfo)

                                    // if not the last item
                                    if (i < wordState.wordInfoItems.size - 1) {
                                        Divider()
                                    }

                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

