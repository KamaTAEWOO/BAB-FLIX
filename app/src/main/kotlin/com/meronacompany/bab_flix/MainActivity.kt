package com.meronacompany.bab_flix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.navigation.AppNavHost
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTimber()
        setContent {
            BAB_FLIXTheme {
                AppNavHost()
            }
        }
    }

    private fun setupTimber() {
        Timber.Forest.plant(CustomDebugTree(this.getString(R.string.app_name)))
    }
}