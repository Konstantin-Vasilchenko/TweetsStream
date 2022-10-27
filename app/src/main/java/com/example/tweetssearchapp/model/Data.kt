package com.example.tweetssearchapp.model

import androidx.annotation.Keep

@Keep
data class Data(
    val edit_history_tweet_ids: List<String>,
    val id: String,
    val text: String
)
