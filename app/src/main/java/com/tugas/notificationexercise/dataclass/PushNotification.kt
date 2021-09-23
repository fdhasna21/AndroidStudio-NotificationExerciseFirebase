package com.tugas.notificationexercise.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PushNotification(
    @SerializedName("to") var topics : String = "/topics/all",
    @SerializedName("notification") var notification : NotificationData
) : Parcelable {
    @Parcelize
    data class NotificationData (
        @SerializedName("title") var title : String ="",
        @SerializedName("body") var message : String = ""
    ) : Parcelable
}
