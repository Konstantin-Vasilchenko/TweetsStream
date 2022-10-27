package com.example.tweetssearchapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetssearchapp.domain.GetTweetsSearchStreamUseCase
import com.example.tweetssearchapp.domain.SetSearchRuleUseCase
import com.example.tweetssearchapp.domain.model.StreamDataUi
import com.example.tweetssearchapp.model.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.Instant
import java.util.*
import javax.inject.Inject

private const val LIFESPAN = 10000L
private const val DELAY = 5000L

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTweetsSearchStreamUseCase: GetTweetsSearchStreamUseCase,
    private val serSearchStreamUseCase: SetSearchRuleUseCase
) : ViewModel() {

    private val _tweetsList = MutableStateFlow<List<StreamDataUi>>(emptyList())
    val tweetsList = _tweetsList.asStateFlow()
    private var isNetworkAvailable = false
    private var job: Job? = null

    init {
        reduceTweetsListForLifespan()
    }

    private fun processTweets() {
        job = viewModelScope.launch {
            getTweetsSearchStreamUseCase().distinctUntilChanged().collect {
                it?.let { _tweetsList.value += it }
            }
        }
    }

    fun setNetworkStatus(isConnected: Boolean) {
        isNetworkAvailable = isConnected
        if (isConnected) processTweets() else job?.cancel()
    }

    fun setSearchRule(rule: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = serSearchStreamUseCase(rule)
                if (result is ResultOf.Success) {
                    _tweetsList.value = emptyList()
                }
            }
        }
    }

    private fun reduceTweetsListForLifespan() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                launch {
                    while (isActive) {
                        if (isNetworkAvailable) {
                            val currentStamp = Date.from(Instant.now()).time
                            _tweetsList.value =
                                _tweetsList.value.filter { currentStamp - it.stamp < LIFESPAN }
                        }
                        delay(DELAY)
                    }
                }
            }
        }
    }
}
