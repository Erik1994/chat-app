package com.mylearnings.ktorchat.data.remote

import com.mylearnings.ktorchat.data.remote.dto.MessageDto
import com.mylearnings.ktorchat.domain.model.Message

interface MessageService {
    suspend fun getAllMessages(): List<Message>
}