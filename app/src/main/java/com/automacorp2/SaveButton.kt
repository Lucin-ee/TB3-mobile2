package com.automacorp2

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SaveButton(onSave: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onSave,
        modifier = modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Save",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = "Save Changes")
    }
}
