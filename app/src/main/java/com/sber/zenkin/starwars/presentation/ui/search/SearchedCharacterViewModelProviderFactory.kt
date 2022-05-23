package com.sber.zenkin.starwars.presentation.ui.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase

@Suppress("UNCHECKED_CAST")
class SearchedCharacterViewModelProviderFactory(
    private val getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchedCharacterViewModel(getStarWarsCharacterUseCase) as T
    }
}