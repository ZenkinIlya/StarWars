package com.sber.zenkin.starwars.presentation.ui.save

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.domain.useCases.SaveStarWarsCharacterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class SavedCharacterViewModel constructor(
    getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase,
    saveStarWarsCharacterUseCase: SaveStarWarsCharacterUseCase
) : ViewModel() {

//    val savedCharactersFlow: Flow<PagingData<Character>>

/*    init {
        savedCharactersFlow =
        savedCharactersFlow = searchByLiveData.asFlow()
            .debounce(500)
            .flatMapLatest { getStarWarsCharacterUseCase.getCharactersFromDb(it) }
            .cachedIn(viewModelScope)
    }

    fun onClickFavorite(character: Character) {
        viewModelScope.launch {
            if (character.favorite) {
                getStarWarsCharacterUseCase.removeCharacterFromFavorite(character)
            } else {
                getStarWarsCharacterUseCase.addCharacterToFavorite(character)
            }
        }
    }*/

    fun onClickFavorite() {

    }
}