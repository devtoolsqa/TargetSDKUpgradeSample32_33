package com.example.targetsdkupgradesample32_33.foreground_service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.targetsdkupgradesample32_33.R
import com.example.targetsdkupgradesample32_33.media.MediaActivity


class PhoneCallService : Service() {

    private val CHANNEL_ID = "phone_call_channel"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val phoneNumber = intent?.getStringExtra("phone_number")

        createNotificationChannel()

        val notification = createNotification(phoneNumber)
        startForeground(1, notification)

        // Your call handling logic here

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Phone Call Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(phoneNumber: String?): Notification {
        val notificationIntent = Intent(this, MediaActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Ongoing Call")
            .setContentText(phoneNumber ?: "Unknown Number")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setOngoing(true) // Make the notification non-dismissable

        return builder.build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cleanup, if necessary
    }
}