package com.tugas.notificationexercise.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tugas.notificationexercise.activity.ShowNotificationActivity
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationData(
    @SerializedName("activity") var activityReceiver : String? = "ShowNotificationActivity",
    @SerializedName("data_animal") var dataAnimal : Animal? = null,
    @SerializedName("data_shape") var dataShape : Shape? = null
) : Parcelable {
    @Parcelize
    data class Animal(
        var name : String? = "",
        var picture : String? = ""
    ): Parcelable

    @Parcelize
    data class Shape(
        var width : Float? = 0f,
        var length : Float? = 0f
    ) : Parcelable
}

