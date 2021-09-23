package com.tugas.notificationexercise.utils

import com.tugas.notificationexercise.dataclass.PushNotification
import com.tugas.notificationexercise.dataclass.PushNotificationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerInterface {
    @POST("/fcm/send")
    fun sendNotification(
        @Body notification: PushNotification
    ) : Call<PushNotificationResponse>
}