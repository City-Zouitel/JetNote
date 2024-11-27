package city.zouitel.generativeai.di

import com.google.ai.client.generativeai.GenerativeModel
import org.koin.dsl.module

val generativeKoinModule = module {

    single {
        GenerativeModel(
            modelName = "gemini-1.5-flash-latest",
            apiKey = "AIzaSyAsJtZ7sHR-gq0pym_w8aPCrn6X0KnxKZY"
        )
    }
}