package com.lovevery.messagemanager.shared.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Header(title: String, onBack: () -> Unit) {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            tint = MaterialTheme.colorScheme.primary,
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onBack()
                })
        Text(
            color = MaterialTheme.colorScheme.primary,
            text = title,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.displaySmall
        )
    }
}