package com.example.weather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.warning))
            .setMessage(context.getString(R.string.low_battery))
            .show()
    }
}
