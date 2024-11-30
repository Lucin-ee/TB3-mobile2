package com.automacorp2.model

data class RoomDto(
    val id: Long,
    val name: String,
    val currentTemperature: Double?,
    val targetTemperature: Double?,
    val windows: List<WindowDto>,
)