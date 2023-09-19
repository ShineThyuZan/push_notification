package com.po.push_notification.firebase

import android.annotation.SuppressLint
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.po.push_notification.dataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TechFirebaseMessageService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("CloudMessage", "From ${message.from}")
        if (message.data.isNotEmpty()) {
            Log.d("CloudMessage", "Message Data ${message.data}")
        }
        // Check if message contains a notification payload
        message.data.let {
            Log.d("CloudMessage", "Message notification Body ${it["body"]}")
        }
        if (message.notification != null) {
            Log.d("CloudMessage", "Notification ${message.notification}")
            Log.d("CloudMessage.Title", "Notification ${message.notification!!.title}")
            Log.d("CloudMessage.Body", "Notification ${message.notification!!.body}")
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        GlobalScope.launch {
            saveGcmToken(token)
        }
    }

    // Save GCM token DataStore Preference
    // You can use to sent it to your server
    @SuppressLint("SuspiciousIndentation")
    private suspend fun saveGcmToken(token: String) {
        val gcmTokenKey = stringPreferencesKey("gcm_token")
            baseContext.dataStore.edit { pref ->
                pref[gcmTokenKey] = token
            }

    }

}