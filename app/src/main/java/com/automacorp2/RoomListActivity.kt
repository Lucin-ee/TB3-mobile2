package com.automacorp2

import RoomViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.automacorp2.model.RoomDto
import com.automacorp2.ui.theme.Automacorp2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomListActivity : ComponentActivity() {

    private val viewModel: RoomViewModel by viewModels() // Using ViewModel delegation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Load data using ViewModel when activity is created
        lifecycleScope.launch(Dispatchers.IO) {
            runCatching {
                viewModel.findAll() // Fetch the data in the background
            }.onFailure { exception ->
                exception.printStackTrace() // Log the exception for debugging
                withContext(Dispatchers.Main) {
                    // Show a Toast message on failure
                    Toast.makeText(
                        this@RoomListActivity,
                        "Error loading rooms: ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        // Set content for the activity using Compose
        setContent {
            // Collect the state from the ViewModel as state inside the composable
            val roomsState = viewModel.roomsState.collectAsState().value

            // Handle UI rendering based on the state
            if (roomsState.error != null) {
                // Show an error message and an empty RoomList if there was an error
                Toast.makeText(
                    applicationContext,
                    "Error loading rooms: ${roomsState.error}",
                    Toast.LENGTH_LONG
                ).show()
                RoomList(emptyList(), navigateBack = { finish() }, openRoom = { /* Handle room click */ })
            } else {
                // Show the list of rooms if loaded successfully
                RoomList(roomsState.rooms, navigateBack = { finish() }, openRoom = { /* Handle room click */ })
            }
        }
    }
}

