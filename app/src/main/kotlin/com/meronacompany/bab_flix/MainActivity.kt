package com.meronacompany.bab_flix

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.meronacompany.core.local.PreferenceManager
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.navigation.AppNavHost
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTimber()
        initApiCount(this)
        setContent {
            BAB_FLIXTheme {
                AppNavHost()
//                CrashButton()
            }
        }
    }

    private fun setupTimber() {
        Timber.Forest.plant(CustomDebugTree(this.getString(R.string.app_name)))
    }

    private fun initApiCount(context: Context) {
        PreferenceManager.checkAndResetApiCallCountIfNeeded(context)
    }
}

// crash 테스트 코드
@Composable
private fun CrashButton() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Button
        Button(onClick = {
            try {
                throw Exception("Test Crash1111")
            } catch (e: Exception) {
                Timber.e(e, "Test Crash")
                FirebaseCrashlytics.getInstance().recordException(e)
            }

        }) {
            Text("Test Crash")
        }
    }
}