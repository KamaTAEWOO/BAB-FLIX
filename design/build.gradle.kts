@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.meronacompany.design"
}

dependencies {
    implementation(libs.glide)

    implementation(libs.timber)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.youtube.player.core)
    implementation(libs.chromecast.sender)

    implementation(libs.androidx.ui)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}