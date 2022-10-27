package com.example.tweetssearchapp.data

import android.util.Log
import com.example.tweetssearchapp.model.ResultOf
import com.example.tweetssearchapp.model.SearchRule
import com.example.tweetssearchapp.model.StreamData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.Buffer

import javax.inject.Inject

class TweetsRepository @Inject constructor(
    private val tweetsApi: TweetsApi,
    private val gson: Gson
) {

    fun getTweetsStream(): Flow<StreamData> {
        return flow {
            val bufferedSource = tweetsApi.getTweetsSearchStream().body()?.source()
            val buffer = Buffer()

            try {
                while (!bufferedSource!!.exhausted()) {
                    bufferedSource.read(buffer, 8192)
                    val data = buffer.readUtf8LineStrict()
                    emit(gson.fromJson(data, StreamData::class.java))
                }
            } catch (exp: Exception) {
                bufferedSource?.close()
                Log.d("TweetsRepository", "getTweetsStream: ${exp.message}")
            }
        }
            .flowOn(Dispatchers.IO)
    }

    suspend fun setSearchRule(rule: SearchRule): ResultOf<Nothing> {
        val response = tweetsApi.setSearchRule(body = rule)
        return if (response.isSuccessful) ResultOf.Success() else ResultOf.Failure(Exception("Network Error")
        )
    }
}
