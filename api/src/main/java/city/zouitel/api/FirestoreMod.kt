package city.zouitel.api

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirestoreMod {

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun getFirestoreData(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore
        .collection("invalid")

    @Provides
    @Singleton
    fun getSortedFirestoreData(
        firestore: FirebaseFirestore,
    ): Query = firestore
        .collection("invalid")
        .document("ajrMEcJn1lYAb6V1lZqF")
        .collection("english-words") // data
        .orderBy("id", Query.Direction.DESCENDING)
}
