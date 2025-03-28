package com.meronacompany.bab_flix

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.meronacompany.design.theme.BAB_FLIXTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTimber()
        setContent {
            BAB_FLIXTheme {
//                AppNavHost()
                CrashButton()
            }
        }
    }

    private fun setupTimber() {
        Timber.Forest.plant(CustomDebugTree(this.getString(R.string.app_name)))
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
            throw RuntimeException("Test Crash") // Force a crash
        }) {
            Text("Test Crash")
        }
    }
}