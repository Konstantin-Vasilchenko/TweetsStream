package com.example.tweetssearchapp.model

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R? = null): ResultOf<R>()
    data class Failure(val throwable: Throwable?): ResultOf<Nothing>()
}
