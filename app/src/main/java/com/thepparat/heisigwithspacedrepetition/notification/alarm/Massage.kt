package com.thepparat.heisigwithspacedrepetition.notification.alarm

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Massage(
    var title:String,
    val content:String,
    val notificationID:Int
    ):Parcelable
