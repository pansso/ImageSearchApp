import com.example.lezhintestapp.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":core:model"))

    implementation(Dep.Kotlin.serializationJson)
    Dep.Retrofit.RetrofitList.forEach(::implementation)
    Dep.Paging.PagingList.forEach(::implementation)
    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)
    kapt(Dep.Room.compiler)
    Dep.Room.RoomList.forEach(::api)
}