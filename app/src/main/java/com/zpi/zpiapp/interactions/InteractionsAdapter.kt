package com.zpi.zpiapp.interactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.Interaction
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.interactions_row.*

class InteractionsAdapter(var items: List<Interaction>) : RecyclerView.Adapter<InteractionsAdapter.CustomVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interactions_row, parent, false)
        return CustomVH(v)
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateInteractions(interactions: List<Interaction>) {
        items = interactions
        notifyDataSetChanged()
    }

    class CustomVH(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(interaction: Interaction) {
            compound1.setText(interaction.idCompoundNavigation.internationalName)
            compound2.setText(interaction.comIdCompoundNavigation.internationalName)
            description.setText(interaction.interactionInfo)
        }
    }
}