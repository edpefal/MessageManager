package com.lovevery.messagemanager.shared.data

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("subject") val subject: String? = null,
    @SerializedName("message") val message: String? = null
)