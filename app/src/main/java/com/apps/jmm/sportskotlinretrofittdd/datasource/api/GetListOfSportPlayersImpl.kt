package com.apps.jmm.sportskotlinretrofittdd.datasource.api

import com.apps.jmm.sportskotlinretrofittdd.BuildConfig
import com.apps.jmm.sportskotlinretrofittdd.model.GroupedPlayers
import com.apps.jmm.sportskotlinretrofittdd.usecase.GetListOfSportPlayers
import com.google.gson.JsonSyntaxException
import es.voghdev.chucknorrisjokes.model.AbsError
import es.voghdev.chucknorrisjokes.model.CNError
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetListOfSportPlayersImpl : GetListOfSportPlayers, ApiRequest {
    override fun getListOfPlayer(): Pair<List<GroupedPlayers>?, AbsError?> {

        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
            builder.addInterceptor(LogJsonInterceptor())

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(getEndPoint())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()


        val service: MyJsonService = retrofit.create(MyJsonService::class.java)

        val call: Call<List<GroupedPlayers>> = service.getListOfSportPlayers()

        try {
            val rsp: Response<List<GroupedPlayers>>? = call.execute()

            if (rsp?.body() ?: false is List<*>) {
                return Pair(rsp?.body() ?: emptyList<GroupedPlayers>(), null)
            } else if (rsp?.errorBody() != null) {
                val error = rsp.errorBody()!!.string()
                return Pair(null, CNError(error))
            }
        } catch (e: JsonSyntaxException) {
            return Pair(null, CNError(e.message ?: "Unknown error parsing JSON"))
        }

        return Pair(null, CNError("Unknown error"))
    }
}