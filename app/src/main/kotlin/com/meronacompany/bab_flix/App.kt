package com.meronacompany.bab_flix

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.Forest.plant(CustomDebugTree(this.getString(R.string.app_name)))
    }
}