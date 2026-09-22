pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CB-Partner"

include(
    ":core",
    ":domain",
    ":data",
    ":navigation",
    ":feature:splash",
    ":feature:dynamic",
    ":androidApp",
    ":desktopApp",
)
