package com.example.tweetssearchapp.domain

import com.example.tweetssearchapp.data.TweetsRepository
import com.example.tweetssearchapp.domain.mapper.StreamDataToStreamDataUiMapper
import com.example.tweetssearchapp.domain.model.StreamDataUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetTweetsSearchStreamUseCase : () -> Flow<StreamDataUi?>

class GetTweetsSearchStreamUseCaseImpl @Inject constructor(
    private val mapper: StreamDataToStreamDataUiMapper,
    private val repository: TweetsRepository
) : GetTweetsSearchStreamUseCase {
    override fun invoke(): Flow<StreamDataUi?> {
        return repository.getTweetsStream()
            .map(mapper)
    }
}
