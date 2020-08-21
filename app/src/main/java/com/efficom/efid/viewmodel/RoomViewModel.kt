package com.efficom.efid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.efficom.efid.data.model.Reservation
import com.efficom.efid.data.model.Room
import com.efficom.efid.data.model.sealedClass.ErrorRoomApi
import com.efficom.efid.data.model.sealedClass.ReserveList
import com.efficom.efid.data.model.sealedClass.RoomApiReturn
import com.efficom.efid.data.model.sealedClass.RoomList
import com.efficom.efid.data.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(private val app: Application,
    private val roomRepository: RoomRepository): AndroidViewModel(app) {

    lateinit var actualRoom: Room

    private val _error = MutableLiveData<RoomApiReturn>()
    val error = _error

    private val _freeRoomList = MutableLiveData<List<Room>>()
    val freeRoomList = _freeRoomList

    private val _oldReserve = MutableLiveData<List<Reservation>>()
    val oldReserve = _oldReserve

    private val _freeRoomByDate = MutableLiveData<List<Room>>()
    val freeRoomByDate = _freeRoomByDate

    init {
        getOpenRoom()
        getOldReserv()
    }

    fun getOpenRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            roomRepository.getFreeRoom().let {response ->
                when(response){
                    is RoomList -> _freeRoomList.postValue(response.data)
                    is ErrorRoomApi -> _error.postValue(ErrorRoomApi)
                }
            }
        }
    }

    fun getOldReserv(){
        GlobalScope.launch(Dispatchers.IO) {
            roomRepository.getOldReserve().let { response ->
                when(response){
                    is ReserveList -> _oldReserve.postValue(response.data)
                    is ErrorRoomApi -> _error.postValue(ErrorRoomApi)
                }
            }
        }
    }

    fun getFreeRoomByDate(date: String){
        GlobalScope.launch(Dispatchers.IO) {
            roomRepository.getFreeRoomByDate(date).let {response ->
                when(response){
                    is  RoomList -> _freeRoomByDate.postValue(response.data)
                    is  ErrorRoomApi ->_error.postValue(ErrorRoomApi)
                }

            }
        }
    }
}