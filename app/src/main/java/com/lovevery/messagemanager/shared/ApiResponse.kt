package com.lovevery.messagemanager.shared

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("statusCode") val statusCode: Int? = null,
    @SerializedName("body") val body: String? = null
)
