package com.sber.zenkin.starwars.presentation.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.domain.useCases.SaveStarWarsCharacterUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchedCharacterViewModel @Inject constructor(
    private val getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase,
    private val saveStarWarsCharacterUseCase: SaveStarWarsCharacterUseCase
) : ViewModel() {

    val searchedCharactersFlow: Flow<PagingData<Character>>

    var searchByLiveData = MutableLiveData("")


    init {
/*        searchedCharactersFlow =
            stateFlow(viewModelScope, PagingData.empty()) { subscriptionCount ->
                searchByLiveData.asFlow()
                    .debounce(500)
                    .flatMapLatest { getStarWarsCharacterUseCase.getCharacters(it) }
                    .cachedIn(viewModelScope)
                    .flowWhileShared(subscriptionCount, SharingStarted.WhileSubscribed(1000L))
                    .distinctUntilChanged()
                    *//*
                    Returns flow where all subsequent repetitions of the same value are filtered out.
                    Note that any instance of StateFlow ALREADY BEHAVES as if distinctUntilChanged operator is applied to it.
                    *//*
                    .catch {  }
            }*/

        searchedCharactersFlow = searchByLiveData.asFlow()
            .debounce(500)
            .flatMapLatest { getStarWarsCharacterUseCase.getCharacters(it) }
            .cachedIn(viewModelScope)
    }

/*    private fun <T> stateFlow(
        scope: CoroutineScope,
        initialValue: T,
        producer: (subscriptionCount: StateFlow<Int>) -> Flow<T>
    ): StateFlow<T> {
        val state = MutableStateFlow(initialValue)
        scope.launch {
            producer(state.subscriptionCount).collect(state)
        }
        return state.asStateFlow()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> Flow<T>.flowWhileShared(
        subscriptionCount: StateFlow<Int>,
        started: SharingStarted
    ): Flow<T> {
        return started.command(subscriptionCount)
            .distinctUntilChanged()
            .flatMapLatest {
                when (it) {
                    SharingCommand.START -> this
                    SharingCommand.STOP,
                    SharingCommand.STOP_AND_RESET_REPLAY_CACHE -> emptyFlow()
                }
            }
    }*/

    fun onClickFavorite(character: Character) {
        viewModelScope.launch {
            if (character.favorite) {
                saveStarWarsCharacterUseCase.addCharacterToFavorite(character)
            } else {
                saveStarWarsCharacterUseCase.removeCharacterFromFavorite(character)
            }
        }
    }

    fun setSearchBy(searchBy: String) {
        if (searchByLiveData.value != searchBy) {
            searchByLiveData.value = searchBy
        }
    }
}