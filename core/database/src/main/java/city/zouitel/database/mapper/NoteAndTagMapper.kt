package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.database.model.NoteAndTagEntity as InNoteAndTag
import city.zouitel.repository.model.NoteAndTag as OutNoteAndTag

class NoteAndTagMapper: Mapper.Base<InNoteAndTag, OutNoteAndTag> {
    override fun toLocal(data: OutNoteAndTag): InNoteAndTag = with(data){
        InNoteAndTag(noteUid, labelId)
    }

    override fun readOnly(data: InNoteAndTag): OutNoteAndTag = with(data){
        OutNoteAndTag(noteUid, labelId)
    }
}