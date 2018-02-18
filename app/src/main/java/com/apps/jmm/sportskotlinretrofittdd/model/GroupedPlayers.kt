package com.apps.jmm.sportskotlinretrofittdd.model

data class GroupedPlayers (
        val players: List<Player>? = emptyList(),
        val type: String = "",
        val title: String = ""
)