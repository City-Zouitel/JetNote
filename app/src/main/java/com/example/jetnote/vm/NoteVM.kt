package com.example.jetnote.vm

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target.*
import com.bumptech.glide.request.transition.Transition
import com.example.jetnote.cons.IMAGE_FILE
import com.example.jetnote.cons.JPEG
import com.example.domain.reposImpl.NoteRepoImp
import com.example.local.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.URL
import java.nio.channels.FileChannel
import javax.inject.Inject


@HiltViewModel
class NoteVM @Inject constructor(
    private val repo: NoteRepoImp
):ViewModel() {

    var isProcessing by mutableStateOf(false)
        private set

    // for putting the note changes on Notes EntityState (the instance of Node Note class).
    private var noteState by mutableStateOf(listOf<Any>())

    // for add a note from NoteEntityState as it to NoteEntity class.
    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            noteState = noteState + note
            repo.addNote(note)
            isProcessing = false
        }
    }

    // for updateNote a note from NoteEntityState and put it to NoteEntity class,
    // depending on changes.
    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            repo.editNote(note)
            isProcessing = false
        }
    }

    // for deleting a note by the uid.
    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            noteState = noteState - note
            repo.deleteNote(note)
            isProcessing = false
        }
    }

    //delete all trashed notes.
    fun eraseTrash() {
        viewModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            repo.deleteAllTrashedNotes()
            isProcessing = false
        }
    }

    //
    fun saveImageLocally(img:Bitmap?, path:String, name:String?) {
            FileOutputStream(
                name?.let { File(path, it) }
            ).use {
                img?.compress(Bitmap.CompressFormat.JPEG, 100, it)
                it.flush()
            }
    }

    fun saveGifLocally(ctx: Context, gifUri: Uri,uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                Glide.with(ctx)
                    .asFile()
                    .load("")
                    .apply(
                        RequestOptions()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(SIZE_ORIGINAL)
                    )
                    .into(object : com.bumptech.glide.request.target.Target<File?> {
                        override fun onResourceReady(
                            resource: File,
                            transition: Transition<in File?>?
                        ) {
                            storeImage(ctx, resource,uid)
                        }

                        override fun onStart() {
                        }

                        override fun onStop() {
                        }

                        override fun onDestroy() {
                        }

                        override fun onLoadStarted(placeholder: Drawable?) {
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }

                        override fun getSize(cb: SizeReadyCallback) {
                        }

                        override fun removeCallback(cb: SizeReadyCallback) {
                        }

                        override fun setRequest(request: Request?) {
                        }

                        override fun getRequest(): Request? {
                            return null
                        }

                    })
            }.onFailure {
                Toast.makeText(ctx, "something wrong!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun storeImage(ctx: Context,image: File,uid: String) {
        val pictureFile = getOutputMediaFile() ?: return
        try {
            val output = FileOutputStream(pictureFile)
            val input = FileInputStream(image)
            val inputChannel: FileChannel = input.channel
            val outputChannel: FileChannel = output.channel
            inputChannel.transferTo(0, inputChannel.size(), outputChannel)
            output.close()
            input.close()
            Toast.makeText(ctx, "Image Downloaded", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getOutputMediaFile(): File? {
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString()
        )
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) return null
        }
        return File(mediaStorageDir.path + File.separator + "land" + ".gif")
    }


    fun saveAudioLocally(link: String, path: String,name:String) {
        viewModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            URL(link).openStream().use { input ->
                FileOutputStream(File(path, name)).use { output ->
                    input.copyTo(output)
                }
            }
            isProcessing = false
        }
    }
    //
    fun decodeBitmapImage (img: MutableState<Bitmap?>?, photo: MutableState<Bitmap?>?, uri: Uri, c:Context){
        if (Build.VERSION.SDK_INT < 28) {
            runCatching {
                img?.value = MediaStore.Images.Media.getBitmap(c.contentResolver,uri)
            }
        } else {
            runCatching {
                val source = ImageDecoder.createSource(c.contentResolver,uri)
                img?.value = ImageDecoder.decodeBitmap(source)
            }
        }
        photo?.value?.let { img?.value = it }
    }

    fun imageDecoder(context: Context, uid:String): ImageBitmap? {
        val path = File(context.filesDir.path + "/" + IMAGE_FILE, "$uid.$JPEG")
        val bitImg = BitmapFactory.decodeFile(path.absolutePath)
        return bitImg?.asImageBitmap()
    }

    private fun saveMediaImageToStorage(bitmap: Bitmap,ctx:Context) {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            ctx.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }
}

