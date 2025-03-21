@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = "com.meronacompany.bab_flix"
}

dependencies {
    implementation(project(":design"))
    implementation(project(":feature"))
    implementation(project(":common"))

    implementation("com.google.android.gms:play-services-oss-licenses:17.1.0") // Added OSS Licenses dependency

    implementation(libs.timber)

    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.youtube.player.core)
    implementation(libs.chromecast.sender)

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