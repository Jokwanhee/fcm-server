package org.example.fcm_demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service


@Service
class FirebaseCloudMessageService(
    private val objectMapper: ObjectMapper,
) {
    companion object {
        const val API_URL  = "https://fcm.googleapis.com/v1/projects/kap-chat/messages:send"
    }

    private fun getAccessToken(): String {
        val firebaseConfigPath = "firebase/firebase_service_key.json"
        val credentials = GoogleCredentials
            .fromStream(ClassPathResource(firebaseConfigPath).inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
        credentials.refreshIfExpired()
        return credentials.accessToken.tokenValue
    }

    fun sendDirectTo(targetToken: String, title: String, body: String) {
        val message = makeMessage(targetToken, title, body)

        val client = OkHttpClient()
        val requestBody = message
            .toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .addHeader("Authorization", "Bearer ${getAccessToken()}")
            .build()
        val response = client.newCall(request).execute()
    }

    private fun makeMessage(targetToken: String, title: String, body: String): String {
        val notification = Notification(title = title, body = body)
        val data = mapOf("data_title" to "title", "data_type" to "type")
        val message = Message(token = targetToken, data = data)
//        val message = Message(token = targetToken, notification = notification, data = data)
//        val android = Android(org.example.fcm_demo.AndroidNotification("a","b","LOGIN_ACTIVITY"))
//        val message = Message(token = targetToken, notification = notification, data = data, android = android)
        return objectMapper.writeValueAsString(FcmMessage(message = message))
    }
}