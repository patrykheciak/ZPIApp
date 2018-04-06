package com.zpi.zpiapp.careAssistants

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.CareAssistant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.care_assistants_row.*

class CareAssistantsAdapter(val clickListener: ClickListener,
                            var items: List<CareAssistant> = emptyList())
    : RecyclerView.Adapter<CareAssistantsAdapter.CareAssistantVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareAssistantVH {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.care_assistants_row,parent,false)
        return CareAssistantVH(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CareAssistantVH, position: Int) {
        holder.bind(items[position])
    }



    inner class CareAssistantVH(override val containerView:View):RecyclerView.ViewHolder(containerView),LayoutContainer {
        fun bind(careAssistant: CareAssistant){
            care_assistant_row_surname.text=careAssistant.surname
            care_assistant_row_name.text=careAssistant.name

            care_assostant_row_remove_button.setOnClickListener({
                clickListener.onRemoveButtonClicked(careAssistant.idCareAssistant)
            })
        }
    }

    public interface ClickListener{
        fun onRemoveButtonClicked( careAssistantId: Int )
    }

}