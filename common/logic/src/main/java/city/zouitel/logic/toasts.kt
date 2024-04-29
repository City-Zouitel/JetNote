package city.zouitel.logic

import android.content.Context
import android.widget.Toast

context (Context)
fun String.asShortToast() {
        Toast.makeText(this@Context, this, Toast.LENGTH_SHORT).show()
    }

context (Context)
fun String.asLongToast()  {
    Toast.makeText(this@Context, this, Toast.LENGTH_LONG).show()
}
