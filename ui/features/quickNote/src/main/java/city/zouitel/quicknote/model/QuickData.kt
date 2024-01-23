package city.zouitel.quicknote.model

import city.zouitel.systemDesign.Cons.NON

data class QuickData(
    var uid: String = "",
    var title: String? = null,
    var description: String? = null,
    var priority: String = NON,
    var color: Int = 0,
    var textColor: Int = 0x000000,
    var date: String = "",
    var trashed: Int = 0,
    var audioDuration: Int = 0,
    var reminding: Long = 0L,
    var imageUrl: String? = null,
    var audioUrl: String? = null,
)
