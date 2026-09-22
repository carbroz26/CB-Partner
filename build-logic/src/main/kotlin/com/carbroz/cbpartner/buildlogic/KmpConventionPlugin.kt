package com.carbroz.cbpartner.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.multiplatform")
    }
}
