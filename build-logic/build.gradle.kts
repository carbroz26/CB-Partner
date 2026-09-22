plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlinJvm)
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation("com.android.tools.build:gradle:${libs.versions.agp.get()}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    implementation("org.jetbrains.compose:compose-gradle-plugin:${libs.versions.composeMultiplatform.get()}")
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
