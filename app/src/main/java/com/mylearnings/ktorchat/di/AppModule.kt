package com.mylearnings.ktorchat.di

import com.mylearnings.ktorchat.data.mapper.MESSAGE_DTO_TO_MESSAGE_MAPPER
import com.mylearnings.ktorchat.data.remote.ChatSocketService
import com.mylearnings.ktorchat.data.remote.ChatSocketServiceImpl
import com.mylearnings.ktorchat.data.remote.MessageService
import com.mylearnings.ktorchat.data.remote.MessageServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    @Provides
    @Singleton
    fun provideMessageService(client: HttpClient): MessageService {
        return MessageServiceImpl(client = client, mapper = MESSAGE_DTO_TO_MESSAGE_MAPPER)
    }

    @Provides
    @Singleton
    fun provideChatSocketService(client: HttpClient): ChatSocketService {
        return ChatSocketServiceImpl(httpClient = client, mapper = MESSAGE_DTO_TO_MESSAGE_MAPPER)
    }
}