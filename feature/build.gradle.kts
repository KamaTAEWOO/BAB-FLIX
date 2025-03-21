@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.android.gms.oss-licenses-plugin") apply false // Added OSS Licenses plugin
}

android {
    namespace = "com.meronacompany.feature"
}

dependencies {
    implementation(project(":design"))
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":common"))
    implementation(project(":data"))

    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0") // Added OSS Licenses dependency

    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.timber)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}