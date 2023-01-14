package com.example.jetnote.ui.add_and_edit

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baha.url.preview.BahaUrlPreview
import com.baha.url.preview.IUrlPreviewCallback
import com.baha.url.preview.UrlInfoItem
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetnote.icons.GLOBE_ICON
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun URLCard(desc: String, itsCard: Boolean) {

    val ctx = LocalContext.current
    val uriHand = LocalUriHandler.current

    val scope = rememberCoroutineScope()

    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val url = remember { mutableStateOf("") }
    val img = remember { mutableStateOf("") }

    var res = ""

    desc.let {
        for (link in it.split(' ')) {
            if (link.matches("https?://.+".toRegex())) res = link
        }
    }

    // TODO: Move this code to vm.
    val urlPreview = BahaUrlPreview(res, object : IUrlPreviewCallback {
        override fun onComplete(urlInfo: UrlInfoItem) {
            scope.launch(Dispatchers.IO) {
                urlInfo.apply {
                    title.value = this.title
                    description.value = this.description
                    url.value = this.url
                    img.value = this.image
                }
            }
        }

        override fun onFailed(throwable: Throwable) {
            Toast.makeText(ctx, "...", Toast.LENGTH_SHORT).show()
        }

    })

    urlPreview.fetchUrlPreview()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (itsCard) 0.dp else 20.dp),
        shape = if (itsCard) roundedForCard else RoundedCornerShape(15.dp),
        onClick = {
            uriHand.openUri(url.value)
        }
    ) {
        Row {
            GlideImage(
                model = img.value,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            ){
                it.placeholder(GLOBE_ICON)
            }

            Column {
                Text(
                    text = title.value,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 5.dp)
                )
                Text(
                    text = description.value,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

val findUrlLink: (String?) -> String? = {
    it?.split(' ')?.find { str -> str.matches("https?://.+".toRegex()) }
}

val roundedForCard = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)
