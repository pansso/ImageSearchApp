import com.example.lezhintestapp.Dep

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
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    Dep.androidList.forEach(::implementation)
    Dep.LifeCycle.LifeCycleList.forEach(::implementation)

    Dep.Compose.ComposeList.forEach(::implementation)
    implementation(Dep.Glide.glide)

    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)
    testImplementation("junit:junit:4.13.2")
}