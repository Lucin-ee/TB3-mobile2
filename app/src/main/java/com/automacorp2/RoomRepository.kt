package com.automacorp2

import com.automacorp2.model.RoomDto
import com.automacorp2.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomRepository {

    fun getAllRooms() {
        val call = RetrofitInstance.api.findAll()
        call.enqueue(object : Callback<List<RoomDto>> {
            override fun onResponse(call: Call<List<RoomDto>>, response: Response<List<RoomDto>>) {
                if (response.isSuccessful) {
                    val rooms = response.body()
                    // Handle the response
                } else {
                    // Handle API error
                }
            }

            override fun onFailure(call: Call<List<RoomDto>>, t: Throwable) {
                // Handle network failure
            }
        })
    }
}
