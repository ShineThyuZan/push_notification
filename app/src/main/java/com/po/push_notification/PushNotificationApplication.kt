package com.po.push_notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "LocalStore")

class PushNotificationApplication:  Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "JetpackPushNotification"
        val description = "Jetpack Push Notification"
        val important = NotificationManager.IMPORTANCE_DEFAULT

        // Now create notification channel
        // it take there parameters. notification id , name and importance.
        val channel = NotificationChannel("Global", name, important)
        channel.description = description

        // Get notification manager
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel
        notificationManager.createNotificationChannel(channel)
    }
}