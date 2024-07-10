package com.lovevery.messagemanager.usermessages.data

import com.google.gson.annotations.SerializedName
import com.lovevery.messagemanager.shared.data.MessageResponse

data class UserMessageResponse(
    @SerializedName("user") val user: String,
    @SerializedName("message") val messages: List<MessageResponse>
)
