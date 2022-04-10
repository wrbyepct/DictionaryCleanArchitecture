package com.example.dictionaryapp.feature_dict.data.repository

import android.util.Log
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.utils.HTTP_ERROR
import com.example.dictionaryapp.core.utils.IO_ERROR
import com.example.dictionaryapp.core.utils.Response
import com.example.dictionaryapp.feature_dict.data.local.data_source.WordInfoDao
import com.example.dictionaryapp.feature_dict.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dict.domain.model.WordInfo
import com.example.dictionaryapp.feature_dict.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    /**
     * Single Source of Truth
     */
    /*
        We only display data from database
     */
    override fun getWordInfo(word: String): Flow<Response<List<WordInfo>>> = flow {
        emit(Response.Loading())

        // Caching data (from entity to model)
        val wordInfos = dao.getAllWordInfos(word).map { it.toWordInfo() }
        emit(Response.Loading(data = wordInfos))

        // Api request
        try {

            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        } catch (e: HttpException) {
            emit(Response.Error(
                message = HTTP_ERROR,
                data = wordInfos
            ))

        } catch (e: IOException) {
            emit(Response.Error(
                message = IO_ERROR,
                data = wordInfos
            ))
        }
        val newWordInfos = dao.getAllWordInfos(word).map { it.toWordInfo() }
        emit(Response.Success(newWordInfos))
    }
}