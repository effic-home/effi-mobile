package com.efficom.efid.data.model.sealedClass

import com.efficom.efid.data.model.Reservation
import com.efficom.efid.data.model.ReservedRoom
import com.efficom.efid.data.model.Room

sealed class RoomApiReturn

data class RoomList(val data: List<Room>): RoomApiReturn()
data class ReservedRoomList(val data: List<ReservedRoom>): RoomApiReturn()
object ErrorRoomApi : RoomApiReturn()
data class ReserveList(val data: List<Reservation>): RoomApiReturn()
object SuccessReserve: RoomApiReturn()
object NoInternet: RoomApiReturn()