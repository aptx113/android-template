package com.danteyu.android_compose_template

import com.android.build.api.dsl.CommonExtension
import com.danteyu.android_compose_template.constants.LibsConst.ANDROID_TEST_IMPLEMENTATION
import com.danteyu.android_compose_template.constants.LibsConst.ANDROIDX_COMPOSE_BOM
import com.danteyu.android_compose_template.constants.LibsConst.IMPLEMENTATION
import com.danteyu.android_compose_template.constants.LibsConst.LIBS
import com.danteyu.android_compose_template.constants.VersionsConst.ANDROIDX_COMPOSE_COMPILER
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.io.File

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion(ANDROIDX_COMPOSE_COMPILER).get().toString()
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + buildComposeMetricsParameters()
        }

        dependencies {
            val bom = libs.findLibrary(ANDROIDX_COMPOSE_BOM).get()
            add(IMPLEMENTATION, platform(bom))
            add(ANDROID_TEST_IMPLEMENTATION, platform(bom))
        }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters = mutableListOf<String>()
    val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
    val enableMetrics = (enableMetricsProvider.orNull == "true")
    if (enableMetrics) {
        val metricsFolder = File(project.buildDir, "compose-metrics")
        metricParameters.apply {
            add("-P")
            add("plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + metricsFolder.absolutePath)
        }
    }

    val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
    val enableReports = (enableReportsProvider.orNull == "true")
    if (enableReports) {
        val reportsFolder = File(project.buildDir, "compose-reports")
        metricParameters.apply {
            add("-P")
            add("plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + reportsFolder.absolutePath)
        }
    }
    return metricParameters.toList()
}