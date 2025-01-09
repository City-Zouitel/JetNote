package city.zouitel.widget.mapper

import city.zouitel.note.mapper.DataMapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.widget.model.WidgetNote
import city.zouitel.domain.model.Note as OutNote

class WidgetMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
) {
    fun fromDomain(notes: List<OutNote>) = notes.map { fromDomain(it) }

    fun fromDomain(data: OutNote): WidgetNote = with(data){
        WidgetNote(
            dataEntity = dataMapper.fromDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.fromDomain(it) },
        )
    }
}