import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.choimaro.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

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
}

dependencies {
    val hiltVersion: String by project

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":domain"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // kakao build version
    implementation (libs.v2.all) // 전체 모듈 설치
    implementation (libs.v2.user) // 카카오 로그인 API 모듈
    implementation (libs.v2.share) // 카카오톡 공유 API 모듈
    implementation (libs.v2.talk) // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation (libs.v2.friend) // 피커 API 모듈
    implementation (libs.v2.navi) // 카카오내비 API 모듈
    implementation (libs.v2.cert) // 카카오톡 인증 서비스 API 모듈

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)

    // Hilt
    implementation (libs.hilt.android)
    kapt("com.google.dagger:hilt-compiler:${hiltVersion}")
}
