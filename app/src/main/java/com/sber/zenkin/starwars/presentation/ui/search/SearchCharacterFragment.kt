package com.sber.zenkin.starwars.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.domain.useCases.SaveStarWarsCharacterUseCase
import com.sber.zenkin.starwars.componentManager
import com.sber.zenkin.starwars.databinding.FragmentSearchCharacterBinding
import com.sber.zenkin.starwars.presentation.ui.viewModelCreator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SearchCharacterFragment : Fragment() {

    private var _binding: FragmentSearchCharacterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase

    @Inject
    lateinit var saveStarWarsCharacterUseCase: SaveStarWarsCharacterUseCase


    private val searchedCharacterViewModel by viewModelCreator {
        SearchedCharacterViewModel(
            getStarWarsCharacterUseCase,
            saveStarWarsCharacterUseCase
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().componentManager.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchCharacterBinding.inflate(inflater, container, false)

        val characterAdapter = initAdapter(object : CharacterClickHandler {
            override fun onClickFavorite(character: Character) {
                searchedCharacterViewModel.onClickFavorite(character)
            }

            override fun onClickCharacter(character: Character) {}
        })

        observeSearchBy()
        observeCharacters(characterAdapter)

        return binding.root
    }

    private fun observeSearchBy() {
        binding.search.addTextChangedListener {
            searchedCharacterViewModel.setSearchBy(it.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart()")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume()")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause()")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop()")
    }

    private fun observeCharacters(characterAdapter: CharacterAdapter) {
        lifecycleScope.launch {
            Timber.d("launch")
            searchedCharacterViewModel.searchedCharactersFlow.collectLatest { pagingData ->
                characterAdapter.submitData(pagingData)
            }
        }
    }

    private fun initAdapter(characterClickHandler: CharacterClickHandler): CharacterAdapter {
        val characterAdapter = CharacterAdapter(characterClickHandler)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCharacterSearcher.layoutManager = linearLayoutManager
        binding.recyclerViewCharacterSearcher.adapter = characterAdapter
        setAnimationForRecyclerView()
        return characterAdapter
    }

    private fun setAnimationForRecyclerView(supportAnimation: Boolean = false) {
        val itemAnimator = binding.recyclerViewCharacterSearcher.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = supportAnimation
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}