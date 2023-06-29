package city.zouitel.api

interface FirestoreRepo {

    suspend fun getAllEnglishWords(): DataOrException<List<Info>, Exception>

    suspend fun addData(info: Info)

}