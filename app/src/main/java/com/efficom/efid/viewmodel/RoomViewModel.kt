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

        val rooms = mutableListOf<Room>()
        val r1 = Room(0,"salle 1", 0, "normal", 0, 21)
        rooms.add(r1)
        val r2 = Room(0,"salle 2", 0, "normal", 0, 21)
        rooms.add(r2)
        val r3 = Room(0,"salle 3", 0, "normal", 0, 21)
        rooms.add(r3)
        val r4 = Room(0,"salle 4", 0, "normal", 0, 21)
        rooms.add(r4)
        val r5 = Room(0,"salle 5", 0, "normal", 0, 21)
        rooms.add(r5)

        _freeRoomList.postValue(rooms)
    }

    private fun getOpenRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            roomRepository.getFreeRoom().let {response ->
                when(response){
                    is RoomList -> _freeRoomList.postValue(response.data)
                    is ErrorRoomApi -> _error.postValue(ErrorRoomApi)
                }
            }

        }
    }

    private fun getRoomByDate(){
        GlobalScope.launch(Dispatchers.IO) {

        }
    }
}