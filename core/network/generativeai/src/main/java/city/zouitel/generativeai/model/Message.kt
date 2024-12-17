package city.zouitel.generativeai.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.generativeai.model.Message.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Message(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "isRequest", defaultValue = "false") val isRequest: Boolean,
    /*@ColumnInfo() val image: Bitmap? = null,*/
    @ColumnInfo(name = "prompt", defaultValue = "") val prompt: String
) {
    companion object {
        const val TABLE_NAME = "messages_table"
    }
}