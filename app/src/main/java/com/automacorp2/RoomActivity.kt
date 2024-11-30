package com.automacorp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.automacorp2.model.RoomDto
import com.automacorp2.service.RoomService
import com.automacorp2.ui.theme.Automacorp2Theme

class RoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val param = intent.getStringExtra(MainActivity.ROOM_PARAM)
        val room = RoomService.findByNameOrId(param)

        setContent {
            Automacorp2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (room != null) {
                        RoomDetail(room,Modifier.padding(innerPadding))
                    } else {
                        NoRoom(Modifier.padding(innerPadding))
                    }

                }
            }
        }
    }
}

@Composable
fun RoomDetail(roomDto: RoomDto, modifier: Modifier = Modifier) {
    var room by remember { mutableStateOf(roomDto) }

    Column(modifier = modifier.padding(16.dp)) {
        // Room Name Label and Field
        Text(
            text = stringResource(R.string.act_room_name),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = room.name,
            onValueChange = { room = room.copy(name = it) },
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
            onValueChange = { room = room.copy(targetTemperature = it.toDouble()) },
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

@Preview(showBackground = true)
@Composable
fun RoomDetailPreview() {
    Automacorp2Theme {
        RoomDetail(
            RoomDto(
                id = 1,
                name = "Room A1",
                currentTemperature = 22.0,
                targetTemperature = 24.0,
                windows = emptyList()
            )
        )
    }
}


@Composable
fun NoRoom(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.act_room_none),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
