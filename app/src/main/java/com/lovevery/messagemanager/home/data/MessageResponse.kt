package com.lovevery.messagemanager.home.data

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("subject") val subject: String? = null,
    @SerializedName("message") val message: String? = null
)