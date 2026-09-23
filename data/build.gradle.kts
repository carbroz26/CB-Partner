plugins {
    id("cbpartner.kmp")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain"))
            implementation(project(":core"))
        }
    }
}
