package com.carbroz.cbpartner.buildlogic

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        target.pluginManager.apply("com.android.kotlin.multiplatform.library")

        target.extensions.configure<KotlinMultiplatformExtension> {
            targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).all {
                compileSdk = 36
            }
            iosArm64()
            iosSimulatorArm64()
            jvm()
        }
    }
}
