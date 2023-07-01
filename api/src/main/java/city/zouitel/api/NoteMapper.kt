package city.zouitel.api

import city.zouitel.api.Note as InNote
import com.example.domain.model.Note as OutNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper
): Mapper.ReadOnly<InNote, OutNote> {
    override fun toView(data: OutNote): InNote = with(data){
        InNote(
            dataEntity = dataMapper.toView(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toView(it) },
            taskEntities = taskEntities.map { taskMapper.toView(it) },
            linkEntities = linkEntities.map { linkMapper.toView(it) }
        )
    }
}