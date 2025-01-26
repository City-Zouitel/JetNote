package city.zouitel.networkRepo.mapper

import city.zouitel.networkRepo.model.Message
import city.zouitel.domain.model.Message as OutMessage

class MessageMapper {

    fun toDomain(messages: List<Message>) = messages.map { toDomain(it) }

    fun fromDomain(message: OutMessage) = Message(
        id = message.id,
        isRequest = message.isRequest,
        prompt = message.prompt
    )

    fun toDomain(message: Message) = OutMessage(
        id = message.id,
        isRequest = message.isRequest,
        prompt = message.prompt
    )
}