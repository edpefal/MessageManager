package com.lovevery.messagemanager.shared

import com.lovevery.messagemanager.shared.data.MessageResponse
import com.lovevery.messagemanager.shared.presentation.MessageModel

fun MessageResponse.toMessageModel() =
    MessageModel(
        subject = this.subject.orEmpty(),
        message = this.message.orEmpty(),
    )