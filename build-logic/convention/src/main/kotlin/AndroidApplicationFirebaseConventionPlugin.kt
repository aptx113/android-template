import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.danteyu.android_compose_template.constants.LibsConst.FIREBASE_ANALYTICS
import com.danteyu.android_compose_template.constants.LibsConst.FIREBASE_BOM
import com.danteyu.android_compose_template.constants.LibsConst.FIREBASE_CRASHLYTICS
import com.danteyu.android_compose_template.constants.LibsConst.FIREBASE_PERFORMANCE
import com.danteyu.android_compose_template.constants.LibsConst.IMPLEMENTATION
import com.danteyu.android_compose_template.constants.LibsConst.LIBS
import com.danteyu.android_compose_template.constants.PluginsConst.FIREBASE_CRASHLYTICS_ID
import com.danteyu.android_compose_template.constants.PluginsConst.FIREBASE_PERFORMANCE_ID
import com.danteyu.android_compose_template.constants.PluginsConst.GOOGLE_SERVICES_ID
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(GOOGLE_SERVICES_ID)
                apply(FIREBASE_PERFORMANCE_ID)
                apply(FIREBASE_CRASHLYTICS_ID)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
            dependencies {
                val bom = libs.findLibrary(FIREBASE_BOM).get()
                add(IMPLEMENTATION, platform(bom))
                IMPLEMENTATION(libs.findLibrary(FIREBASE_ANALYTICS).get())
                IMPLEMENTATION(libs.findLibrary(FIREBASE_PERFORMANCE).get())
                IMPLEMENTATION(libs.findLibrary(FIREBASE_CRASHLYTICS).get())
            }

            extensions.configure<ApplicationAndroidComponentsExtension> {
                finalizeDsl {
                    it.buildTypes.forEach { buildType ->
                        buildType.configure<CrashlyticsExtension> {
                            mappingFileUploadEnabled = false
                        }
                    }
                }
            }
        }
    }
}