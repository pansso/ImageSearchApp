plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.bookmark"

    buildFeatures {
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {

    com.example.lezhintestapp.Dep.androidList.forEach(::implementation)
    com.example.lezhintestapp.Dep.LifeCycle.LifeCycleList.forEach(::implementation)

//    implementation(platform(com.example.lezhintestapp.Dep.Compose.bom))
    com.example.lezhintestapp.Dep.Compose.ComposeList.forEach(::implementation)

    implementation(com.example.lezhintestapp.Dep.Hilt.hilt)
    kapt(com.example.lezhintestapp.Dep.Hilt.compiler)
    testImplementation("junit:junit:4.13.2")
}