package com.sber.zenkin.starwars.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.starwars.R
import com.sber.zenkin.starwars.databinding.FragmentCharacterBinding

private const val ARG_FAVORITE = "arg.favorite"

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
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(
        holder: CharacterViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val character = getItem(position) ?: return
        with(holder.binding) {
            val characterWrapper = CharacterWrapper(character, position)
            holder.itemView.tag = characterWrapper
            favoriteImageView.tag = characterWrapper

            if (payloads.isEmpty() || payloads[0] !is Bundle) {
                CharacterViewHandler.DefaultRepository(this, character)
                    .bindView()
                    .bindFavoriteImageView()
            } else {
                val bundle = payloads[0] as Bundle
                character.favorite = bundle.getBoolean(ARG_FAVORITE)
                CharacterViewHandler.DefaultRepository(this, character)
                    .bindFavoriteImageView()
            }
        }
    }

    override fun onClick(view: View) {
        val characterWrapper = view.tag as CharacterWrapper
        when (view.id) {
            R.id.favorite_image_view -> {
                characterWrapper.character.favorite = !characterWrapper.character.favorite
                notifyItemChanged(characterWrapper.position)
                characterClickHandler.onClickFavorite(characterWrapper.character)
            }
            else -> {
                characterClickHandler.onClickCharacter(characterWrapper.character)
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

    /**
     * Define kind of changes for correct animation*/
    override fun getChangePayload(oldItem: Character, newItem: Character): Any? {
        return if (oldItem.favorite == newItem.favorite) {
            super.getChangePayload(oldItem, newItem)
        } else {
            val diff = Bundle()
            diff.putBoolean(ARG_FAVORITE, newItem.favorite)
            diff
        }
    }
}

class CharacterWrapper(val character: Character, val position: Int)

