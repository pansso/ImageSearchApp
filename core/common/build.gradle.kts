import com.example.lezhintestapp.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.example.common"


    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}


dependencies {

    Dep.androidList.forEach(::implementation)
    Dep.LifeCycle.LifeCycleList.forEach(::implementation)

    Dep.Compose.ComposeList.forEach(::implementation)
}