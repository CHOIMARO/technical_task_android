import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt") // This applies the kapt plugin
    id("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.choimaro.technical_task_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.choimaro.technical_task_android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        //Kakao API KEY
        buildConfigField("String", "KAKAO_API_KEY", gradleLocalProperties(rootDir, providers).getProperty("KAKAO_API_KEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val kotlinVersion: String by project
    val hiltVersion: String by project
    val splashScreenVersion: String by project

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.coil.compose)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Okhttp
    implementation(libs.okhttp)
    implementation(libs.json)

    // Hilt
    implementation(libs.hilt.android)
    kapt("com.google.dagger:hilt-compiler:${hiltVersion}")

    // Navigation
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    //kakao build version
    implementation (libs.v2.all) // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation (libs.v2.user) // 카카오 로그인 API 모듈
    implementation (libs.v2.share) // 카카오톡 공유 API 모듈
    implementation (libs.v2.talk) // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation (libs.v2.friend) // 피커 API 모듈
    implementation (libs.v2.navi) // 카카오내비 API 모듈
    implementation (libs.v2.cert) // 카카오톡 인증 서비스 API 모듈

    //Paging3
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)


}