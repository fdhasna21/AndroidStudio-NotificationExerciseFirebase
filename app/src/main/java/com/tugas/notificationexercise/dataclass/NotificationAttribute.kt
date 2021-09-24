package com.tugas.notificationexercise.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationAttribute (
    @SerializedName("title") var title : String? ="",
    @SerializedName("body") var message : String? = "",
    @SerializedName("image") var image : String? = ""
) : Parcelable