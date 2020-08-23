package com.efficom.efid.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.efficom.efid.data.model.Reservation
import com.efficom.efid.data.model.ReservedRoom
import com.efficom.efid.data.model.Room
import com.efficom.efid.data.model.request.ReservationRequest
import com.efficom.efid.data.model.sealedClass.*
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

    private val _successReserve = MutableLiveData<Boolean>()
    val successReserve = _successReserve

    private val _inProcess = MutableLiveData<Boolean>()
    val inProcess = _inProcess

    fun getOpenRoom(){
        if (isInternetAvailable()){
            GlobalScope.launch(Dispatchers.IO) {
                roomRepository.getFreeRoom().let {response ->
                    when(response){
                        is RoomList -> _freeRoomList.postValue(response.data)
                        is ErrorRoomApiReturn -> _error.postValue(response)
                    }
                }
            }
        }else{
            _error.postValue(NoInternet)
        }

    }

    fun getOldReserv(){
        if (isInternetAvailable()){
            GlobalScope.launch(Dispatchers.IO) {
                roomRepository.getOldReserve().let { response ->
                    when(response){
                        is ReserveList -> _oldReserve.postValue(response.data)
                        is ErrorRoomApiReturn -> _error.postValue(response)
                    }
                }
            }
        }else{
            _error.postValue(NoInternet)
        }
    }

    fun getFreeRoomByDate(date: String){
        if (isInternetAvailable()){
            GlobalScope.launch(Dispatchers.IO) {
                roomRepository.getFreeRoomByDate(date).let {response ->
                    when(response){
                        is  ReservedRoomList -> getAllFreeRoom(response.data)
                        is  ErrorRoomApiReturn ->_error.postValue(response)
                    }
                }
            }
        }else{
            _error.postValue(NoInternet)
        }
    }

    private fun getAllFreeRoom(reservedRoom: List<ReservedRoom>){
        if (isInternetAvailable()){
            GlobalScope.launch(Dispatchers.IO) {
                roomRepository.getRoom().let { response ->
                    when(response){
                        is RoomList -> {
                            if (reservedRoom.isNullOrEmpty()){
                                _freeRoomByDate.postValue(response.data)
                            }
                            else{
                                val reservedRoomList = mutableListOf<Room>()
                                reservedRoomList.addAll(response.data)
                                reservedRoom.forEach { reservedRoom ->
                                    response.data.forEach { room ->
                                        if (reservedRoom.id_salle == room.id_salle){
                                            reservedRoomList.remove(room)
                                        }
                                    }
                                }.let {
                                    _freeRoomByDate.postValue(reservedRoomList)
                                }
                            }
                        }
                        is ErrorRoomApiReturn -> _error.postValue(response)
                    }
                }
            }
        }else{
            _error.postValue(NoInternet)
        }

    }

    fun reserveRoom(reservation: ReservationRequest){
        if (isInternetAvailable()){
            GlobalScope.launch(Dispatchers.IO) {
                roomRepository.reserveRoom(reservation).let {response ->
                    when(response){
                        is SuccessReserve -> _successReserve.postValue(true)
                        is ErrorRoomApiReturn -> _error.postValue(response)
                    }
                    _inProcess.postValue(true)
                }
            }
        }else{
            _error.postValue(NoInternet)
        }

    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}