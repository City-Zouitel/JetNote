package city.zouitel.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import city.zouitel.database.utils.Constants.CRYPTIC_PASS
import java.io.File
import java.security.SecureRandom

class Encryption(private val ctx: Context) {

    fun getCrypticPass(): ByteArray {
        val file = File(ctx.filesDir, CRYPTIC_PASS)
        val encryptedFile = EncryptedFile.Builder(
            file,
            ctx,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        return if (file.exists()) {
            encryptedFile.openFileInput().use { it.readBytes() }
        } else {
            generateCryptic().also { cryptic ->
                encryptedFile.openFileOutput().use { it.write(cryptic) }
            }
        }
    }

    private fun generateCryptic(): ByteArray {
        val random = SecureRandom.getInstanceStrong()
        val res = ByteArray(32)

        random.nextBytes(res)
        while (res.contains(0)) {
            random.nextBytes(res)
        }

        return res
    }
}