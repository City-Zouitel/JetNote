package city.zouitel.widget.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.domain.usecase.WidgetUseCase
import city.zouitel.note.mapper.NoteMapper
import city.zouitel.note.model.Note as InNote
import city.zouitel.systemDesign.Cons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class WidgetViewModel: ViewModel(), KoinComponent {
    private val getAll: WidgetUseCase.GetAllWidgetMainEntityById by inject()
    private val mapper: NoteMapper by inject()

//    @WorkerThread
//    fun getAllEntities() = getAll

    // for observing the dataEntity changes.
    private val _allNotesById = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesById: StateFlow<List<InNote>>
        get() = _allNotesById.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            getAll.invoke().collect { list ->
                _allNotesById.value = list.map { note -> mapper.toView(note) }
            }
        }

    }

    @WorkerThread
    fun imageDecoder(context: Context, uid: String): Bitmap {
        val path = File(context.filesDir.path + "/" + Cons.IMAGES, "$uid.${Cons.JPEG}")
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = 4
        return BitmapFactory.decodeFile(path.absolutePath, bitmapOptions)
    }
}