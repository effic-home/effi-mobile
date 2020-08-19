package com.efficom.efid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.efficom.efid.data.model.Room
import com.efficom.efid.data.model.sealedClass.ErrorRoomApi
import com.efficom.efid.data.model.sealedClass.RoomApiReturn
import com.efficom.efid.data.model.sealedClass.RoomList
import com.efficom.efid.data.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(private val app: Application,
    private val roomRepository: RoomRepository): AndroidViewModel(app) {

    private val _error = MutableLiveData<RoomApiReturn>()
    val error = _error

    private val _freeRoomList = MutableLiveData<List<Room>>()
    val freeRoomList = _freeRoomList

    init {
        getOpenRoom()
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

        }
    }
}