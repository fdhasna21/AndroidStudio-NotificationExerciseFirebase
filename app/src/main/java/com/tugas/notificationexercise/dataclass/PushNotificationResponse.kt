package com.tugas.notificationexercise.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PushNotificationResponse(
    @SerializedName("message_id") var message_id : Long? = null
) : Parcelable
