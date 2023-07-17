import com.android.build.gradle.TestExtension
import com.danteyu.android_compose_template.configureKotlinAndroid
import com.danteyu.android_compose_template.constants.LibsConst.KOTLIN_ANDROID
import com.danteyu.android_compose_template.constants.PluginsConst.ANDROID_TEST_ID
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin :Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply(ANDROID_TEST_ID)
                apply(KOTLIN_ANDROID)
            }
            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 31
            }
        }
    }
}