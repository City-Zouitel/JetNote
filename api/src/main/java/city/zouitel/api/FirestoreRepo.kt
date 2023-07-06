package city.zouitel.api

interface FirestoreRepo {

    suspend fun getAllEnglishWords(): ArrayList<String>

    suspend fun addData(list: HashMap<String, ArrayList<String>>)

}