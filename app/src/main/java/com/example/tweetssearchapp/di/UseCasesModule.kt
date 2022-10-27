package com.example.tweetssearchapp.di

import com.example.tweetssearchapp.domain.GetTweetsSearchStreamUseCase
import com.example.tweetssearchapp.domain.GetTweetsSearchStreamUseCaseImpl
import com.example.tweetssearchapp.domain.SetSearchRuleUseCase
import com.example.tweetssearchapp.domain.SetSearchRuleUseCaseImpl
import com.example.tweetssearchapp.domain.mapper.StreamDataToStreamDataUiMapper
import com.example.tweetssearchapp.domain.mapper.StreamDataToStreamDataUiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCasesModule {

    @ViewModelScoped
    @Binds
    abstract fun bindGetTweetsSearchStreamUseCase(useCase: GetTweetsSearchStreamUseCaseImpl): GetTweetsSearchStreamUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindStreamDataToStreamDataUiMapper(mapper: StreamDataToStreamDataUiMapperImpl): StreamDataToStreamDataUiMapper

    @ViewModelScoped
    @Binds
    abstract fun bindSetSearchRuleUseCase(useCase: SetSearchRuleUseCaseImpl): SetSearchRuleUseCase
}
