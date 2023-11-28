package com.mylearnings.ktorchat.data.remote

import com.mylearnings.ktorchat.domain.model.Message
import com.mylearnings.ktorchat.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {
    suspend fun initSession(
        username: String
    ): Resource<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessage(): Flow<Message>

    suspend fun closeSession()

    companion object {
        const val BASE_URL = "http://192.168.10.27:8001/"
    }

    sealed class Endpoints(val url: String) {
        object GetAllMessages: Endpoints("$BASE_URL/messages")
    }
}