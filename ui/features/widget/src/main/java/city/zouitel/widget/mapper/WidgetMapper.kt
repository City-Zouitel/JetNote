package city.zouitel.widget.mapper

import city.zouitel.note.mapper.DataMapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.widget.model.WidgetNote
import city.zouitel.domain.model.Note as OutNote

class WidgetMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper
) {
    fun fromDomain(notes: List<OutNote>) = notes.map { fromDomain(it) }

    private fun fromDomain(note: OutNote): WidgetNote = with(note){
        WidgetNote(
            data = dataMapper.fromDomain(this@with.data),
            tags = tags.map { tagMapper.fromDomain(it) }
        )
    }
}