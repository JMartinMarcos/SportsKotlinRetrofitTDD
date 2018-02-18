package com.apps.jmm.sportskotlinretrofittdd.datasource.api

import com.apps.jmm.sportskotlinretrofittdd.model.GroupedPlayers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface MyJsonService {
    @Headers("Content-Type: application/json;charset=UTF-8")

    @GET("bins/66851")
    fun getListOfSportPlayers(): Call<List<GroupedPlayers>>

}