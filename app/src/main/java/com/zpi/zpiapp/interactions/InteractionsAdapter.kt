package com.zpi.zpiapp.interactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.Interaction
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.interactions_row.*

class InteractionsAdapter(var items: List<Interaction> = emptyList()) : RecyclerView.Adapter<InteractionsAdapter.InteractionVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InteractionVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interactions_row, parent, false)
        return InteractionVH(v)
    }

    override fun onBindViewHolder(holder: InteractionVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateInteractions(interactions: List<Interaction>) {
        items = interactions
        notifyDataSetChanged()
    }

    class InteractionVH(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(interaction: Interaction) {
            compound1.text = interaction.firstIngredient
            compound2.text = interaction.secondIngredient
            description.text = interaction.interactionDesctiption
        }
    }
}