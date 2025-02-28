package com.meronacompany.common.base

import com.meronacompany.core.di.NetworkModule.provideRetrofit
import com.meronacompany.core.network.service.HomeService
import com.meronacompany.data.repository.HomeRepositoryImpl
import com.meronacompany.domain.repository.HomeRepository

object BaseInjection {

    // HomeService 인터페이스 생성
    private val homeService: HomeService by lazy {
        provideRetrofit().create(HomeService::class.java)
    }

    // HomeRepositoryImpl 생성
    val homeRepository: HomeRepository by lazy {
        HomeRepositoryImpl(homeService)
    }

    // 다른 서비스와 저장소도 동일한 방식으로 생성
    // val otherService: OtherService by lazy { retrofit.create(OtherService::class.java) }
    // val otherRepository: OtherRepository by lazy { OtherRepositoryImpl(otherService) }
}