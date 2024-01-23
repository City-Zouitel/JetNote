package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.database.model.relational.NoteEntity as InNote
import city.zouitel.repository.model.Note as OutNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper
): Mapper.ReadOnly<InNote, OutNote> {
    override fun readOnly(data: InNote): OutNote = with(data){
        OutNote(
            dataEntity = dataMapper.readOnly(dataEntity),
            tagEntities = tagEntities.map { tagMapper.readOnly(it) },
            taskEntities = taskEntities.map { taskMapper.readOnly(it) },
            linkEntities = linkEntities.map { linkMapper.readOnly(it) }
        )
    }
}