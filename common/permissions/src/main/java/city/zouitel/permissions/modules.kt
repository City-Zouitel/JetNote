package city.zouitel.permissions

import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val permissionsDIModule = module {

    single {
        PermissionsController(androidApplication())
    }

    factoryOf(::PermissionScreenModel)
}