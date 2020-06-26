package com.efficom.efid.data.network

import com.efficom.efid.data.model.request.LoginRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Url

public interface AuthApi {

    @GET
    suspend fun authenticate(@Url url: String): Response<Void>
}