package city.zouitel.tags.mapper

import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag
import city.zouitel.tags.mapper.base.Mapper
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag

class NoteAndTagMapper: Mapper.Base<InNoteAndTag, OutNoteAndTag> {
    override fun toView(data: OutNoteAndTag): InNoteAndTag = with(data) {
        InNoteAndTag(noteUid, labelId)
    }

    override fun toDomain(data: InNoteAndTag): OutNoteAndTag = with(data){
        OutNoteAndTag(noteUid, labelId)
    }
}