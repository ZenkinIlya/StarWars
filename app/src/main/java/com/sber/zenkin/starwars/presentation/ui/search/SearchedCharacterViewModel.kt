package com.sber.zenkin.starwars.presentation.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchedCharacterViewModel @Inject constructor(
    private val getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase
) : ViewModel() {

    val searchedCharactersFlow: Flow<PagingData<Character>>

    var searchByLiveData = MutableLiveData("")

    init {
        searchedCharactersFlow = searchByLiveData.asFlow()
            .debounce(500)
            .flatMapLatest { getStarWarsCharacterUseCase.getCharacters(it) }
            .cachedIn(viewModelScope)
    }

/*    fun loadData(searchBy: String){
        viewModelScope.launch {
             _searchedCharacters = getStarWarsCharacterUseCase.getCharacters(searchBy).onEach {
                Timber.tag("viewModel").d(it.toString())
            }
                .asLiveData(Dispatchers.IO) as MutableLiveData<PagingData<Character>>
        }
    }*/

/*    fun deleteData() {
        viewModelScope.launch {
            getStarWarsCharacterUseCase.deleteAllCharacter()
        }
    }*/

    fun onClickFavorite(character: Character) {
        viewModelScope.launch {
            if (character.favorite) {
                getStarWarsCharacterUseCase.removeCharacterFromFavorite(character)
            } else {
                getStarWarsCharacterUseCase.addCharacterToFavorite(character)
            }
        }
    }

    fun setSearchBy(searchBy: String) {
        if (searchByLiveData.value != searchBy) {
            searchByLiveData.value = searchBy
        }
    }
}