package com.meronacompany.domain.repository

import com.meronacompany.domain.model.RequestLanguagesData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun requestIsApiKey(): Flow<Boolean>

    fun requestGetLanguages(): Flow<RequestLanguagesData>
}