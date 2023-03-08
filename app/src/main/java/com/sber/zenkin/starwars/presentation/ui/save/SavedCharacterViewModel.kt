package com.sber.zenkin.starwars.presentation.ui.save

import androidx.lifecycle.*
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.domain.useCases.SaveStarWarsCharacterUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import timber.log.Timber
import javax.inject.Inject

class SavedCharacterViewModel @Inject constructor(
    private val getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase,
    private val saveStarWarsCharacterUseCase: SaveStarWarsCharacterUseCase
) : ViewModel() {

    private var searchByLiveData = MutableLiveData("")

    @OptIn(FlowPreview::class)
    val savedCharactersLiveData: LiveData<List<Character>> = searchByLiveData.asFlow()
        .debounce(500)
        .asLiveData()
        .switchMap {
            //old liveData ignored when new search value comes
            liveData {
                val characterFromDatabase = viewModelScope.async(Dispatchers.IO) {
                    Timber.i("Get saved characters from database by search value: $it")
                    getStarWarsCharacterUseCase.getCharactersFromDb(it ?: "")
                }
                emit(characterFromDatabase.await())
            }
        }

    fun onClickFavorite(character: Character) {
        viewModelScope.launch {
            if (character.favorite) {
                Timber.i("Add character to favorite: ${character.name}")
                saveStarWarsCharacterUseCase.addCharacterToFavorite(character)
            } else {
                Timber.i("Remove character from favorite: ${character.name}")
                saveStarWarsCharacterUseCase.removeCharacterFromFavorite(character)
            }
        }
    }

    fun setSearchValue(searchBy: String) {
        Timber.i("Set search value for saved character list: $searchBy")
        if (searchByLiveData.value != searchBy) {
            searchByLiveData.value = searchBy
        }
    }
}