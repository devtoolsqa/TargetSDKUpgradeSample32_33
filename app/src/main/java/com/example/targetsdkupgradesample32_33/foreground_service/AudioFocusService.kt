package com.example.targetsdkupgradesample32_33.foreground_service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.getSystemService
import com.example.targetsdkupgradesample32_33.R


@RequiresApi(Build.VERSION_CODES.O)
class AudioFocusService : Service() {

    private val audioManager: AudioManager by lazy { getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private val audioFocusRequest: AudioFocusRequest by lazy {
        AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setOnAudioFocusChangeListener(audioFocusChangeListener)
            .build()
    }

    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                // Resume playback
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                // Stop playback
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // Pause playback
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                // Lower the volume
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
        requestAudioFocus()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        abandonAudioFocus()
        stopForeground(true)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun createNotification(): Notification {
        // Create a notification channel (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Audio Playback")
            .setContentText("Playing audio in the background")
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your icon
            .setOngoing(true)

        return notificationBuilder.build()
    }

    private fun requestAudioFocus() {
        audioManager.requestAudioFocus(audioFocusRequest)
    }

    private fun abandonAudioFocus() {
        audioManager.abandonAudioFocus(audioFocusChangeListener)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "audio_playback_channel"
        private const val CHANNEL_NAME = "Audio Playback"
    }
}