package com.mylearnings.ktorchat.data.remote

const val BASE_URL = "http://192.168.10.27:8001/"

sealed class Endpoints(val url: String) {
    object GetAllMessages: Endpoints("$BASE_URL/messages")
    object ChatSocket: Endpoints("$BASE_URL/chat-socket")
}