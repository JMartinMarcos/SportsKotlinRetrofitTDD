package com.apps.jmm.sportskotlinretrofittdd.datasource.api

interface ApiRequest {
    fun getEndPoint(): String {
        return "https://api.myjson.com/"
    }
}
