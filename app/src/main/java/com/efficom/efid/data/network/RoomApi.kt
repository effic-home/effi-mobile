package com.efficom.efid.data.network

import com.efficom.efid.data.model.Room
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApi {

    @GET("salles")
    suspend fun getFreeRoom(): Response<List<Room>>

    @GET("salles/date/{date}")
    suspend fun getRoomByDate(@Path("date")date: String): Response<List<Room>>
}