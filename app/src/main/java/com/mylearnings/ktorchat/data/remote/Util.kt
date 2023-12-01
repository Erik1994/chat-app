package com.mylearnings.ktorchat.data.remote

const val BASE_URL = "http://ip-address:port"
const val WEB_SOCKET_BASE_URL = "ws://ip-address:port"

sealed class Endpoints(val url: String) {
    object GetAllMessages: Endpoints("$BASE_URL/messages")
    object ChatSocket: Endpoints("$WEB_SOCKET_BASE_URL/chat-socket")
}