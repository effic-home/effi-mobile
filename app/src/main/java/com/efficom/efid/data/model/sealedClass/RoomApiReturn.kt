package com.efficom.efid.data.model.sealedClass

import com.efficom.efid.data.model.Room

sealed class RoomApiReturn

data class RoomList(val data: List<Room>): RoomApiReturn()
object ErrorRoomApi : RoomApiReturn()