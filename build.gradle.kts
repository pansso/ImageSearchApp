buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }


    dependencies {
        classpath(com.example.lezhintestapp.Dep.androidGradlePlugin)
        classpath(com.example.lezhintestapp.Dep.androidGradleApiPlugin)
        classpath(com.example.lezhintestapp.Dep.Kotlin.gradlePlugin)
        classpath(com.example.lezhintestapp.Dep.Kotlin.serializationPlugin)
        classpath(com.example.lezhintestapp.Dep.Hilt.plugin)
    }

    subprojects {
        afterEvaluate {
            project.apply("$rootDir/gradle/common.gradle")
        }
    }
}