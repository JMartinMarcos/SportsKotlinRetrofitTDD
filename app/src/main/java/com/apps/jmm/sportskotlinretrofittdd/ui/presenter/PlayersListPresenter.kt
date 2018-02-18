package com.apps.jmm.sportskotlinretrofittdd.ui.presenter

import com.apps.jmm.sportskotlinretrofittdd.app.ResLocator
import com.apps.jmm.sportskotlinretrofittdd.app.coroutine
import com.apps.jmm.sportskotlinretrofittdd.app.success
import com.apps.jmm.sportskotlinretrofittdd.model.GroupedPlayers
import com.apps.jmm.sportskotlinretrofittdd.model.Player
import com.apps.jmm.sportskotlinretrofittdd.repository.PlayersRepository

class PlayersListPresenter(val context: ResLocator, val repository: PlayersRepository) :
        Presenter<PlayersListPresenter.MVPView, PlayersListPresenter.Navigator>() {

    private var listGroupedPlayer: List<GroupedPlayers> = emptyList()


    override suspend fun initialize() {

        coroutine { repository.getListOfPlayer() }.await().let { result ->
            if (result.success()) {
                listGroupedPlayer = result.first ?: emptyList()
                val categories = listGroupedPlayer.map { c -> c.title }
                val dataPlayers = listGroupedPlayer[0].players ?: emptyList()
                val typeLayout = listGroupedPlayer[0].type
                view?.fillCategoriesSpinner(categories)
                view?.fillGroupedPlayersRecycler(dataPlayers, typeLayout)
            }
        }
    }

    interface MVPView {
        fun fillCategoriesSpinner(categoryList: List<String>)
        fun fillGroupedPlayersRecycler(listPlayers: List<Player>, typeLayout: String)
    }

    interface Navigator {

    }

    fun updateRecyclerPlayerList(positionSpinner: Int) {
        val listPlayers = listGroupedPlayer[positionSpinner].players ?: emptyList()
        val typeLayout = listGroupedPlayer[positionSpinner].type
        view?.fillGroupedPlayersRecycler(listPlayers, typeLayout)
    }

}