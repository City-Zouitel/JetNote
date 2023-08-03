package city.zouitel.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://jpjzlztvqxgbdiwphqre.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpwanpsenR2cXhnYmRpd3BocXJlIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTAxMjM2NTMsImV4cCI6MjAwNTY5OTY1M30.Pw6gViRrVOkxBhu8rXmKY7n66JPItn3NsRkhrY6mqWo"
    ) {
        install(Postgrest)
        install(GoTrue)
        install(Realtime)
    }

    @Provides
    @Singleton
    fun provideDataSource(client: SupabaseClient) = ApiDataSourceImpl(client)

    @Provides
    @Singleton
    fun provideApiRepositoryImpl(dataSourceImpl: ApiDataSourceImpl) =
        ApiRepositoryImpl(dataSourceImpl)
}