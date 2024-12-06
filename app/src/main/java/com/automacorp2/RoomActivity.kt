package com.automacorp2

import RoomViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.automacorp2.model.RoomCommandDto
import com.automacorp2.model.RoomDto
import com.automacorp2.ui.theme.Automacorp2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomActivity : ComponentActivity() {
    private val viewModel: RoomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val param = intent.getStringExtra(MainActivity.ROOM_PARAM)
        param?.let {
            // Ensure that this call is made in the composable context if needed, or call it once in onCreate
            viewModel.findRoom(param.toLongOrNull() ?: -1L)
        }

        setContent {
            Automacorp2Theme {
                Scaffold(
                    topBar = {
                        AutomacorpTopAppBar(
                            title = "Room Details",
                            onNavigationClick = { finish() }
                        )
                    },
                    floatingActionButton = {
                        // Collect state within the composable
                        val room = viewModel.room
                        SaveButton(
                            onSave = {
                                if (room != null) {
                                    viewModel.updateRoom(room.id, room)
                                    Toast.makeText(baseContext, "Room ${room.name} was updated", Toast.LENGTH_LONG).show()
                                    finish() // Close the activity after saving
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    // Collect the state only inside the composable context
                    val room = viewModel.room

                    if (room != null) {
                        RoomDetail(
                            roomViewModel = viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        NoRoom(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
