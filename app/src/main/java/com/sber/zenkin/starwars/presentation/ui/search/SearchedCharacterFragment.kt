package com.sber.zenkin.starwars.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.starwars.R
import com.sber.zenkin.starwars.componentManager
import com.sber.zenkin.starwars.databinding.FragmentSearchedCharacterBinding
import timber.log.Timber
import javax.inject.Inject

class SearchedCharacterFragment : Fragment() {

    private var _binding: FragmentSearchedCharacterBinding? = null

    @Inject
    lateinit var getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().componentManager.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchedCharacterViewModel = ViewModelProvider(
            this,
            SearchedCharacterViewModelProviderFactory(getStarWarsCharacterUseCase)
        )[SearchedCharacterViewModel::class.java]

        _binding = FragmentSearchedCharacterBinding.inflate(inflater, container, false)

        val characterAdapter = initAdapter(object : CharacterClickHandler {
            override fun onClickFavorite(character: Character) {
                searchedCharacterViewModel.addToFavorite(character)
            }

            override fun onClickCharacter(character: Character) {
/*                findNavController().navigate(
                    R.id.action_listRepositoryFragment_to_repositoryFragment,
                    bundleOf("character" to character)
                )*/
            }
        })

        searchedCharacterViewModel.searchedCharacters.observe(viewLifecycleOwner) {
            characterAdapter.characters = it
        }

        searchedCharacterViewModel.loadData()

        return binding.root
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