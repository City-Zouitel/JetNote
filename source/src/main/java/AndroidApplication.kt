import city.zouitel.source.configureAndroidApplication
import city.zouitel.source.configureAndroidCompose
import city.zouitel.source.configureAndroidLibrary
import city.zouitel.source.configureComposeVoyager
import city.zouitel.source.libs
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplication: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = libs.findVersion("targetSdk-version").get().requiredVersion.toInt()

                configureAndroidApplication(this)
                configureAndroidCompose(this)
                configureAndroidLibrary(this)
                configureComposeVoyager(this)
            }
        }
    }
}