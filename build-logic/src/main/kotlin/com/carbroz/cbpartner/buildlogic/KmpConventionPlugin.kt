package com.carbroz.cbpartner.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.multiplatform")

        target.extensions.configure<KotlinMultiplatformExtension> {
            iosArm64()
            iosSimulatorArm64()
            jvm()
        }
    }
}
