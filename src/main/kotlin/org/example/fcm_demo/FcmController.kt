package org.example.fcm_demo

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FcmController(
    private val firebaseCloudMessageService: FirebaseCloudMessageService,
) {
    @PostMapping("/send")
    fun send(@RequestBody messageDto: MessageDto): MessageDto {
        firebaseCloudMessageService.sendDirectTo(
            messageDto.targetToken,
            messageDto.title,
            messageDto.body
        )
        return messageDto
    }
}