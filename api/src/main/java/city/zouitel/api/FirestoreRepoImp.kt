package city.zouitel.api

import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepoImp @Inject constructor(
//    private val query: Query,
    private val documentReference: DocumentReference,
    private val usersStore: CollectionReference,
    ): FirestoreRepo {
    @Suppress("Unchecked_cast")
    override suspend fun getAllEnglishWords(): ArrayList<String> {
        var infoOrException = ArrayList<String>()
        kotlin.runCatching {
            infoOrException = documentReference
                .get(Source.SERVER)
                .await()
                .get("english") as ArrayList<String>
         }
        return infoOrException
    }


    override suspend fun addData(list: HashMap<String, ArrayList<String>>) {
        kotlin.runCatching {
            this@FirestoreRepoImp.usersStore
                .document("invalid-words")
                .set(list)
                .await()
        }
    }
}