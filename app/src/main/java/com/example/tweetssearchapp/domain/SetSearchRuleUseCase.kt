package com.example.tweetssearchapp.domain

import com.example.tweetssearchapp.data.TweetsRepository
import com.example.tweetssearchapp.model.Add
import com.example.tweetssearchapp.model.ResultOf
import com.example.tweetssearchapp.model.SearchRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SetSearchRuleUseCase : suspend (String) -> ResultOf<Nothing>

class SetSearchRuleUseCaseImpl @Inject constructor(private val repository: TweetsRepository) :
    SetSearchRuleUseCase {

    override suspend fun invoke(rule: String): ResultOf<Nothing> {
        return repository.setSearchRule(SearchRule(add = listOf(Add(tag = rule, value = rule))))
    }
}
