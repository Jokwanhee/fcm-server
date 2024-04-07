package org.example.fcm_demo

data class MessageDto(
    val targetToken: String,
    val title: String,
    val body: String
)