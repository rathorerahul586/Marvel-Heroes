package com.rathoreapps.marvelheros

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.GONE
import android.view.ViewGroup.VISIBLE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.rathoreapps.marvelheros.dataModels.MarvelCharacter
import com.rathoreapps.marvelheros.databinding.HeroesItemViewBinding
import com.rathoreapps.marvelheros.utils.dpToPixels
import com.rathoreapps.marvelheros.utils.removeEscapeCharacters
import com.rathoreapps.marvelheros.utils.setImageWithGlideCache

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 09/12/23
 * @Time: 1:45 pm
 * @Email: rathoreapps01@gmail.com
 *
 * Description: Adapter for Marvel characters Recycler view
 */
class HeroesListAdapter : RecyclerView.Adapter<HeroesListAdapter.HeroesViewHolder>() {

    private var dataList: List<MarvelCharacter> = ArrayList()

    private var currentExpendedItem: Int = -1
    private var previousExpendedItem: Int = -1

    /**
     * Method to set list of in adapter. This method notify the recycler view for list update
     * @param charList - List of Marvel Characters
    * */
    fun setCharacterList(charList: List<MarvelCharacter>) {
        dataList = charList
        notifyItemRangeChanged(0, charList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        return HeroesViewHolder(
            HeroesItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {

        holder.setCharacter(dataList[position]) {
            previousExpendedItem = currentExpendedItem
            currentExpendedItem = it
            if (currentExpendedItem != previousExpendedItem) {
                notifyItemChanged(previousExpendedItem)
            }
        }
    }

    inner class HeroesViewHolder(private val itemBinding: HeroesItemViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private var isExpended = false
        private var context = itemBinding.root.context

        /**
         * Method to set character details in the recyclerview item
         * @param character - Character to set details in card
         * @param onItemTapped - Callback to get tapped item.
         * */
        fun setCharacter(character: MarvelCharacter, onItemTapped: ((position: Int) -> Unit)) {
            isExpended = currentExpendedItem == adapterPosition
            with(itemBinding) {
                heroName.text = character.name
                heroImage.setImageWithGlideCache(character.imageUrl, character.name)
                heroBio.text = character.bio.removeEscapeCharacters()
                createdBy.text = character.createdBy
                publisher.text = character.publisher
                team.text = character.team
                realName.text = character.realName
                root.setOnClickListener {
                    onItemTapped(adapterPosition)
                    expendOrCollapseItem(!isExpended)
                }
            }
            expendOrCollapseItem(isExpended)
        }

        /**
         * Method to collapse or expend item and apply related appearance on the card UI
         * @param expend true for expanding card
         * */
        private fun expendOrCollapseItem(expend: Boolean) {
            if (expend) {
                expendItem()
            } else {
                collapseItem()
            }
            setUIAppearance(forExpended = expend)
        }

        /**
         * Method to expend recycler view item
         * */
        private fun expendItem() {
            itemBinding.heroImage.updateLayoutParams<ConstraintLayout.LayoutParams> {
                height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                width = ConstraintLayout.LayoutParams.MATCH_PARENT
            }
            itemBinding.heroName.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = itemBinding.parentConstraintLayout.id

            }
            itemBinding.heroBio.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = itemBinding.parentConstraintLayout.id
            }
        }

        /**
         * Method to collapse expended recycler view item
         * */
        private fun collapseItem() {
            itemBinding.heroImage.updateLayoutParams<ConstraintLayout.LayoutParams> {
                height = 140.dpToPixels(context)
                width = 120.dpToPixels(context)
            }
            itemBinding.heroName.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.UNSET
            }

            itemBinding.heroBio.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.UNSET
            }
        }

        /**
         * Method to set view appearance on recycler view item
         * @param forExpended true for expended item appearance
         * */
        private fun setUIAppearance(forExpended: Boolean) {
            if (forExpended) {
                itemBinding.heroImage.foreground = ContextCompat.getDrawable(
                    context, R.drawable.rounded_card_16_dp_dim
                )
                itemBinding.heroName.setTextColor(ContextCompat.getColor(context, R.color.white))
                with(itemBinding.heroBio) {
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    maxLines = Int.MAX_VALUE
                }
                itemBinding.layoutGroup.visibility = VISIBLE
            } else {
                itemBinding.heroImage.foreground = null
                itemBinding.heroName.setTextColor(ContextCompat.getColor(context, R.color.black))
                with(itemBinding.heroBio) {
                    setTextColor(ContextCompat.getColor(context, R.color.grey))
                    maxLines = 3
                }
                itemBinding.layoutGroup.visibility = GONE
            }
        }
    }
}