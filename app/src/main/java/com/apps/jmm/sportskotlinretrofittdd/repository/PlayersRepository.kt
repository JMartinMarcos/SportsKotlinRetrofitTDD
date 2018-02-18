package com.apps.jmm.sportskotlinretrofittdd.repository

import com.apps.jmm.sportskotlinretrofittdd.usecase.GetListOfSportPlayers
/** con una sola llamada no es necesario pero lo dejamos preparado para futuros **/
class PlayersRepository(
        val getListOfSportPlayers: GetListOfSportPlayers
) : GetListOfSportPlayers by getListOfSportPlayers