plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(com.example.lezhintestapp.Dep.Hilt.hilt)
    kapt(com.example.lezhintestapp.Dep.Hilt.compiler)
}