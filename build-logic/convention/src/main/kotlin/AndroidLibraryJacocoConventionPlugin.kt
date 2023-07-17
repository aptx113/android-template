import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.danteyu.android_compose_template.configureJacoco
import com.danteyu.android_compose_template.constants.PluginsConst.ANDROID_LIBRARY_ID
import com.danteyu.android_compose_template.constants.PluginsConst.JACOCO_ID
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(JACOCO_ID)
                apply(ANDROID_LIBRARY_ID)
            }
            val extension = extensions.getByType<LibraryAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }
}