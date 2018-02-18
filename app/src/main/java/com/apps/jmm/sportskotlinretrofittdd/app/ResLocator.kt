package com.apps.jmm.sportskotlinretrofittdd.app

interface ResLocator {
    fun getString(  resId: Int): String
    fun getStringArray(resId: Int): List<String>
}
