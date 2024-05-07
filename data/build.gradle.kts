import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.choimaro.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":domain"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //kakao build version
    implementation (libs.v2.all) // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation (libs.v2.user) // 카카오 로그인 API 모듈
    implementation (libs.v2.share) // 카카오톡 공유 API 모듈
    implementation (libs.v2.talk) // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation (libs.v2.friend) // 피커 API 모듈
    implementation (libs.v2.navi) // 카카오내비 API 모듈
    implementation (libs.v2.cert) // 카카오톡 인증 서비스 API 모듈
}
