package com.example.dictionaryapp.feature_dict.di

import android.app.Application
import androidx.room.Room
import com.example.dictionaryapp.feature_dict.data.local.Converters
import com.example.dictionaryapp.feature_dict.data.local.data_source.WordInfoDatabase
import com.example.dictionaryapp.feature_dict.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dict.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryapp.feature_dict.data.util.GsonParser
import com.example.dictionaryapp.feature_dict.domain.repository.WordInfoRepository
import com.example.dictionaryapp.feature_dict.domain.use_cases.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app,
            WordInfoDatabase::class.java,
            WordInfoDatabase.DATABASE_NAME
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(db: WordInfoDatabase, api: DictionaryApi): WordInfoRepository {
        return WordInfoRepositoryImpl(dao = db.dao, api= api)
    }


    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository)
    }


}