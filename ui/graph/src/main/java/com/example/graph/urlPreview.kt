package com.example.graph

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.baha.url.preview.BahaUrlPreview
import com.baha.url.preview.IUrlPreviewCallback
import com.baha.url.preview.UrlInfoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

fun urlPreview(
    ctx: Context,
    scope: CoroutineScope?,
    res: String?,
    url: MutableState<String>?,
    title: MutableState<String>?,
    host: MutableState<String>?,
    img: MutableState<String>?
) = res?.let {
    BahaUrlPreview(it, object : IUrlPreviewCallback {
        override fun onComplete(urlInfo: UrlInfoItem) {
            scope?.launch(Dispatchers.IO) {
                urlInfo.apply {
                    title?.value = this.title
//                    description.value = this.description
                    host?.value = URL(this.url).host
                    url?.value = this.url
                    img?.value = this.image
                }
            }
        }

        override fun onFailed(throwable: Throwable) {
            Toast.makeText(ctx, "Can't load link", Toast.LENGTH_SHORT).show()
        }
    })
}