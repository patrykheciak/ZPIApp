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
                            var items: MutableList<CareAssistant> = mutableListOf())
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

    fun deleteItem( careAssistant: CareAssistant ){
        items.indexOf(careAssistant).let {
            items.removeAt(it)
            notifyItemRemoved(it)
        }
    }

    fun addItemToBeginning( careAssistant:CareAssistant ){
        items.add(0,careAssistant)
        notifyItemInserted(0)
    }

    fun addItemToEnd(careAssistant: CareAssistant ){
        items.add(careAssistant)
        notifyItemInserted(items.size)
    }

    fun replaceItems( careAssistantCollection: Collection<CareAssistant> ){
        removeCollection()
        careAssistantCollection.forEach { addItemToEnd(it) }
    }

    fun removeCollection(){
        while (items.isEmpty().not()){
            items.removeAt(0)
            notifyItemRemoved(0)
        }
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