package city.zouitel.logic

import android.content.Context
import android.widget.Toast

context (Context)
val String.shortToast : () -> Unit
    get() = {
        Toast.makeText(this@Context, this, Toast.LENGTH_SHORT).show()
    }

context (Context)
val String.longToast : () -> Unit
    get() = {
        Toast.makeText(this@Context, this, Toast.LENGTH_LONG).show()
    }

