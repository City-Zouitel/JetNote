package city.zouitel.navigation

import android.net.Uri
import android.view.OnReceiveContentListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.EditText
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.core.view.inputmethod.InputConnectionCompat
import city.zouitel.note.DataScreenModel
import org.koin.androidx.compose.koinViewModel

@Deprecated("ender developing!")
@Composable
fun NoteTextField(
    dataScreenModel: DataScreenModel,
    uid: String,
    txtHint: String = "",
    txtSize: Float = 20f,
    forSingleLine: Boolean = false,
    gifUri: MutableState<Uri?>
) {
    val ctx = LocalContext.current
    val internalPath = ctx.filesDir.path
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val editText = @Suppress("all") object : EditText(context) {

                override fun setOnReceiveContentListener(
                    mimeTypes: Array<out String>?,
                    listener: OnReceiveContentListener?
                ) {
                    super.setOnReceiveContentListener(mimeTypes, listener)
                }

                override fun onCreateInputConnection(editorInfo: EditorInfo): InputConnection {
                    val ic = super.onCreateInputConnection(editorInfo)
                    EditorInfoCompat.setContentMimeTypes(editorInfo, arrayOf("*/*"))

                    val callback =
                        InputConnectionCompat.OnCommitContentListener { inputContentInfo, _, _ ->
                            runCatching {
                                inputContentInfo.requestPermission()
                            }.onFailure { return@OnCommitContentListener false }

                            val uri = inputContentInfo.contentUri
                            gifUri.value = uri

                            // gif
//                                dataScreenModel.saveGifLocally(ctx,uri,id)

//                            val bitImg = BitmapFactory.decodeFile(uri.path)
//                            val img = dataScreenModel.decodeBitmapImage(bitImg,uri,ctx)

                            // png
//                            dataScreenModel.saveImageLocally(img,"$internalPath/$IMG_DIR", "$id.$JPEG")

                            true
                        }
                    return InputConnectionCompat.createWrapper(ic, editorInfo, callback)
                }
            }
            editText.apply {
                textSize = txtSize
                hint = txtHint
                isSingleLine = forSingleLine
                setBackgroundResource(android.R.color.transparent)

            }
        }) {
    }
}