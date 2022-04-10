package com.example.dictionaryapp.feature_dict.data.local.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionaryapp.feature_dict.data.local.Converters
import com.example.dictionaryapp.feature_dict.data.local.entity.WordInfoEntity


@Database(entities = [WordInfoEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase : RoomDatabase() {
    abstract val dao: WordInfoDao

    companion object {
        const val DATABASE_NAME = "dictionary_db"
    }
}