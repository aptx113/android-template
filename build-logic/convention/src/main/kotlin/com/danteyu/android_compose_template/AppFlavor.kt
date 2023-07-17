package com.danteyu.android_compose_template

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

@Suppress("EnumEntryName")
enum class AppFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val versionNameSuffix: String? = null
) {
    dev(FlavorDimension.contentType, applicationIdSuffix = ".dev", versionNameSuffix = "-dev"),
    qat(FlavorDimension.contentType, applicationIdSuffix = ".qat", versionNameSuffix = "-qat"),
    prod(FlavorDimension.contentType)
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.contentType.name
        productFlavors {
            AppFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null && it.versionNameSuffix != null) {
                            this.applicationIdSuffix = it.applicationIdSuffix
                            this.versionNameSuffix = it.versionNameSuffix
                        }
                    }
                }
            }
        }
    }
}