package com.apps.jmm.sportskotlinretrofittdd.ui

import com.apps.jmm.sportskotlinretrofittdd.app.ResLocator
import com.apps.jmm.sportskotlinretrofittdd.model.GroupedPlayers
import com.apps.jmm.sportskotlinretrofittdd.model.Player
import com.apps.jmm.sportskotlinretrofittdd.repository.PlayersRepository
import com.apps.jmm.sportskotlinretrofittdd.ui.presenter.PlayersListPresenter
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PlayerListTest {

    @Mock
    lateinit var mockResLocator: ResLocator

    @Mock
    lateinit var mockNavigator: PlayersListPresenter.Navigator

    @Mock
    lateinit var mockView: PlayersListPresenter.MVPView

    @Mock
    lateinit var mockPlayersRepository: PlayersRepository

    lateinit var presenter: PlayersListPresenter


    private val player1 = Player(image = "http://www.segundoasegundo.com/sas/wp-content/uploads/2014/01/cristiano-ronaldo.jpg",
            surname = "Ronaldo",
            name = "Cristiano")

    private val player2 = Player(image = "http://1.bp.blogspot.com/-YGMAme-l8DQ/T6fJARFRgAI/AAAAAAAAAj8/0cB5NiT4R5Q/s320/lionel-messi-2011-2-5-18-11-2.jpg",
            surname = "Messi",
            name = "Lionel")

    private val listOdPlayers = listOf(player1, player2)

    private val requestPlayers: List<GroupedPlayers> = listOf(GroupedPlayers(players = listOdPlayers,
            type = "LIST_A",
            title = "Football"))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = createMockedPresenter()
    }

    private fun createMockedPresenter(): PlayersListPresenter {
        val presenter = PlayersListPresenter(mockResLocator, mockPlayersRepository)
        presenter.view = mockView
        presenter.navigator = mockNavigator
        return presenter
    }


    @Test
    fun shouldRequestAListOfPlayersGroupedByType() {
        whenever(mockPlayersRepository.getListOfPlayer()).thenReturn(Pair(requestPlayers, null))
        runBlocking { presenter.initialize() }
        verify(mockPlayersRepository).getListOfPlayer()
    }

    @Test
    fun shouldFillCategoryNamesInASpinnerWhenCategoriesAreReceivedFromApi() {
        whenever(mockPlayersRepository.getListOfPlayer()).thenReturn(Pair(requestPlayers, null))
        runBlocking { presenter.initialize() }
        verify(mockView).fillCategoriesSpinner(ArgumentMatchers.anyList())
    }

    @Test
    fun shouldFillPersonalDataOfPlayersInARecyclerViewWhenListPlayersAreReceivedFromApi() {
        whenever(mockPlayersRepository.getListOfPlayer()).thenReturn(Pair(requestPlayers, null))
        runBlocking { presenter.initialize() }
        verify(mockView).fillGroupedPlayersRecycler(listOdPlayers, "LIST_A")
    }


    @Test
    fun shouldUpdateRecyclerViewPlayerListAfterSpinnerChangeItemSelected() {
        whenever(mockPlayersRepository.getListOfPlayer()).thenReturn(Pair(requestPlayers, null))
        runBlocking {
            presenter.initialize()
            presenter.updateRecyclerPlayerList(0)
        }
    }

    @Test
    fun shouldNotDownloadAnyImageIfUrlIsEmpty() {

        val playerMod = player1.copy(image = "")
        val requestPlayerListMod = GroupedPlayers(players = listOf(playerMod, playerMod))
        val requestPlayerMod = listOf(requestPlayerListMod)

        whenever(mockPlayersRepository.getListOfPlayer()).thenReturn(Pair(requestPlayerMod, null))

        runBlocking {
            presenter.initialize()
            presenter.updateRecyclerPlayerList(0)
        }
        verify(mockView, times(0)).fillGroupedPlayersRecycler(listOf(playerMod, playerMod), "LIST_A")
    }

}