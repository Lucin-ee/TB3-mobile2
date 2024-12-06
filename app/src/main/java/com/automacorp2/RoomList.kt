package com.automacorp2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.automacorp2.model.RoomDto
import com.automacorp2.ui.theme.Automacorp2Theme
import androidx.compose.foundation.lazy.items

@Composable
fun RoomList(
    rooms: List<RoomDto>,
    navigateBack: () -> Unit,
    openRoom: (id: Long) -> Unit
) {
    Automacorp2Theme {
        Scaffold(
            topBar = { AutomacorpTopAppBar("Rooms", navigateBack) }
        ) { innerPadding ->
            if (rooms.isEmpty()) {
                Text(
                    text = "No rooms found",
                    modifier = Modifier.padding(innerPadding)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(innerPadding),
                ) {
                    items(rooms, key = { it.id }) { room ->
                        RoomItem(
                            room = room,
                            modifier = Modifier.clickable { openRoom(room.id) },
                        )
                    }
                }
            }
        }
    }
}

class RoomList(
    val rooms: List<RoomDto> = emptyList(),
    val error: String? = null
)