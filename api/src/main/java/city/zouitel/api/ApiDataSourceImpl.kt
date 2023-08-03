package city.zouitel.api

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.FilterOperator
import io.github.jan.supabase.realtime.realtime
import io.github.jan.supabase.supabaseJson
import io.github.jan.supabase.toJsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.decodeToSequence
import kotlinx.serialization.json.internal.readJson
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.json.JSONArray
import java.io.InputStream
import javax.inject.Inject

class ApiDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
): ApiDataSource {

    override fun getAllData() = flow {
            emit(ApiResult.Loading)
            kotlin.runCatching {
                val res = client.postgrest.from("invalid_english").select()
                val invalids = res.decodeList<Invalid>()
                emit(ApiResult.Success(invalids))
            }.onFailure {
                emit(ApiResult.Error(it.message))
            }
        }

    override fun dataModifier(data: Invalid) = flow {
            emit(ApiResult.Loading)
             kotlin.runCatching {
//                 client.postgrest.from("invalid_english").insert(data)
//                 client.postgrest.from("english").update(data) {
//                     eq("id", data.id)
//                 }
//                 client.postgrest.from("english").delete {
//                     filter("id", FilterOperator.EQ, data.id)
//                 }
                 emit(ApiResult.Success(Unit))
             }.onFailure {
                 emit(ApiResult.Error(it.message))
             }
        }
}