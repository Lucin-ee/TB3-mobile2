import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.automacorp2.RoomList
import com.automacorp2.model.RoomCommandDto
import com.automacorp2.model.RoomDto
import com.automacorp2.model.WindowDto
import com.automacorp2.service.RetrofitInstance
import com.automacorp2.service.RoomService.findAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomViewModel : ViewModel() {
    var room by mutableStateOf<RoomDto?>(null)
    private val _roomsState = MutableStateFlow(RoomList())
    val roomsState: StateFlow<RoomList> = _roomsState

    // State for room windows
    private val _roomWindows = MutableStateFlow<List<WindowDto>>(emptyList())
    val roomWindows: StateFlow<List<WindowDto>> = _roomWindows

    // Create a new room
    fun createRoom(roomCommand: RoomCommandDto) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RetrofitInstance.api.createRoom(roomCommand).execute()
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    findAll() // Refresh the list of rooms
                }
            }.onFailure { exception ->
                exception.printStackTrace()
            }
        }
    }

    // Delete a room by its ID
    fun deleteRoom(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RetrofitInstance.api.deleteRoom(id).execute()
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    findAll() // Refresh the list of rooms
                }
            }.onFailure { exception ->
                exception.printStackTrace()
            }
        }
    }

    fun findAll() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RetrofitInstance.api.findAll().execute()
            }.onSuccess { response ->
                val rooms = response.body() ?: emptyList()
                _roomsState.value = RoomList(rooms)
            }.onFailure { exception ->
                exception.printStackTrace()
                _roomsState.value = RoomList(emptyList(), exception.stackTraceToString())
            }
        }
    }

    fun findRoom(id: Long) {
        viewModelScope.launch(context = Dispatchers.IO) {
            runCatching {
                // Call the backend API to get room details by ID
                RetrofitInstance.api.findById(id).execute()
            }.onSuccess { response ->
                // Update the state with the retrieved room details
                room = response.body()
            }.onFailure { exception ->
                exception.printStackTrace()
                // Handle error by setting room to null
                room = null
            }
        }
    }


    // Get the windows for a specific room
    fun getRoomWindows(roomId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RetrofitInstance.api.getRoomWindows(roomId).execute()
            }.onSuccess { response ->
                _roomWindows.value = response.body() ?: emptyList()
            }.onFailure { exception ->
                exception.printStackTrace()
            }
        }
    }

    fun updateRoom(id: Long, roomDto: RoomDto) {
        val command = RoomCommandDto(
            name = roomDto.name,
            targetTemperature = roomDto.targetTemperature?.let { Math.round(it * 10) / 10.0 },
            currentTemperature = roomDto.currentTemperature
        )

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RetrofitInstance.api.updateRoom(id, command).execute()
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    room = response.body()
                    withContext(Dispatchers.Main) {
                        // Optionally update the UI here if needed
                    }
                } else {
                    room = null
                }
            }.onFailure { exception ->
                exception.printStackTrace()
                room = null
            }
        }
    }


    // Update a window in a room
    fun updateWindow(roomId: Long, windowId: Long, updatedWindow: WindowDto) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RetrofitInstance.api.updateWindow(roomId, windowId, updatedWindow).execute()
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    getRoomWindows(roomId) // Refresh windows list
                }
            }.onFailure { exception ->
                exception.printStackTrace()
            }
        }
    }
}
