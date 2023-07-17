import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.danteyu.android_compose_template.configureFlavors
import com.danteyu.android_compose_template.configureKotlinAndroid
import com.danteyu.android_compose_template.configurePrintApksTask
import com.danteyu.android_compose_template.constants.LibsConst.ANDROID_TEST_IMPLEMENTATION
import com.danteyu.android_compose_template.constants.LibsConst.JUNIT4
import com.danteyu.android_compose_template.constants.LibsConst.KOTLIN_ANDROID
import com.danteyu.android_compose_template.constants.LibsConst.LIBS
import com.danteyu.android_compose_template.constants.LibsConst.TEST
import com.danteyu.android_compose_template.constants.LibsConst.TEST_IMPLEMENTATION
import com.danteyu.android_compose_template.constants.PluginsConst.ANDROID_LIBRARY_ID
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(ANDROID_LIBRARY_ID)
                apply(KOTLIN_ANDROID)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
                configureFlavors(this)
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary(JUNIT4).get())
                }
            }
            dependencies {
                add(ANDROID_TEST_IMPLEMENTATION, kotlin(TEST))
                add(TEST_IMPLEMENTATION, kotlin(TEST))
            }
        }
    }
}