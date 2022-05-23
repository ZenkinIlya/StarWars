package com.sber.zenkin.starwars.presentation.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sber.zenkin.starwars.databinding.FragmentSavedCharacterBinding

class SavedCharacterFragment : Fragment() {

    private var _binding: FragmentSavedCharacterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val savedCharacterViewModel =
            ViewModelProvider(this).get(SavedCharacterViewModel::class.java)

        _binding = FragmentSavedCharacterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        savedCharacterViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}