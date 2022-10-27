package com.example.tweetssearchapp.domain.mapper

import com.example.tweetssearchapp.domain.model.StreamDataUi
import com.example.tweetssearchapp.model.StreamData
import javax.inject.Inject

interface StreamDataToStreamDataUiMapper : (StreamData?) -> StreamDataUi?

class StreamDataToStreamDataUiMapperImpl @Inject constructor(): StreamDataToStreamDataUiMapper {
    override fun invoke(streamData: StreamData?): StreamDataUi? {
        return if (streamData != null )StreamDataUi(streamData.data.text) else null
    }
}
