import city.zouitel.source.configureAndroidApplication
import city.zouitel.source.configureAndroidCompose
import city.zouitel.source.configureAndroidLibrary
import city.zouitel.source.configureAndroidNavigation
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
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = libs.findVersion("targetsdk-v").get().requiredVersion.toInt()

                configureAndroidApplication(this)
                configureAndroidCompose(this)
                configureAndroidLibrary(this)
                configureAndroidNavigation(this)
            }
        }
    }
}