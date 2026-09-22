plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.4.20"
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:9.3.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.20")
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.12.0")
}

gradlePlugin {
    plugins {
        register("cbPartnerKmp") {
            id = "cbpartner.kmp"
            implementationClass = "com.carbroz.cbpartner.buildlogic.KmpConventionPlugin"
        }
        register("cbPartnerCompose") {
            id = "cbpartner.compose"
            implementationClass = "com.carbroz.cbpartner.buildlogic.ComposeConventionPlugin"
        }
        register("cbPartnerAndroidApplication") {
            id = "cbpartner.android.application"
            implementationClass = "com.carbroz.cbpartner.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("cbPartnerDesktopApplication") {
            id = "cbpartner.desktop.application"
            implementationClass = "com.carbroz.cbpartner.buildlogic.DesktopApplicationConventionPlugin"
        }
    }
}