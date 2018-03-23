package com.zpi.zpiapp.interactions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import com.zpi.zpiapp.model.Compound
import com.zpi.zpiapp.model.Interaction
import kotlinx.android.synthetic.main.fragment_interactions.*


class InteractionsFragment : Fragment(), InteractionsContract.View {

    override fun setPresenter(presenter: InteractionsContract.Presenter) {
        TODO("not implemented") //To change body of created functions use File | Settings | // File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_interactions, container, false)

        val drugs = resources.getStringArray(R.array.drug_array)


        val actv = view.findViewById<AutoCompleteTextView>(R.id.comp1)
        val adapter = ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, drugs)
        actv.setAdapter(adapter)




        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        val c0 = Compound(0, "Paracetamol")
        val c1 = Compound(1, "Chlorowodorek pseudoefedryny")
        val c2 = Compound(2, "Bromowodorek dekstrometorfanu")
        val c3 = Compound(3, "Acyklowir")
        val c4 = Compound(4, "Agomelatyna")
        val c5 = Compound(5, "Akamprozat")
        val c6 = Compound(6, "Alprazolam")
        val c7 = Compound(7, "Papaweryna")

        val i0 = Interaction(0, 0, 1, "Polaczenie paracetamolu z chlorowodorkiem pseudoefedryny jest niebezpieczne dla twojego zdrowia xD", c0, c1)
        val i1 = Interaction(1, 2, 3, "Moze wywolywac zawroty glowy", c2,c3)
        val i2 = Interaction(2, 4,5, "Lorem ipsum", c4,c5)
        val i3 = Interaction(3, 6,7,"Papaweryna to taka karyna wsrod skladnikow lekow. Trzymaj sie od niej z daleka", c6,c7)

        val interakcje = ArrayList<Interaction>()
        interakcje.add(i0)
        interakcje.add(i1)
        interakcje.add(i2)
        interakcje.add(i3)
        recycler_interactions.adapter = InteractionsAdapter(interakcje)
        recycler_interactions.layoutManager = LinearLayoutManager(context)
    }

}// Required empty public constructor
