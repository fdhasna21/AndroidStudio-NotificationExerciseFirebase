package com.tugas.notificationexercise.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 Documentation https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages
**/
@Parcelize
data class PushNotification(
    @SerializedName("to")   var topics : String = "/topics/all",
    @SerializedName("notification") var notification : NotificationAttribute? = null,
    @SerializedName("data") var data : NotificationData? = null
) : Parcelable
