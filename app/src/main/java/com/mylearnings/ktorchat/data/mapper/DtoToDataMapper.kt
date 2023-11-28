package com.mylearnings.ktorchat.data.mapper

import com.mylearnings.ktorchat.data.remote.dto.MessageDto
import com.mylearnings.ktorchat.domain.model.Message
import com.mylearnings.ktorchat.util.Mapper
import com.mylearnings.ktorchat.util.formatTimeStamp

val MESSAGE_DTO_TO_MESSAGE_MAPPER: Mapper<MessageDto, Message> = Mapper { dto ->
    dto.run {
        Message(
            text = text,
            formattedTime = timestamp.formatTimeStamp(),
            username = username
        )
    }
}