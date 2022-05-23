package com.sber.zenkin.starwars.presentation.ui.search

import androidx.lifecycle.*
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.data.repository.StarWarsCharacterRepository
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchedCharacterViewModel @Inject constructor(
    private val getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase
): ViewModel() {

    private var _searchedCharacters = MutableLiveData<List<Character>>()
    val searchedCharacters = _searchedCharacters

    fun loadData(){
        viewModelScope.launch {
            _searchedCharacters.postValue(getStarWarsCharacterUseCase.getCharacters())
        }
    }

}