package city.zouitel.domain.usecase

import city.zouitel.domain.model.Message
import city.zouitel.domain.repository.MessageRepository

sealed class MessageUseCase {

    data class ObserveAllMessages(private val repository: MessageRepository): MessageUseCase() {
        operator fun invoke() = repository.observeAll
    }

    data class InsertMessage(private val repository: MessageRepository): MessageUseCase() {
        suspend operator fun invoke(message: Message) = repository.insert(message)
    }
}