package city.zouitel.generativeai

import city.zouitel.datastore.DataStoreRepo

class Constants(
    private val dataStoreRepo: DataStoreRepo
) {
    init {
        dataStoreRepo.getLayout
    }

    val c = ""

    companion object {

    }
}