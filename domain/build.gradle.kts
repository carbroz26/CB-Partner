plugins {
    id("cbpartner.kmp")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core"))
        }
    }
}
