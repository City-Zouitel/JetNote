package city.zouitel.api

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
@InstallIn(SingletonComponent::class)
object FirestoreMod {

    @Provides
    @Singleton
    fun provideTagMapper() = TagMapper()

    @Provides
    @Singleton
    fun provideDataMapper() = DataMapper()

    @Provides
    @Singleton
    fun provideTaskMapper() = TaskMapper()

    @Provides
    @Singleton
    fun provideLinkMapper() = LinkMapper()

    @Singleton
    @Provides
    fun provideNoteMapper(
        dataMapper: DataMapper,
        tagMapper: TagMapper,
        taskMapper: TaskMapper,
        linkMapper: LinkMapper
    ) = NoteMapper(dataMapper, tagMapper, taskMapper, linkMapper)

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
    ): DocumentReference = firestore
        .collection("invalid")
        .document("invalid-words")
}
