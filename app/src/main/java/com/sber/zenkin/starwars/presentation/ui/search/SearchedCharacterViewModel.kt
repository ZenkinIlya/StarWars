package com.sber.zenkin.starwars.presentation.ui.search

import androidx.lifecycle.*
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.data.repository.StarWarsCharacterRepository
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SearchedCharacterViewModel @Inject constructor(
    private val getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase
) : ViewModel() {

    private var _searchedCharacters = MutableLiveData<List<Character>>()
    val searchedCharacters = _searchedCharacters

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _searchedCharacters.postValue(getStarWarsCharacterUseCase.getCharacters())

/*            withContext(Dispatchers.Default) {

            }*/
        }

/*        _searchedCharacters = liveData{
            val characters = getStarWarsCharacterUseCase.getCharacters()
            emit(characters)
        } as MutableLiveData<List<Character>>*/
    }

    fun deleteData() {
        viewModelScope.launch {
            getStarWarsCharacterUseCase.deleteAllCharacter()
        }
    }

    fun addToFavorite(character: Character) {
        viewModelScope.launch {
            getStarWarsCharacterUseCase.addCharacterToFavorite(character)
        }
    }

    fun removeFromFavorite(character: Character) {
        viewModelScope.launch {
            getStarWarsCharacterUseCase.removeCharacterFromFavorite(character)
        }
    }

}