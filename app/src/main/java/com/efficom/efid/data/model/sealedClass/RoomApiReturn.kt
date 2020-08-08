package com.efficom.efid.data.model.sealedClass

sealed class RoomApiReturn

data class RoomList(val data: List<String>): RoomApiReturn()
object ErrorRoomApi : RoomApiReturn()