package city.zouitel.api

interface FirestoreRepo {

    suspend fun getAllEnglishWords(): DataOrException<List<Data>, Exception>

    suspend fun addData(data: Data)

}