package com.efficom.efid.data.repository

import android.util.Log
import com.efficom.efid.data.model.sealedClass.ErrorRoomApi
import com.efficom.efid.data.model.sealedClass.RoomApiReturn
import com.efficom.efid.data.model.sealedClass.RoomList
import com.efficom.efid.data.network.RoomApi
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class RoomRepository @Inject constructor(private val roomApi: RoomApi) {

    suspend fun getFreeRoom(): RoomApiReturn {

        return try {
            val response = roomApi.getFreeRoom()
            if (response.isSuccessful){
                RoomList(response.body()!!)
            }
            else {
                ErrorRoomApi
            }
        } catch (e: Exception) {
            Timber.e(e)
            ErrorRoomApi
        }
    }

    suspend fun getRoomByDate(date: String): RoomApiReturn {

        return try {
            val response = roomApi.getRoomByDate(date)
            if (response.isSuccessful){
                response.body()?.let {
                    RoomList(it)
                }
                ErrorRoomApi
            }
            else{
                ErrorRoomApi
            }
        } catch (e: Exception){
            Timber.e(e)
            ErrorRoomApi
        }
    }
}