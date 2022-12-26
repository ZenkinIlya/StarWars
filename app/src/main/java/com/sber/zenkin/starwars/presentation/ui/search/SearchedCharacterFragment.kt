package com.sber.zenkin.starwars.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.starwars.componentManager
import com.sber.zenkin.starwars.databinding.FragmentSearchedCharacterBinding
import com.sber.zenkin.starwars.presentation.ui.viewModelCreator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchedCharacterFragment : Fragment() {

    private var _binding: FragmentSearchedCharacterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase

    private val searchedCharacterViewModel by viewModelCreator {
        SearchedCharacterViewModel(
            getStarWarsCharacterUseCase
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
        _binding = FragmentSearchedCharacterBinding.inflate(inflater, container, false)

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

    private fun observeCharacters(characterAdapter: CharacterAdapter) {
        lifecycleScope.launch {
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
        return characterAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}