package com.offline.imagedemo.screen.repo

import androidx.annotation.WorkerThread
import com.offline.imagedemo.screen.datasource.OpenAiRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpenAiRepo @Inject constructor(
    private val remoteDataSource : OpenAiRemoteDataSource
    ): Repository
    {

        @WorkerThread
        suspend fun loadImage(imageDetail:String) = flow {
            emit(remoteDataSource.fetchImageUrl(imageDetail))
        }.flowOn(Dispatchers.IO)

    }



