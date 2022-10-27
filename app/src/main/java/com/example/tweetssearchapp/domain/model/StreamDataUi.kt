package com.example.tweetssearchapp.domain.model

import java.time.Instant
import java.util.Date

data class StreamDataUi(val text: String, val stamp: Long = Date.from(Instant.now()).time)
