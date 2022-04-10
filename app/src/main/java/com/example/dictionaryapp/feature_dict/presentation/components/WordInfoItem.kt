package com.example.dictionaryapp.feature_dict.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dictionaryapp.feature_dict.domain.model.WordInfo



@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // The word name
        Text(
            text = wordInfo.word,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        // Phonetic
        wordInfo.phonetic?.let { phonetic ->
            Text(text = wordInfo.phonetic, fontWeight = FontWeight.Light)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Meanings
        wordInfo.meanings.forEach { meaning ->
            Text(
                text = meaning.partOfSpeech,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            meaning.definitions.forEachIndexed { index, definition ->
                Text(text = "${index + 1}. ${definition.definition}")
                Spacer(modifier = Modifier.height(8.dp))
                definition.example?.let { example ->
                    Text(text = "Example: $example")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}