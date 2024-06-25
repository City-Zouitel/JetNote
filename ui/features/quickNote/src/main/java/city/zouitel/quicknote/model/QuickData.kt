package city.zouitel.quicknote.model

import city.zouitel.systemDesign.CommonConstants.NON

data class QuickData(
    var uid: String = "",
    var title: String? = null,
    var description: String? = null,
    var priority: String = NON,
    var color: Int = 0,
    var textColor: Int = 0x000000,
    var date: String = "",
    var removed: Int = 0,
    var reminding: Long = 0L,
)
