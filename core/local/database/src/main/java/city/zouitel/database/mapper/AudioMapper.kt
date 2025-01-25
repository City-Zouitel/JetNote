package city.zouitel.database.mapper

import city.zouitel.database.model.Audio
import city.zouitel.database.utils.Constants.DEFAULT_TXT
import city.zouitel.repository.model.Audio as OutAudio

/**
 * Mapper class responsible for converting between [Audio] (domain model) and [OutAudio] (repository model).
 *
 * This class provides methods to transform a list of tasks or a single Audio
 * between the domain and repository representations.
 */
class  AudioMapper {

    /**
     * Maps a list of Audio objects to a list of their repository representations.
     *
     * @param Audios The list of Audios objects to map.
     * @return A list containing the repository representations of the given Audios
     * that are mapped from Audio to OutAudio.
     */
    fun toRepo(audios: List<Audio?>) = audios.map { toRepo(it) }

    /**
     * Maps a Task object to an OutAudio object.
     *
     * This function converts a Task object, which is typically used internally,
     * to an OutAudio object, which is likely used for external communication or data transfer.
     *
     * @param Audio The input Audio object to be mapped.
     * @return An OutAudio object representing the mapped Audio.
     */
    fun toRepo(audio: Audio?) = OutAudio(
        id = audio?.id ?: 0L,
        uid = audio?.uid ?: DEFAULT_TXT,
        title = audio?.title ?: DEFAULT_TXT,
        path = audio?.path ?: DEFAULT_TXT,
        uri = audio?.uri ?: DEFAULT_TXT,
        size = audio?.size ?: 0L,
        duration = audio?.duration ?: 0L
    )

    /**
     * Maps an OutAudio data object from the repository layer to a Audio data object for the repository layer.
     *
     * @param Audio The OutAudio object to map.
     * @return A Audio object representing the same data.
     */
    fun fromRepo(audio: OutAudio) = Audio(
        id = audio.id,
        uid = audio.uid,
        title = audio.title,
        path = audio.path,
        uri = audio.uri,
        size = audio.size,
        duration = audio.duration
    )
}