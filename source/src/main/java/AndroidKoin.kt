import city.zouitel.source.androidKoinDependencies
import city.zouitel.source.configureAndroidKoin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidKoin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureAndroidKoin()
        }
    }
}