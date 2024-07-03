import com.example.lezhintestapp.Dep
import com.example.lezhintestapp.Versions

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.lezhintestapp"
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.example.lezhintestapp"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    Dep.androidList.forEach(::implementation)
    Dep.LifeCycle.LifeCycleList.forEach(::implementation)

    implementation(platform(Dep.Compose.bom))
    Dep.Compose.ComposeList.forEach(::implementation)

    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)
}