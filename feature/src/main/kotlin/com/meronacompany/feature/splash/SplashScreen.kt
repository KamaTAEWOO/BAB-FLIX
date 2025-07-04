package com.meronacompany.feature.splash

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity

    // Register flexible update listener on composition, unregister on dispose
    DisposableEffect(Unit) {
        registerFlexibleUpdateListener(activity)
        onDispose {
            unregisterFlexibleUpdateListener(activity)
        }
    }

    LaunchedEffect(Unit) {
        checkFlexibleUpdate(activity)
        checkImmediateUpdate(activity)
        kotlinx.coroutines.delay(2000)
        onNavigateToHome()
    }

    Scaffold(
        topBar = {},
        content = { SplashContent(it) },
        bottomBar = {}
    )
}

@Composable
fun SplashContent(paddingValues: PaddingValues) {
    var startAnimation by remember { mutableStateOf(false) }

    val animatedScale by animateFloatAsState(
        targetValue = if (startAnimation) 2f else 1f,
        animationSpec = tween(durationMillis = 2000),
        label = "scale-up"
    )

    // 애니메이션 시작 트리거
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = com.meronacompany.design.R.drawable.ic_new_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                }
        )
    }
}

fun checkFlexibleUpdate(
    activity: Activity?
) {
    activity ?: return
    val appUpdateManager = AppUpdateManagerFactory.create(activity)
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
        Timber.d("taewoo - UpdateAvailability = ${appUpdateInfo.updateAvailability()}")
        Timber.d("taewoo - IsFlexibleAllowed = ${appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)}")

        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
            appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
        ) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.FLEXIBLE,
                activity,
                1234
            )
        }
    }
}

fun checkImmediateUpdate(activity: Activity?) {
    activity ?: return
    val appUpdateManager = AppUpdateManagerFactory.create(activity)
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
            appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
        ) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                activity,
                5678
            )
        }
    }
}

fun registerFlexibleUpdateListener(activity: Activity?) {
    activity ?: return
    val appUpdateManager = AppUpdateManagerFactory.create(activity)
    // Hold listener as a property to allow unregister
    val listener = InstallStateUpdatedListener { state ->
        Timber.d("SplashScreen, InstallStatus: ${state.installStatus()}")
        when (state.installStatus()) {
            InstallStatus.PENDING ->
                Timber.d("SplashScreen, InstallStatus: PENDING")

            InstallStatus.DOWNLOADING ->
                Timber.d("SplashScreen, InstallStatus: DOWNLOADING")

            InstallStatus.DOWNLOADED -> {
                Timber.d("SplashScreen, InstallStatus: DOWNLOADED")
                appUpdateManager.completeUpdate()
            }

            InstallStatus.INSTALLING ->
                Timber.d("SplashScreen, InstallStatus: INSTALLING")

            InstallStatus.INSTALLED ->
                Timber.d("SplashScreen, InstallStatus: INSTALLED")

            InstallStatus.FAILED -> {
                Timber.e("SplashScreen, InstallStatus: FAILED")
                FirebaseCrashlytics.getInstance().recordException(
                    Exception("Update failed with status: ${state.installStatus()}")
                )
            }

            InstallStatus.CANCELED ->
                Timber.d("SplashScreen, InstallStatus: CANCELED")

            InstallStatus.UNKNOWN ->
                Timber.d("SplashScreen, InstallStatus: UNKNOWN")

            else ->
                Timber.d("SplashScreen, InstallStatus: UNKNOWN (${state.installStatus()})")
        }
    }
    // Save the listener in a tag for unregistering later (or use a singleton/global if needed)
    activity.application?.let {
        it.javaClass.getDeclaredFieldOrNull("flexibleUpdateListener")?.let { field ->
            field.isAccessible = true
            field.set(it, listener)
        }
    }
    appUpdateManager.registerListener(listener)
}

// Helper to get declared field or null
private fun Class<*>.getDeclaredFieldOrNull(name: String) = try {
    getDeclaredField(name)
} catch (_: Exception) { null }

fun unregisterFlexibleUpdateListener(activity: Activity?) {
    activity ?: return
    val appUpdateManager = AppUpdateManagerFactory.create(activity)
    // Try to get the listener from the application tag (if you used a global/singleton, use that)
    val listener = activity.application?.let {
        it.javaClass.getDeclaredFieldOrNull("flexibleUpdateListener")?.let { field ->
            field.isAccessible = true
            field.get(it) as? InstallStateUpdatedListener
        }
    }
    if (listener != null) {
        appUpdateManager.unregisterListener(listener)
    }
}
