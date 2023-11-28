package com.mylearnings.ktorchat.data.remote

import com.mylearnings.ktorchat.data.remote.dto.MessageDto
import com.mylearnings.ktorchat.domain.model.Message
import com.mylearnings.ktorchat.util.Mapper
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class MessageServiceImpl(
    private val client: HttpClient,
    private val mapper: Mapper<MessageDto, Message>
): MessageService {
    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get<List<MessageDto>>(Endpoints.GetAllMessages.url).map {
                mapper.map(it)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}