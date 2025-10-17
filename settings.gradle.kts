includeBuild("gradle-plugins")

pluginManagement {
    includeBuild("gradle-plugins")
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

rootProject.name = "Fintrack"

include(
    ":app",
    ":core:ui",
    ":feature:account",
    ":feature:onboarding",
    ":feature:dashboard",
    ":feature:budget",
    ":feature:savings",
    ":feature:expenses"
)