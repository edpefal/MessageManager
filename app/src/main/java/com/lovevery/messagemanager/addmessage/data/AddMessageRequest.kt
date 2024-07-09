package com.lovevery.messagemanager.addmessage.data

import com.google.gson.annotations.SerializedName

data class AddMessageRequest(
    @SerializedName("user") val user: String? = null,
    @SerializedName("operation") val operation: String? = null,
    @SerializedName("subject") val subject: String? = null,
    @SerializedName("message") val message: String? = null
)
