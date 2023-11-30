package com.mylearnings.ktorchat.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mylearnings.ktorchat.data.remote.ChatSocketService
import com.mylearnings.ktorchat.data.remote.MessageService
import com.mylearnings.ktorchat.presentation.common.BaseViewModel
import com.mylearnings.ktorchat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    var messageText by mutableStateOf("")
        private set

    var chatState by mutableStateOf(ChatState())
        private set

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    init {
        getAllMessages()
        connect()
    }

    fun onMessageSent(message: String) {
        messageText = message
    }

    fun connect() {
        savedStateHandle.get<String>(USERNAME)?.let {
            viewModelScope.launch {
                when (val result = chatSocketService.initSession(it)) {
                    is Resource.Success -> {
                        chatSocketService.observeMessage()
                            .onEach {message ->  
                                val newList = chatState.messages.toMutableList().apply {
                                    add(0,message)
                                }
                                chatState = chatState.copy(
                                    messages = newList
                                )
                            }
                    }

                    is Resource.Error -> {
                        _toastEvent.emit(result.message ?: "Unknown error")
                    }
                }
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            chatSocketService.closeSession()
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            if (messageText.isNotBlank()) {
                chatSocketService.sendMessage(message = messageText)
            }
        }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            chatState = chatState.copy(isLoading = true)
            val messages = messageService.getAllMessages()
            chatState = chatState.copy(
                messages = messages,
                isLoading = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }

    private companion object {
        const val USERNAME = "username"
    }
}