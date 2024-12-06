package com.automacorp2

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.automacorp2.RoomList

@Composable
fun RoomListScreenWithTopAppBar(
    roomsState: RoomList,
    onNavigationClick: () -> Unit,
    openRoom: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            AutomacorpTopAppBar(
                title = "Room List",
                onNavigationClick = onNavigationClick
            )
        }
    ) { innerPadding ->
        RoomListScreen(
            roomsState = roomsState,
            navigateBack = onNavigationClick,
            openRoom = openRoom
            // No modifier here unless needed for this specific screen
        )
    }
}


