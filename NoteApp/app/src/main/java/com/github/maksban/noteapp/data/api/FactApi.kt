package com.github.maksban.noteapp.data.api

import com.github.maksban.noteapp.data.model.Fact
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface FactApi {

   @Headers("X-Api-Key: dVbCpg0cGbSwo6iZtUAGeg==iwDvNCdw207zCHon")
    @GET("v1/facts")
    suspend fun getFact(): Response<List<Fact>>

}