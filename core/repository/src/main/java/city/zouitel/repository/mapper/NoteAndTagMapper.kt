package city.zouitel.repository.mapper

import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.NoteAndTag as InNoteAndTag
import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag

class NoteAndTagMapper: Mapper.Base<InNoteAndTag, OutNoteAndTag> {
    override fun toRepository(data: OutNoteAndTag): InNoteAndTag = with(data){
        InNoteAndTag(noteUid, labelId)
    }

    override fun toDomain(data: InNoteAndTag): OutNoteAndTag = with(data){
        OutNoteAndTag(noteUid, labelId)
    }
}