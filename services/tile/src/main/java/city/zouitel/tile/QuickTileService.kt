package city.zouitel.tile

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import city.zouitel.quicknote.QuickActivity

class QuickTileService: TileService() {

    @SuppressLint("StartActivityAndCollapseDeprecated", "NewApi")
    override fun onClick() {
        super.onClick()
        val intent = Intent(applicationContext, QuickActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        intent.putExtra("quick_note", true)

        val pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_IMMUTABLE)
        startActivityAndCollapse(pendingIntent)

        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
    }
}