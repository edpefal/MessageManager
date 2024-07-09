package com.lovevery.messagemanager.addmessage.data

import com.google.gson.annotations.SerializedName

data class AddMessageResponse(
    @SerializedName("user") val user: String? = null,
    @SerializedName("subject") val subject: String? = null,
    @SerializedName("message") val message: String? = null
)
