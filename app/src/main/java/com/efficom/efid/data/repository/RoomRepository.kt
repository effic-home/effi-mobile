package com.efficom.efid.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.efficom.efid.data.model.User
import com.efficom.efid.data.model.request.ReservationRequest
import com.efficom.efid.data.model.sealedClass.*
import com.efficom.efid.data.network.RoomApi
import com.google.gson.Gson
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class RoomRepository @Inject constructor(private val roomApi: RoomApi) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    suspend fun getFreeRoom(): RoomApiReturn {

        return try {
            val response = roomApi.getFreeRoom()
            if (response.isSuccessful){
                RoomList(data = response.body()!!)
            }
            else {
                when(response.code()){
                    400 -> BadRequest
                    500 -> NoRoomAvailable
                    else -> GenericError
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            GenericError
        }
    }

    suspend fun getOldReserve(): RoomApiReturn {
        getUserId()?.let {
            return try {
                val response = roomApi.getOldReserve(it.toString())
                if (response.isSuccessful){
                    ReserveList(response.body()!!)
                }else {
                    when(response.code()){
                        400 -> BadRequest
                        500 -> NoReservationAvailable
                        else -> GenericError
                    }
                }
            }catch (e: Exception) {
                Timber.e(e)
                GenericError
            }
        }
        return GenericError
    }

    suspend fun getFreeRoomByDate(date: String): RoomApiReturn {
        return try {
            val response = roomApi.getFreeRoomByDate(date)
            if (response.isSuccessful){
                ReservedRoomList(response.body()!!)
            }else {
                when(response.code()){
                    400 -> BadRequest
                    500 -> NoRoomAvailable
                    else -> GenericError
                }
            }
        }catch (e: Exception) {
            Timber.e(e)
            GenericError
        }
    }

    suspend fun getRoom(): RoomApiReturn{
        return try {
            val response = roomApi.getRoom()
            if (response.isSuccessful){
                RoomList(response.body()!!)
            }else {
                when(response.code()){
                    400 -> BadRequest
                    500 -> NoRoomAvailable
                    else -> GenericError
                }
            }
        }catch (e: Exception) {
            Timber.e(e)
            GenericError
        }
    }

    suspend fun reserveRoom(reservation: ReservationRequest): RoomApiReturn{
        return try {
            val response = roomApi.reserveRoom(reservation)
            if (response.isSuccessful){
                SuccessReserve
            }else {
                when(response.code()){
                    400 -> BadRequest
                    500 -> ReservationFailed
                    else -> GenericError
                }
            }
        }catch (e: Exception){
            Timber.e(e)
            GenericError
        }
    }

    private fun getUserId(): Int?{
        sharedPreferences.getString("user", null).let {
            if (it != null){
                val user: User = Gson().fromJson(it, User::class.java)
                return user.id_user
            }
        }
        return null
    }
}