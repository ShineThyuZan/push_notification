package com.po.push_notification

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val ONESIGNAL_APP_ID = "40b84669-4664-4b9d-a734-1470e5848f4e"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** One signal setup*/
        oneSignalSetup()

        /** Firebase setup*/
        FirebaseApp.initializeApp(this)
        firebaseMessageTokenSetUp()

        setContentView(R.layout.activity_main)
    }

    private fun firebaseMessageTokenSetUp() {
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        // Get the FCM registration token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                // Log the token (for debugging purposes)
                Log.d("token.main.succ", token ?: "Token is null")
            } else {
                // Handle the error
                Log.e("token.main.error", "Error getting token: ${task.exception?.message}")
            }
        }
    }

    private fun oneSignalSetup() {
        OneSignal.Debug.logLevel = LogLevel.VERBOSE
        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }
    }
}