import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.danteyu.android_compose_template.configureJacoco
import com.danteyu.android_compose_template.constants.PluginsConst.ANDROID_APPLICATION_ID
import com.danteyu.android_compose_template.constants.PluginsConst.JACOCO_ID
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(JACOCO_ID)
                apply(ANDROID_APPLICATION_ID)
            }
            val extension = extensions.getByType<ApplicationAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }
}