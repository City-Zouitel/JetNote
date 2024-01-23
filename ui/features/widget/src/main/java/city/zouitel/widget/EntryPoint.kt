package city.zouitel.widget

import city.zouitel.widget.ui.WidgetViewModel

//@EntryPoint
//@InstallIn(SingletonComponent::class)
interface EntryPoint {

    fun vm(): WidgetViewModel
}