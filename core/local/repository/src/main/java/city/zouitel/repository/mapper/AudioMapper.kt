package city.zouitel.repository.mapper

import city.zouitel.repository.model.Audio
import city.zouitel.domain.model.Audio as OutAudio

/**
 * Mapper class responsible for converting between [Audio] (repository layer representation) and
 * [OutAudio] (domain layer representation) objects.
 */
class AudioMapper {

    /**
     * Converts a list of [Audio] entities to a list of corresponding domain [Audio] models.
     *
     * This function iterates through a list of [Audio] entities and applies the [toDomain(Audio)]
     * conversion function to each entity, resulting in a new list containing the domain
     * representations of those Audios.
     *
     * @param Audios The list of [Audio] entities to convert.
     * @return A list of [Audio] domain models, where each model corresponds to a
     *         converted entity from the input list.
     *
     * @see toDomain(Audio)
     */
    fun toDomain(audios: List<Audio?>) = audios.map { toDomain(it) }

    /**
     * Converts a [Audio] entity repository model to an [OutAudio] domain model.
     *
     * This function maps the properties of a [Audio] object to a corresponding [OutAudio] object,
     * effectively transforming a data-layer representation of a Audio into a domain-layer
     * representation. The mapping includes the Audio's ID, UID, item description, and completion status.
     *
     * @param Audio The [Audio] entity to convert.
     * @return An [OutAudio] domain model representing the provided Audio.
     */
    fun toDomain(audio: Audio?) = OutAudio(
        id = audio?.id ?: 0L,
        uid = audio?.uid ?: "",
        title = audio?.title ?: "",
        path = audio?.path ?: "",
        uri = audio?.uri ?: "",
        size = audio?.size ?: 0L,
        duration = audio?.duration ?: 0L
    )

    /**
     * Converts an [OutAudio] (domain model) to a [Audio] (repository model).
     *
     * This function maps the properties of an [OutAudio] to a new [Audio] instance.
     * It's typically used when transferring data from the domain layer to the
     * data/presentation layer.
     *
     * @param Audio The [OutAudio] object to convert.
     * @return A new [Audio] object with properties copied from the input [OutAudio].
     */
    fun fromDomain(audio: OutAudio) = Audio(
        id = audio.id,
        uid = audio.uid,
        title = audio.title,
        path = audio.path,
        uri = audio.uri,
        size = audio.size,
        duration = audio.duration
    )
}