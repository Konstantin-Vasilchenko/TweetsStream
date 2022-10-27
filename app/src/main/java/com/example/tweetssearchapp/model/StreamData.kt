package com.example.tweetssearchapp.model

import androidx.annotation.Keep

@Keep
data class StreamData(
    val data: Data,
    val matching_rules: List<MatchingRule>
)
