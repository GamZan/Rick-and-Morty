package com.example.myapplication.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.CharacterFromRickAndMorty
import com.example.myapplication.databinding.ViewCharactersBinding

class CharacterAdapter : PagingDataAdapter<CharacterFromRickAndMorty, MyViewHolder>(
    DiffUtilCallback()
) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            name.text = item?.name
            lastLoc.text = item?.location?.name
            species.text = "${item?.status} - ${item?.species}"
            when (item?.status) {
                "Alive" -> {
                    live.setBackgroundResource(R.drawable.round_outline_green)
                }
                else -> {
                    live.setBackgroundResource(R.drawable.round_outline_red)
                }
            }
            item?.let {
                Glide
                    .with(characterPhoto.context)
                    .load(it.image)
                    .into(characterPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ViewCharactersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

}

class MyViewHolder(val binding: ViewCharactersBinding) : ViewHolder(binding.root)


class DiffUtilCallback : DiffUtil.ItemCallback<CharacterFromRickAndMorty>() {
    override fun areItemsTheSame(
        oldItem: CharacterFromRickAndMorty,
        newItem: CharacterFromRickAndMorty
    ): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: CharacterFromRickAndMorty,
        newItem: CharacterFromRickAndMorty
    ): Boolean =
        oldItem.name == newItem.name && oldItem.species == newItem.species

}