package org.example.fcm_demo

import com.google.common.collect.ImmutableMap

data class FcmMessage(
    val validate_only: Boolean = false,
    val message: Message
)

data class Message(
//    val notification: Notification,
    val data: Map<String,String>,
//    val android: Android,
    val token: String,
)
data class Notification(
    val title: String,
    val body: String,
)
data class Android(
    val notification: AndroidNotification
)

data class AndroidNotification(
    val title: String,
    val body: String,
    val click_action: String
)