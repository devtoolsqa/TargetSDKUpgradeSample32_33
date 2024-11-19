package com.example.targetsdkupgradesample32_33.foreground_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat

class PhoneCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        when (state) {
            TelephonyManager.EXTRA_STATE_RINGING -> {
                // Incoming call
                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                startPhoneCallService(context, incomingNumber)
            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                // Call started
            }
            TelephonyManager.EXTRA_STATE_IDLE -> {
                // Call ended
                stopPhoneCallService(context)
            }
        }
        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL) {
            // Outgoing call
            val outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            startPhoneCallService(context, outgoingNumber)
        }
    }

    private fun startPhoneCallService(context: Context, phoneNumber: String?) {
        val serviceIntent = Intent(context, PhoneCallService::class.java)
        serviceIntent.putExtra("phone_number", phoneNumber)
        ContextCompat.startForegroundService(context, serviceIntent)
    }

    private fun stopPhoneCallService(context: Context) {
        val serviceIntent = Intent(context, PhoneCallService::class.java)
        context.stopService(serviceIntent)
    }
}