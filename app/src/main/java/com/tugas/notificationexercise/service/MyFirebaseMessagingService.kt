package com.tugas.notificationexercise.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tugas.notificationexercise.R
import com.tugas.notificationexercise.dataclass.ShowNotificationActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        const val CHANNEL_ID = "channel_id_example_01"
        const val NOTIFICATION_ID = 101
        const val TAG = "FirebaseMessaging"
    }

    override fun onMessageReceived(rm: RemoteMessage) {
        Log.i(TAG, "From : ${rm.from}")

        if(rm.data.isNotEmpty()){
            val title = rm.data["title"]
            val body = rm.data["body"]
            showNotification(applicationContext, title, body, rm.from)
        } else {
            val title = rm.notification!!.title
            val body = rm.notification!!.body
            showNotification(applicationContext, title, body, rm.from)
        }

        rm.notification?.let{
            Log.i(TAG, "Message : ${it.title} | ${it.body} ")
        }
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        Log.i(TAG, "onMessageSent: $p0")
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i(TAG, "Refreshed token : $p0")
    }

    private fun showNotification(context: Context, title:String?, message:String?, from:String?) {
        val intent = Intent(context, ShowNotificationActivity::class.java).apply {
            data = Uri.parse("custom://" + System.currentTimeMillis())
            action = "actionString" + System.currentTimeMillis()
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("title" ,title)
            putExtra("message", message)
            putExtra("from", from)
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification : Notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build()
            val notificationChannel = NotificationChannel(CHANNEL_ID, title, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        } else {
            notification = NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build()
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}