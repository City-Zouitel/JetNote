//package city.zouitel.api
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.net.ConnectivityManager
//import android.widget.Toast
//
//
//class ApiBroadcastReceiver: BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        val connectivityManager =
//            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
//        val isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting
//        if (isConnected){
//            Toast.makeText(context, "omhfom<dbf< d<dihfo<sdfh<d f<sdf", Toast.LENGTH_SHORT).show()
//        }
//    }
//}