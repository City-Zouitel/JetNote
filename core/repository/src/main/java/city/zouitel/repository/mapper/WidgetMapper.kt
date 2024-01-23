package city.zouitel.repository.mapper

import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.Note as InNote
import city.zouitel.domain.model.Note as OutNote

class WidgetMapper (
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper
): Mapper.ReadOnly<InNote, OutNote> {
    override fun toDomain(data: InNote): OutNote = with(data) {
        OutNote(
            dataEntity = dataMapper.toDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toDomain(it) },
            taskEntities = taskEntities.map { taskMapper.toDomain(it) },
            linkEntities = linkEntities.map { linkMapper.toDomain(it) }
        )
    }
}