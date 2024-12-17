package city.zouitel.generativeai.mapper

import city.zouitel.generativeai.model.Message
import city.zouitel.repository.model.Message as OutMessage

class MessageMapper {

    fun toRepo(messages: List<Message>) = messages.map { toRepo(it) }

    fun fromRepo(message: OutMessage) = Message(
        id = message.id,
        isRequest = message.isRequest,
        prompt = message.prompt
    )

    fun toRepo(message: Message) = OutMessage(
        id = message.id,
        isRequest = message.isRequest,
        prompt = message.prompt
    )
}