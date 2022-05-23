package com.sber.zenkin.starwars.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.starwars.componentManager
import com.sber.zenkin.starwars.databinding.FragmentSearchedCharacterBinding
import com.sber.zenkin.starwars.presentation.ui.save.SavedCharacterViewModel
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
        /*val searchedCharacterViewModel =
            ViewModelProvider(this)[SearchedCharacterViewModel::class.java]*/

        /*val savedCharacterViewModel =
            ViewModelProvider(this).get(SavedCharacterViewModel::class.java)*/

        _binding = FragmentSearchedCharacterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        searchedCharacterViewModel.searchedCharacters.observe(viewLifecycleOwner) {
            textView.text = it.toString()
        }

        searchedCharacterViewModel.loadData()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}