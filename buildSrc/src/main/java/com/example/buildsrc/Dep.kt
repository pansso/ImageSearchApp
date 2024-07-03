package com.example.lezhintestapp


object Dep {

    const val androidGradlePlugin = "com.android.tools.build:gradle:8.1.0"
    const val androidGradleApiPlugin = "com.android.tools.build:gradle-api:8.1.0"

    object Andriod {
        const val core = "androidx.core:core-ktx:1.10.1"
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"
        const val activitiy = "androidx.activity:activity-ktx:1.7.2"
        const val fragment = "androidx.fragment:fragment-ktx:1.6.1"
    }

    val androidList = listOf(
        "androidx.core:core-ktx:1.10.1",
        "androidx.appcompat:appcompat:1.6.1",
        "androidx.activity:activity-ktx:1.7.2",
    )

    object LifeCycle {
        private const val lifecycleVersion = "2.6.1"

        val LifeCycleList = listOf(
            "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion",
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion",
        )
    }

    object Kotlin {
        private const val kotlinVersion = "1.9.0"
        private const val coroutineVersion = "1.7.3"

        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:1.6.0"

        val CoroutineList = listOf(
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion",
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
        )
    }

    object Glide {

    }

    object Retrofit {
        private const val retrofitVersion = "2.9.0"

        val RetrofitList = listOf(
            "com.squareup.retrofit2:retrofit:$retrofitVersion",
            "com.squareup.retrofit2:converter-gson:$retrofitVersion",
            "com.squareup.retrofit2:converter-scalars:$retrofitVersion",
            "com.squareup.okhttp3:logging-interceptor:3.9.1"
        )

    }

    object Compose {

        val bom = "androidx.compose:compose-bom:2023.03.00"
        val ComposeList = listOf(
            "androidx.compose.ui:ui",
            "androidx.compose.ui:ui-tooling",
            "androidx.navigation:navigation-compose",
            "androidx.hilt:hilt-navigation-compose",
            "androidx.compose.material3:material3-android",
            "androidx.activity:activity-compose",
            "androidx.lifecycle:lifecycle-runtime-compose",
            "androidx.lifecycle:lifecycle-viewmodel-compose",
        )

    }

    object Google {
        const val gson = "com.google.code.gson:gson:2.8.6"
    }

    object Hilt {
        private const val hiltVersion = "2.50"

        const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val compiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    }

    const val timber = "com.jakewharton.timber:timber:5.0.1"
    object Room {
        private const val roomVersion = "2.5.0"

        const val compiler = "androidx.room:room-compiler:$roomVersion"

        val RoomList = listOf(
            "androidx.room:room-runtime:$roomVersion",
            "androidx.room:room-ktx:$roomVersion"
        )
    }


}