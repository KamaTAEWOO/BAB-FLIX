package com.meronacompany.bab_flix

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "744303b78663b969cbaad7e8df73233c")
        FirebaseApp.initializeApp(this)
    }
}