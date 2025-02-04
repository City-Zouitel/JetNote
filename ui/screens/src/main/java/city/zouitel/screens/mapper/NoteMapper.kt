package city.zouitel.screens.mapper

import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.model.Note
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.domain.model.Note as OutNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper
) {
    fun fromDomain(notes: List<OutNote>) = notes.map { fromDomain(it) }

    private fun fromDomain(note: OutNote): Note = with(note){
        Note(
            data = dataMapper.fromDomain(this@with.data),
            tags = tags.map { tagMapper.fromDomain(it) }
        )
    }
}