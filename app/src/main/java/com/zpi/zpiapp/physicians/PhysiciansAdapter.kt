package com.zpi.zpiapp.physicians

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.Physician
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.physicians_row.*

class PhysiciansAdapter(val clickListener: ClickListener,
                            var items: MutableList<Physician> = mutableListOf())
    : RecyclerView.Adapter<PhysiciansAdapter.PhysicianVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhysicianVH {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.physicians_row,parent,false)
        return PhysicianVH(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PhysicianVH, position: Int) {
        holder.bind(items[position])
    }

    fun deleteItem( physician: Physician){
        items.indexOf(physician).let {
            items.removeAt(it)
            notifyItemRemoved(it)
        }
    }

    fun addItemToBeginning( physician: Physician ){
        items.add(0,physician)
        notifyItemInserted(0)
    }

    fun addItemToEnd(physician: Physician ){
        items.add(physician)
        notifyItemInserted(items.size)
    }

    fun replaceItems( physicianCollection: Collection<Physician> ){
        removeCollection()
        physicianCollection.forEach { addItemToEnd(it) }
    }

    fun removeCollection(){
        while (items.isEmpty().not()){
            items.removeAt(0)
            notifyItemRemoved(0)
        }
    }


    inner class PhysicianVH(override val containerView:View):RecyclerView.ViewHolder(containerView),LayoutContainer {
        fun bind(physician: Physician){
            physician_row_name.text="${physician.name} ${physician.surname}"
            physician_row_pwz.text=physician.pwzNumber

            physician_row_remove_button.setOnClickListener({
                clickListener.onRemoveButtonClicked(physician.idPhysician)
            })
        }
    }

    public interface ClickListener{
        fun onRemoveButtonClicked( physicianId: Int )
    }

}