package com.automacorp2

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.automacorp2.model.RoomDto
import com.automacorp2.model.WindowDto
import com.automacorp2.model.WindowStatus
import com.automacorp2.service.RoomService
import com.automacorp2.ui.theme.Automacorp2Theme
import com.automacorp2.RoomList

@Composable
fun RoomListScreen(roomsState: RoomList, navigateBack: () -> Unit, openRoom: (Long) -> Unit) {
    if (roomsState.error != null) {
        // Display an error message if there is an error
        Toast.makeText(
            LocalContext.current,
            "Error loading rooms: ${roomsState.error}",
            Toast.LENGTH_LONG
        ).show()
        RoomList(emptyList(), navigateBack, openRoom)
    } else {
        // Display the list of rooms if successfully loaded
        RoomList(roomsState.rooms, navigateBack, openRoom)
    }
}

@Composable
fun RoomListScreenPreview() {
    // Sample data for preview
    val rooms = listOf(
        RoomDto(
            id = 1,
            name = "Living Room",
            currentTemperature = 22.5,
            targetTemperature = 23.0,
            windows = listOf(
                WindowDto(
                    id = 1,
                    name = "Window 1",
                    roomName = "Living Room",
                    roomId = 1,
                    windowStatus = WindowStatus.OPENED
                ),
                WindowDto(
                    id = 2,
                    name = "Window 2",
                    roomName = "Living Room",
                    roomId = 1,
                    windowStatus = WindowStatus.CLOSED
                )
            )
        ),
        RoomDto(
            id = 2,
            name = "Bedroom",
            currentTemperature = 19.5,
            targetTemperature = 21.0,
            windows = listOf(
                WindowDto(
                    id = 3,
                    name = "Window 3",
                    roomName = "Bedroom",
                    roomId = 2,
                    windowStatus = WindowStatus.CLOSED
                )
            )
        )
    )

    // Wrap the RoomList composable with MaterialTheme and Surface for consistent styling
    Automacorp2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RoomList(
                rooms = rooms,
                navigateBack = { /* Handle navigate back */ },
                openRoom = { id -> /* Handle room item click */ }
            )
        }
    }
}

