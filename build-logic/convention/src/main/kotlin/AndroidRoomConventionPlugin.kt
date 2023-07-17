import com.android.build.gradle.internal.utils.KSP_PLUGIN_ID
import com.danteyu.android_compose_template.constants.LibsConst.IMPLEMENTATION
import com.danteyu.android_compose_template.constants.LibsConst.LIBS
import com.danteyu.android_compose_template.constants.LibsConst.ROOM_COMPILER
import com.danteyu.android_compose_template.constants.LibsConst.ROOM_KTX
import com.danteyu.android_compose_template.constants.LibsConst.ROOM_RUNTIME
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.process.CommandLineArgumentProvider
import java.io.File

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(KSP_PLUGIN_ID)

            extensions.configure<KspExtension> {
                arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named(LIBS)
            dependencies {
                add(IMPLEMENTATION, libs.findLibrary(ROOM_RUNTIME).get())
                add(IMPLEMENTATION, libs.findLibrary(ROOM_KTX).get())
                add(KSP_PLUGIN_ID, libs.findLibrary(ROOM_COMPILER).get())
            }
        }
    }

    class RoomSchemaArgProvider(
        @get: InputDirectory
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val schemaDir: File
    ) : CommandLineArgumentProvider {
        override fun asArguments(): List<String> = listOf("room.schemaLocation=${schemaDir.path}")
    }
}

