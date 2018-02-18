package com.apps.jmm.sportskotlinretrofittdd.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import com.apps.jmm.sportskotlinretrofittdd.R
import com.apps.jmm.sportskotlinretrofittdd.app.AndroidResLocator
import com.apps.jmm.sportskotlinretrofittdd.app.configureDefaultAdapter
import com.apps.jmm.sportskotlinretrofittdd.datasource.api.GetListOfSportPlayersImpl
import com.apps.jmm.sportskotlinretrofittdd.model.Player
import com.apps.jmm.sportskotlinretrofittdd.repository.PlayersRepository
import com.apps.jmm.sportskotlinretrofittdd.ui.adapter.PlayersListAdapter
import com.apps.jmm.sportskotlinretrofittdd.ui.presenter.PlayersListPresenter
import kotlinx.android.synthetic.main.fragment_player_list.*
import kotlinx.coroutines.experimental.runBlocking


class PlayerListFragment : BaseFragment(), PlayersListPresenter.MVPView, PlayersListPresenter.Navigator {

    var presenter: PlayersListPresenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playersRepository = PlayersRepository(GetListOfSportPlayersImpl())
        presenter = PlayersListPresenter(AndroidResLocator(context), playersRepository)
        presenter?.view = this
        presenter?.navigator = this

        runBlocking { presenter?.initialize() }

        spn_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                runBlocking {
                    presenter?.updateRecyclerPlayerList(spn_categories.selectedItemPosition)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_player_list
    }

    override fun fillCategoriesSpinner(categoryList: List<String>) {
        spn_categories.configureDefaultAdapter(categoryList)
    }

    override fun fillGroupedPlayersRecycler(listPlayers: List<Player>, typeLayout: String) {
        val typeListGrid = "GRID"
        val switchLayout : Boolean

        if (typeLayout == typeListGrid) {
            recycler.layoutManager = GridLayoutManager(context,2)
            switchLayout = true
        } else {
            recycler.layoutManager = LinearLayoutManager(context)
            switchLayout = false
        }
        recycler.adapter = PlayersListAdapter(context, listPlayers, switchLayout)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SPINNER_POSITION", spn_categories.selectedItemPosition)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getInt("SPINNER_POSITION",0)?.let { spn_categories.setSelection(it) }
    }
}
