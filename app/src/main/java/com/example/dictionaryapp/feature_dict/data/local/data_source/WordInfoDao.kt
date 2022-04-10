package com.example.dictionaryapp.feature_dict.data.local.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionaryapp.feature_dict.data.local.entity.WordInfoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WordInfoDao {

    @Query("SELECT * FROM word_table WHERE word LIKE '%' || :word || '%' ")
    suspend fun getAllWordInfos(word: String): List<WordInfoEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(wordInfos: List<WordInfoEntity>)

    @Query("DELETE FROM word_table WHERE word IN(:words)")
    suspend fun deleteWordInfos(words: List<String>)

}