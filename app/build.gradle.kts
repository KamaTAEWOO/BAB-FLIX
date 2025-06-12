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

android.applicationVariants.all {
    if (buildType.name == "release") {
        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            if (outputImpl.outputFileName.endsWith(".apk")) {
                outputImpl.outputFileName = "BABFLIX-v${versionName}(${versionCode}).apk"
            }
            if (outputImpl.outputFileName.endsWith(".aab")) {
                outputImpl.outputFileName = "BABFLIX-v${versionName}(${versionCode}).aab"
            }
        }
    }
}

// Rename AAB after bundleRelease finishes, but only if the task exists
afterEvaluate {
    tasks.findByName("bundleRelease")?.doLast {
        val bundleDir = File(buildDir, "outputs/bundle/release")
        val originalFile = File(bundleDir, "app-release.aab")
        val renamedFile = File(
            bundleDir,
            "BABFLIX-v${android.defaultConfig.versionName}(${android.defaultConfig.versionCode}).aab"
        )

        if (originalFile.exists()) {
            originalFile.renameTo(renamedFile)
            println("Renamed AAB to: ${renamedFile.absolutePath}")
        } else {
            println("Original AAB not found: ${originalFile.absolutePath}")
        }
    }
}

tasks.register("buildAllReleaseArtifacts") {
    group = "build"
    description = "Builds both release APK and AAB"

    dependsOn("assembleRelease")
    dependsOn("bundleRelease")

    doLast {
        println("Release APK and AAB have been assembled.")
        println("APK path: ${buildDir}/outputs/apk/release/BABFLIX-v${android.defaultConfig.versionName}(${android.defaultConfig.versionCode}).apk")
        println("AAB path: ${buildDir}/outputs/bundle/release/BABFLIX-v${android.defaultConfig.versionName}(${android.defaultConfig.versionCode}).aab")
    }
}
