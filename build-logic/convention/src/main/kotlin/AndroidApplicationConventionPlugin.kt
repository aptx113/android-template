import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.danteyu.android_compose_template.configureKotlinAndroid
import com.danteyu.android_compose_template.configurePrintApksTask
import com.danteyu.android_compose_template.constants.LibsConst.KOTLIN_ANDROID
import com.danteyu.android_compose_template.constants.PluginsConst.ANDROID_APPLICATION_ID
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(ANDROID_APPLICATION_ID)
                apply(KOTLIN_ANDROID)
            }

            extensions.configure<ApplicationExtension>() {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(
                    this
                )
            }
        }
    }
}