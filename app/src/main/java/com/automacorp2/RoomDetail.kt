package com.automacorp2

import RoomViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.automacorp2.model.RoomDto
import com.automacorp2.ui.theme.Automacorp2Theme

@Composable
fun RoomDetail(roomViewModel: RoomViewModel, modifier: Modifier = Modifier) {
    //var room by remember { mutableStateOf(roomDto) }
    val room = roomViewModel.room!!
    Column(modifier = modifier.padding(16.dp)) {
        // Room Name Label and Field
        Text(
            text = stringResource(R.string.act_room_name),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = room.name,
            onValueChange = { roomViewModel.room = room.copy(name = it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.act_room_name)) }
        )

        // Current Temperature Label and Display
        Text(
            text = stringResource(R.string.act_room_current_temperature),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )
        Text(
            text = "${room.currentTemperature} °C",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Target Temperature Label and Slider
        Text(
            text = stringResource(R.string.act_room_target_temperature),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Slider(
            value = room.targetTemperature?.toFloat() ?: 18.0f, // Default to 18.0f if null
            onValueChange = { roomViewModel.room = room.copy(targetTemperature = it.toDouble()) },
            valueRange = 10f..28f,
            steps = 0,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Slider Value Display
        Text(
            text = "${String.format("%.1f", room.targetTemperature)} °C",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RoomDetailPreview() {
//    Automacorp2Theme {
//        RoomDetail(
//            RoomDto(
//                id = 1,
//                name = "Room A1",
//                currentTemperature = 22.0,
//                targetTemperature = 24.0,
//                windows = emptyList()
//            )
//        )
//    }
//}