package com.sber.zenkin.starwars.presentation.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.starwars.R
import com.sber.zenkin.starwars.databinding.FragmentCharacterBinding
import timber.log.Timber

class CharacterAdapter(
    private val characterClickHandler: CharacterClickHandler,
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>(),
    View.OnClickListener {

    class CharacterViewHolder(val binding: FragmentCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    var characters: List<Character> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCharacterBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.favoriteImageView.setOnClickListener(this)

        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        with(holder.binding) {
            holder.itemView.tag = character
            favoriteImageView.tag = character

            CharacterViewHandler.DefaultRepository(this, character)
                .bindView()
                .bindFavoriteImageView()
        }
    }

    override fun getItemCount(): Int = characters.size

    override fun onClick(view: View) {
        val character = view.tag as Character
        when (view.id) {
            R.id.favorite_image_view -> {
                characterClickHandler.onClickFavorite(character)
            }
            else -> {
                characterClickHandler.onClickCharacter(character)
            }
        }
    }
}