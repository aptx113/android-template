import com.danteyu.android_compose_template.AppBuildType
import com.danteyu.android_compose_template.config.DefaultConfigs

plugins {
    id("app.android.application")
    id("app.android.application.compose")
    id("app.android.application.flavors")
    id("app.android.application.jacoco")
    id("app.android.hilt")
    id("jacoco")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")

android {
    defaultConfig {
        applicationId = DefaultConfigs.APPLICATION_ID
        versionCode = DefaultConfigs.VERSION_CODE
        versionName = DefaultConfigs.VERSION_NAME

        testInstrumentationRunner = DefaultConfigs.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = AppBuildType.DEBUG.applicationIdSuffix
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
            signingConfig = signingConfigs.getByName("debug")
        }

        val release by getting {
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = AppBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (keystorePropertiesFile.exists()) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        val benchmark by creating {
            initWith(release)
            matchingFallbacks.add("release")
            signingConfig = signingConfigs.getByName("debug")

            isMinifyEnabled = true
            applicationIdSuffix = AppBuildType.BENCHMARK.applicationIdSuffix
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests { isIncludeAndroidResources = true }
    }
    namespace = DefaultConfigs.NAME_SPACE
}

dependencies {

    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.accompanist.testharness)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)

    implementation(libs.coil.kt)
}

configurations.configureEach {
    resolutionStrategy {
        force(libs.junit4)
    }
}
