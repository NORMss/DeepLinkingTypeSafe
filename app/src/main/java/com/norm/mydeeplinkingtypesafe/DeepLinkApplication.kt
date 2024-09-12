package com.norm.mydeeplinkingtypesafe

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import androidx.core.net.toUri

class DeepLinkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChanel()
        showNotification()
    }

    private fun createNotificationChanel() {
        val chanel = NotificationChannel(
            "chanel_id",
            "chanel_name",
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.createNotificationChannel(chanel)
    }

    private fun showNotification() {
        val activityIntent = Intent(this, MainActivity::class.java).apply {
//            data = "https://github.com/normss/34?optional=43".toUri()
            data = "https://$DEEP_LINK_DOMAIN/34".toUri()
        }
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(activityIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }
        val notification = NotificationCompat.Builder(this, "chanel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("App Lunched")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.notify(1, notification)
    }
}