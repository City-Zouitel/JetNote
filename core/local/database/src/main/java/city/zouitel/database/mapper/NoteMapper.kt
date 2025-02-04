package city.zouitel.database.mapper

import city.zouitel.database.model.Note
import city.zouitel.repository.model.Note as OutNote

/**
 * Mapper class responsible for converting between [Note] entities and their repository ([OutNote]) representations.
 *
 * This class utilizes [DataMapper] and [TagMapper] to handle the mapping of nested data and tags within a [Note].
 *
 * @property dataMapper The [DataMapper] instance used to map the `data` field of a [Note].
 * @property tagMapper The [TagMapper] instance used to map the `tags` associated with a [Note].
 */
class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper
) {
    /**
     * Maps a list of Data objects to a list of their repository representations.
     *
     * @param Datas The list of Datas objects to map.
     * @return A list containing the repository representations of the given Datas
     * that are mapped from Data to OutData.
     */
    fun toRepo(notes: List<Note>) = notes.map { toRepo(it) }

    /**
     * Maps a Task object to an OutData object.
     *
     * This function converts a Task object, which is typically used internally,
     * to an OutData object, which is likely used for external communication or data transfer.
     *
     * @param Data The input Data object to be mapped.
     * @return An OutData object representing the mapped Data.
     */
    fun toRepo(data: Note): OutNote = with(data){
        OutNote(
            data = dataMapper.toRepo(this.data),
            tags = tags.map { tagMapper.toRepo(it) }
        )
    }
}