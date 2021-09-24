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
import com.google.gson.Gson
import com.tugas.notificationexercise.R
import com.tugas.notificationexercise.activity.ShowNotificationActivity
import com.tugas.notificationexercise.activity.ShowAnimalActivity
import com.tugas.notificationexercise.activity.ShowShapeActivity
import com.tugas.notificationexercise.dataclass.NotificationData

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        const val CHANNEL_ID = "channel_id_example_01"
        const val NOTIFICATION_ID = 101
        const val TAG = "FirebaseMessaging"
    }

    override fun onMessageReceived(rm: RemoteMessage) {
        Log.i(TAG, "size ${rm.data.size} ${rm.data}")

        val title : String? = rm.notification!!.title
        val body : String? = rm.notification!!.body
        val data : String = rm.data.toString()
        val dataMap = Gson().fromJson<NotificationData>(data, NotificationData::class.java)
        showNotification(title, body, dataMap)
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        Log.i(TAG, "onMessageSent: $p0")
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i(TAG, "Refreshed token : $p0")
    }

    private fun showNotification(title:String?, message:String?, dataMap:NotificationData?){
        val intent = Intent(baseContext, when(dataMap?.activityReceiver){
            "ShowAnimalActivity" -> ShowAnimalActivity::class.java
            "ShowShapeActivity" -> ShowShapeActivity::class.java
            else -> ShowNotificationActivity::class.java
        }).apply {
                data = Uri.parse("custom://" + System.currentTimeMillis())
                action = "actionString" + System.currentTimeMillis()
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("title", title)
                putExtra("message", message)
                putExtra("data", dataMap)
        }
        Log.i(TAG, "${intent.javaClass}")

        val pendingIntent = PendingIntent.getActivity(baseContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification : Notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notification = NotificationCompat.Builder(baseContext, CHANNEL_ID)
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
            notification = NotificationCompat.Builder(baseContext)
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