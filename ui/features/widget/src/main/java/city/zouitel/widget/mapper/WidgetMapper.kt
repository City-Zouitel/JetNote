package city.zouitel.widget.mapper

import city.zouitel.links.mapper.LinkMapper
import city.zouitel.note.mapper.DataMapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.widget.model.WidgetNote
import city.zouitel.domain.model.Note as OutNote

class WidgetMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val linkMapper: LinkMapper
) {
    fun fromDomain(notes: List<OutNote>) = notes.map { fromDomain(it) }

    fun fromDomain(data: OutNote): WidgetNote = with(data){
        WidgetNote(
            dataEntity = dataMapper.fromDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.fromDomain(it) },
            linkEntities = linkEntities.map { linkMapper.fromDomain(it) }
        )
    }
}