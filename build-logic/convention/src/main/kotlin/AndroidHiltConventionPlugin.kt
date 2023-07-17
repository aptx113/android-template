import com.danteyu.android_compose_template.constants.LibsConst.HILT_ANDROID
import com.danteyu.android_compose_template.constants.LibsConst.HILt_COMPILER
import com.danteyu.android_compose_template.constants.LibsConst.IMPLEMENTATION
import com.danteyu.android_compose_template.constants.LibsConst.KAPT
import com.danteyu.android_compose_template.constants.LibsConst.KAPT_ANDROID_TEST
import com.danteyu.android_compose_template.constants.LibsConst.LIBS
import com.danteyu.android_compose_template.constants.PluginsConst.HILT_PLUGIN_ID
import com.danteyu.android_compose_template.constants.PluginsConst.KAPT_PLUGIN_ID
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(HILT_PLUGIN_ID)
                apply(KAPT_PLUGIN_ID)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
            dependencies {
                IMPLEMENTATION(libs.findLibrary(HILT_ANDROID).get())
                KAPT(libs.findLibrary(HILt_COMPILER).get())
                KAPT_ANDROID_TEST(libs.findLibrary(HILt_COMPILER).get())
            }
        }
    }
}