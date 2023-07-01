package com.example.domain.model

import androidx.annotation.Keep
import com.example.domain.utils.Constants.NON

@Keep
data class Data(
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
    var isVerified: Boolean = false
)
