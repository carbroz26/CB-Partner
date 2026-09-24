plugins {
    id("cbpartner.kmp")
    id("cbpartner.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain"))
            implementation(project(":core"))
            implementation(project(":navigation"))
        }
    }
}
