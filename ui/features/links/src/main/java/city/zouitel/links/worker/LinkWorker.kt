package city.zouitel.links.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.utils.Constants.DEFAULT_INT
import city.zouitel.domain.utils.Constants.DEFAULT_TXT
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.links.model.Link
import city.zouitel.links.utils.Constants.DESCRIBE
import city.zouitel.links.utils.Constants.ICON
import city.zouitel.links.utils.Constants.IMG
import city.zouitel.links.utils.Constants.LINK_ID
import city.zouitel.links.utils.Constants.TITLE
import city.zouitel.links.utils.Constants.UID
import city.zouitel.links.utils.Constants.URL
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LinkWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val ioDeprecated: CoroutineDispatcher,
    private val insert: LinkUseCase.Insert,
    private val linkMapper: LinkMapper,
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(ioDeprecated) {
        return@withContext try {
            val id_data = inputData.getInt(LINK_ID, DEFAULT_INT)
            val uid_data = inputData.getString(UID) ?: DEFAULT_TXT
            val url_data = inputData.getString(URL) ?: DEFAULT_TXT
            val title_data = inputData.getString(TITLE) ?: DEFAULT_TXT
            val description_data = inputData.getString(DESCRIBE) ?: DEFAULT_TXT
            val image_data = inputData.getString(IMG) ?: DEFAULT_TXT
            val icon_data = inputData.getString(ICON) ?: DEFAULT_TXT

            insert.invoke(
               linkMapper.toDomain(
                   Link(
                       id = id_data,
                       uid = uid_data,
                       url = url_data,
                       title = title_data,
                       description = description_data,
                       image = image_data,
                       icon = icon_data
                   )
               )
            )

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    }
}