package city.zouitel.api

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FirestoreRepoImp @Inject constructor(
    private val query: Query,
    private val usersStore: CollectionReference,
    ): FirestoreRepo {
    override suspend fun getAllEnglishWords(): DataOrException<List<Data>, Exception> {
        val dataOrException = DataOrException<List<Data>, Exception>()
        kotlin.runCatching {
            dataOrException.data = query
                .get()
                .await()
                .map {
                    it.toObject(Data::class.java)
                }
        }.onFailure {
            dataOrException.e = Exception(it.message)
        }
        return dataOrException
    }


    override suspend fun addData(data: Data) {
        this@FirestoreRepoImp.usersStore
            .document("ajrMEcJn1lYAb6V1lZqF")
            .collection("english-words")
            .document(data.data)
            .set(data)
            .await()
    }
}