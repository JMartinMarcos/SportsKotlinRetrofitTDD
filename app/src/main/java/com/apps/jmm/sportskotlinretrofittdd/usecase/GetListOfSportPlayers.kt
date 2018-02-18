package com.apps.jmm.sportskotlinretrofittdd.usecase

import com.apps.jmm.sportskotlinretrofittdd.model.GroupedPlayers
import es.voghdev.chucknorrisjokes.model.AbsError

interface GetListOfSportPlayers {
    fun getListOfPlayer() : Pair<List<GroupedPlayers>?, AbsError?>
}