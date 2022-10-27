package com.example.tweetssearchapp.data

import com.example.tweetssearchapp.model.SearchRule
import com.example.tweetssearchapp.utils.Constants.AUTH_TOKEN
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Streaming
import javax.inject.Singleton

private const val API_VER = 2

@Singleton
interface TweetsApi {

    @Streaming
    @GET("$API_VER/tweets/search/stream")
    suspend fun getTweetsSearchStream(@Header("Authorization") token: String = AUTH_TOKEN): Response<ResponseBody>

    @POST("$API_VER/tweets/search/stream/rules")
    suspend fun setSearchRule(
        @Header("Authorization") token: String = AUTH_TOKEN,
        @Body body: SearchRule
    ): Response<ResponseBody>
}
