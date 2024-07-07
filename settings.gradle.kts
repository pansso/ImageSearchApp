pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LezhinTestApp"
include(":app")

include(":core:domain")
include(":core:data")
include(":core:model")

include(":feature:search")
include(":feature:bookmark")
include(":feature:main")
include(":core:common")
