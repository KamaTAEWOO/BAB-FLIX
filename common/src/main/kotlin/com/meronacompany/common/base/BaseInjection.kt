package com.meronacompany.common.base

import android.content.Context
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
    // 호출 시 context를 넘겨야 하는 방식으로 변경
    // 사용 예: BaseInjection.provideHomeRepository(context)
    fun provideHomeRepository(context: Context): HomeRepository {
        return HomeRepositoryImpl(context, homeService)
    }

    // 다른 서비스와 저장소도 동일한 방식으로 생성
    // val otherService: OtherService by lazy { retrofit.create(OtherService::class.java) }
    // val otherRepository: OtherRepository by lazy { OtherRepositoryImpl(otherService) }
}