package com.sber.zenkin.starwars.presentation.ui.save

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.domain.useCases.SaveStarWarsCharacterUseCase
import com.sber.zenkin.starwars.componentManager
import com.sber.zenkin.starwars.databinding.FragmentSavedCharacterBinding
import com.sber.zenkin.starwars.presentation.ui.search.CharacterAdapter
import com.sber.zenkin.starwars.presentation.ui.search.CharacterClickHandler
import com.sber.zenkin.starwars.presentation.ui.viewModelCreator
import timber.log.Timber
import javax.inject.Inject

class SavedCharacterFragment : Fragment() {

    private var _binding: FragmentSavedCharacterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var getStarWarsCharacterUseCase: GetStarWarsCharacterUseCase

    @Inject
    lateinit var saveStarWarsCharacterUseCase: SaveStarWarsCharacterUseCase

    private val savedCharacterViewModel by viewModelCreator {
        SavedCharacterViewModel(
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
        _binding = FragmentSavedCharacterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val characterAdapter = initAdapter(object : CharacterClickHandler {
            override fun onClickFavorite(character: Character) {
                savedCharacterViewModel.onClickFavorite(character)
            }

            override fun onClickCharacter(character: Character) {
                TODO("realize click character")
            }
        })

        initListeners()
        observeSavedCharacters(characterAdapter)

        return root
    }

    private fun initListeners() {
        binding.searchSavedCharacters.addTextChangedListener {
            savedCharacterViewModel.setSearchValue(it.toString())
        }
    }

    private fun observeSavedCharacters(adapter: CharacterAdapter) {
        savedCharacterViewModel.savedCharactersLiveData.observe(viewLifecycleOwner) {
            Timber.d("characters from database: $it")
            TODO("realize adapter without Paging and output saved characters")
        }
    }

    private fun initAdapter(characterClickHandler: CharacterClickHandler): CharacterAdapter {
        val characterAdapter = CharacterAdapter(characterClickHandler)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSavedCharactersSearcher.layoutManager = linearLayoutManager
        binding.recyclerViewSavedCharactersSearcher.adapter = characterAdapter
        setAnimationForRecyclerView()
        return characterAdapter
    }

    private fun setAnimationForRecyclerView(supportAnimation: Boolean = false) {
        val itemAnimator = binding.recyclerViewSavedCharactersSearcher.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = supportAnimation
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}