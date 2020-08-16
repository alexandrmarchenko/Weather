package com.example.weather

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var messageId = 0

    override fun onMessageReceived(p0: RemoteMessage) {
        var title = p0.notification?.title ?: "Push Message"
        val text = p0.notification?.body

        val builder = NotificationCompat.Builder(this, "2")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text);
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(messageId++, builder.build());
    }

}
