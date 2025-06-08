@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.meronacompany.bab_flix"

    signingConfigs {
        create("release") {
            storeFile = file(System.getProperty("user.home") + "/Desktop/bab_key")
            storePassword = "123456"
            keyAlias = "bab_key"
            keyPassword = "123456"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            enableAndroidTestCoverage = false
            enableUnitTestCoverage = false
        }
    }
}

dependencies {
    implementation(project(":design"))
    implementation(project(":feature"))
    implementation(project(":common"))
    implementation(project(":core"))

    implementation("com.google.android.gms:play-services-oss-licenses:17.1.0") // Added OSS Licenses dependency

    implementation(libs.timber)

    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.youtube.player.core)
    implementation(libs.chromecast.sender)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)

    implementation(libs.v2.user)
    implementation(libs.firebase.auth.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks.configureEach {
    if (name.contains("collectReleaseBaselineProfile")) {
        enabled = false
    }
}

tasks.register("buildReleaseApk") {
    group = "build"
    description = "Builds the release APK"

    dependsOn("assembleRelease")

    doLast {
        println("Release APK has been assembled.")
        val apkPath = "${buildDir}/outputs/apk/release/app-release.apk"
        println("APK path: $apkPath")
    }
}

tasks.register("signReleaseApk") {
    group = "build"
    description = "Signs the unsigned release APK manually"

    val unsignedApkPath = "${buildDir}/outputs/apk/release/app-release-unsigned.apk"
    val signedApkPath = "${buildDir}/outputs/apk/release/app-release-signed.apk"
    val keystorePath = System.getProperty("user.home") + "/Desktop/bab_key"
    val keystorePassword = "123456"
    val keyAlias = "bab_key"
    val keyPassword = "123456"

    doLast {
        println("Signing APK...")
        exec {
            commandLine(
                "jarsigner",
                "-verbose",
                "-sigalg", "SHA1withRSA",
                "-digestalg", "SHA-1",
                "-keystore", keystorePath,
                "-storepass", keystorePassword,
                "-keypass", keyPassword,
                "-signedjar", signedApkPath,
                unsignedApkPath,
                keyAlias
            )
        }
        println("Signed APK path: $signedApkPath")
    }
}