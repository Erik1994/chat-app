package com.mylearnings.ktorchat.presentation.username

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mylearnings.ktorchat.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor(): BaseViewModel() {

    var usernameText by mutableStateOf("")
        private set

    private val _onJoinChat = MutableSharedFlow<String>()
    val onJoinChat = _onJoinChat.asSharedFlow()

    fun onUsernameChange(username: String) {
        usernameText = username
    }

    fun onJoinChat() {
        viewModelScope.launch {
            if (usernameText.isNotBlank()) {
                _onJoinChat.emit(usernameText)
            }
        }
    }
}