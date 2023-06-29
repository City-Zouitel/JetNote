package city.zouitel.api

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepoImp @Inject constructor(
    private val query: Query,
    private val usersStore: CollectionReference,
    ): FirestoreRepo {
    override suspend fun getAllEnglishWords(): DataOrException<List<Info>, Exception> {
        val infoOrException = DataOrException<List<Info>, Exception>()
        kotlin.runCatching {
            infoOrException.data = query
                .get()
                .await()
                .map {
                    it.toObject(Info::class.java)
                }
        }.onFailure {
            infoOrException.e = Exception(it.message)
        }
        return infoOrException
    }


    override suspend fun addData(info: Info) {
        this@FirestoreRepoImp.usersStore
            .document("ajrMEcJn1lYAb6V1lZqF")
            .collection("english-words")
            .document(info.data ?: "null")
            .set(info)
            .await()
    }
}