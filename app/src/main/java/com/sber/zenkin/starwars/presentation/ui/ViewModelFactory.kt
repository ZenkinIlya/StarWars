package com.sber.zenkin.starwars.presentation.ui

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner

typealias ViewModelCreator<VM> = (savedStateHandle: SavedStateHandle) -> VM

class ViewModelFactory<VM : ViewModel>(
    owner: SavedStateRegistryOwner,
    private val viewModelCreator: ViewModelCreator<VM>
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return viewModelCreator(handle) as T
    }

}

inline fun <reified VM : ViewModel> Fragment.viewModelCreator(noinline creator: ViewModelCreator<VM>): Lazy<VM> {
    return viewModels { ViewModelFactory(this, creator) }
}