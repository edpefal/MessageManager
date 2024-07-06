package com.lovevery.messagemanager.home.data

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("user") val user: String,
    @SerializedName("operation") val operation: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("message") val message: String
)