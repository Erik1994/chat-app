package com.mylearnings.ktorchat.presentation.chat

import com.mylearnings.ktorchat.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
