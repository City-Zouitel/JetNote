package city.zouitel.database.mapper

import city.zouitel.database.model.NoteAndTag
import city.zouitel.repository.model.NoteAndTag as OutNoteAndTag

/**
 * A mapper class responsible for converting between repository [NoteAndTag] and database [OutNoteAndTag].
 * This class provides methods for mapping lists of NoteAndTag and individual medias between the two types.
 */
class NoteAndTagMapper {
    /**
     * Maps a list of NoteAndTag objects to a list of their repository representations.
     *
     * @param NoteAndTags The list of NoteAndTags objects to map.
     * @return A list containing the repository representations of the given NoteAndTags
     * that are mapped from NoteAndTag to OutNoteAndTag.
     */
    fun toRepo(notesAndTag: List<NoteAndTag>) = notesAndTag.map { toRepo(it) }

    /**
     * Maps a Task object to an OutNoteAndTag object.
     *
     * This function converts a Task object, which is typically used internally,
     * to an OutNoteAndTag object, which is likely used for external communication or data transfer.
     *
     * @param NoteAndTag The input NoteAndTag object to be mapped.
     * @return An OutNoteAndTag object representing the mapped NoteAndTag.
     */
    private fun toRepo(noteAndTag: NoteAndTag) = OutNoteAndTag(
        uid = noteAndTag.uid,
        id = noteAndTag.id
    )

    /**
     * Maps an OutNoteAndTag data object from the repository layer to a NoteAndTag data object for the repository layer.
     *
     * @param NoteAndTag The OutNoteAndTag object to map.
     * @return A NoteAndTag object representing the same data.
     */
    fun fromRepo(noteAndTag: OutNoteAndTag) = NoteAndTag(
        uid = noteAndTag.uid,
        id = noteAndTag.id
    )
}