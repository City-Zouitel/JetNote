import city.zouitel.source.configureAndroidApplication
import city.zouitel.source.configureAndroidCompose
import city.zouitel.source.configureAndroidLibrary
import city.zouitel.source.configureAndroidNavigation
import city.zouitel.source.libs
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidNavigation: Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            extensions.configure<LibraryExtension> {
                configureAndroidNavigation(this)
            }
        }
    }
}