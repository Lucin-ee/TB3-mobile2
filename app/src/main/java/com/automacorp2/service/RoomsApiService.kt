package com.automacorp2.service

import com.automacorp2.model.RoomDto
import com.automacorp2.model.RoomCommandDto
import com.automacorp2.model.WindowDto
import com.automacorp2.model.WindowStatus
import retrofit2.Call
import retrofit2.http.*

interface RoomsApiService {

    // Create a new room
    @POST("rooms")
    fun createRoom(@Body roomDto: RoomCommandDto): Call<RoomDto>

    // Delete a room by its ID
    @DELETE("rooms/{id}")
    fun deleteRoom(@Path("id") id: Long): Call<Void>

    // Get a list of windows for a specific room
    @GET("rooms/{roomId}/windows")
    fun getRoomWindows(@Path("roomId") roomId: Long): Call<List<WindowDto>>

    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/{id}")
    fun findById(@Path("id") id: Long): Call<RoomDto>

    @PUT("rooms/{id}")
    fun updateRoom(
        @Path("id") id: Long,
        @Body roomCommandDto: RoomCommandDto
    ): Call<RoomDto>

    // Update a specific window's status within a room
    @PUT("rooms/{roomId}/windows/{windowId}")
    fun updateWindow(
        @Path("roomId") roomId: Long,
        @Path("windowId") windowId: Long,
        @Body windowDto: WindowDto
    ): Call<WindowDto>
}
