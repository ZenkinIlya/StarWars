package com.sber.zenkin.starwars.presentation.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.starwars.R
import com.sber.zenkin.starwars.databinding.FragmentCharacterBinding
import timber.log.Timber

class CharacterAdapter(
    private val characterClickHandler: CharacterClickHandler,
) : PagingDataAdapter<Character, CharacterViewHolder>(CharacterDiffItemCallback),
    View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCharacterBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.favoriteImageView.setOnClickListener(this)

        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position) ?: return
        with(holder.binding) {
            holder.itemView.tag = character
            favoriteImageView.tag = character

            CharacterViewHandler.DefaultRepository(this, character)
                .bindView()
                .bindFavoriteImageView()
        }
    }

    override fun onClick(view: View) {
        val character = view.tag as Character
        when (view.id) {
            R.id.favorite_image_view -> {
                (view as ImageView).setImageResource(
                    if (character.favorite) R.drawable.ic_baseline_favorite_border_24 else
                        R.drawable.ic_baseline_favorite_24
                )
                characterClickHandler.onClickFavorite(character)
            }
            else -> {
                characterClickHandler.onClickCharacter(character)
            }
        }
    }
}

class CharacterViewHolder(val binding: FragmentCharacterBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

private object CharacterDiffItemCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}
