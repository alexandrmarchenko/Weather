package com.example.weather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connManager?.activeNetworkInfo == null) {
            MaterialAlertDialogBuilder(context)
                .setTitle(context.getString(R.string.warning))
                .setMessage(context.getString(R.string.network_off))
                .show()
        }
    }
}
