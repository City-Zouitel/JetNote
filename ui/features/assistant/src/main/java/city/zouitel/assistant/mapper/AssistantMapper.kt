package city.zouitel.assistant.mapper

import city.zouitel.assistant.model.Message
import city.zouitel.domain.model.Message as OutMessage

class AssistantMapper {

    fun fromDomain(messages: List<OutMessage>) = messages.map { fromDomain(it) }

    fun toDomain(message: Message) = OutMessage(
        id = message.id,
        isRequest = message.isRequest,
        prompt = message.prompt
    )

    fun fromDomain(message: OutMessage) = Message(
        id = message.id,
        isRequest = message.isRequest,
        prompt = message.prompt
    )
}