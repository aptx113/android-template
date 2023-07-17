import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import com.android.build.api.dsl.ApplicationExtension
import com.danteyu.android_compose_template.configureAndroidCompose
import com.danteyu.android_compose_template.constants.PluginsConst.ANDROID_APPLICATION_ID

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(ANDROID_APPLICATION_ID)
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}