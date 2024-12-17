package city.zouitel.generativeai.datasouceImpl

import city.zouitel.generativeai.dao.MessageDao
import city.zouitel.generativeai.mapper.MessageMapper
import city.zouitel.generativeai.model.Message
import city.zouitel.repository.datasource.MessageDataSource
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Message as OutMessage

class MessageDataSourceImpl(
    private val generativeModel: GenerativeModel,
    private val dao: MessageDao,
    private val mapper: MessageMapper
): MessageDataSource {

    override val observeAllMessages: Flow<List<OutMessage>>
        get() = dao.observeAllMessages.map { mapper.toRepo(it) }

    override suspend fun insertMessage(message: OutMessage) {
        dao.insertMessage(mapper.fromRepo(message))

        runCatching {
            generativeModel.generateContent(
                content {
                    /*mapper.fromRepo(message).image?.let { image(it) }*/
                    text(mapper.fromRepo(message).prompt)
                }
            ).run {
                text?.let {
                    dao.insertMessage(Message(isRequest = false, prompt = it))
                } ?: run {
                    dao.insertMessage(Message(isRequest = false, prompt = "No response"))
                }
            }
        }.onFailure {
            dao.insertMessage(Message(isRequest = false, prompt = it.message.toString()))
        }
    }
}