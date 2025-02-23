package com.meronacompany.data.repository

import com.meronacompany.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class HomeRepositoryImpl : HomeRepository {

    override fun requestIsApiKey(): Flow<Boolean> = flow {
        emit(true)
    }
}