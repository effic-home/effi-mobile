package com.efficom.efid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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

    private val _roomList = MutableLiveData<List<String>>()
    val roomList = _roomList

    init {
        getOpenRoom()
    }

    private fun getOpenRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            roomRepository.getFreeRoom().let {response ->
                when(response){
                    is RoomList -> _roomList.postValue(response.data)
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