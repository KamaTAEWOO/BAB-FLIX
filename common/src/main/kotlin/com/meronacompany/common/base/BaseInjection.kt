package com.meronacompany.common.base

import android.content.Context
import com.meronacompany.core.di.NetworkModule.provideRetrofit
import com.meronacompany.core.network.service.HomeService
import com.meronacompany.data.repository.HomeRepositoryImpl
import com.meronacompany.domain.repository.HomeRepository

object BaseInjection {
    private fun createHomeService(context: Context): HomeService {
        return provideRetrofit(context).create(HomeService::class.java)
    }

    fun provideHomeRepository(context: Context): HomeRepository {
        return HomeRepositoryImpl(context, createHomeService(context))
    }

    // 다른 서비스와 저장소도 동일한 방식으로 생성
    // val otherService: OtherService by lazy { retrofit.create(OtherService::class.java) }
    // val otherRepository: OtherRepository by lazy { OtherRepositoryImpl(otherService) }
}