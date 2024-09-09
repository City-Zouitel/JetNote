import city.zouitel.source.configureComposeVoyager
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ComposeVoyager: Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            extensions.configure<LibraryExtension> {
                configureComposeVoyager(this)
            }
        }
    }
}