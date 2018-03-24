package com.zpi.zpiapp.interactions

import com.zpi.zpiapp.model.Compound
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.Interaction

class Mock {
    companion object {
        fun interactions(): ArrayList<Interaction> {
            val c0 = Compound(0, "Paracetamol")
            val c1 = Compound(1, "Chlorowodorek pseudoefedryny")
            val c2 = Compound(2, "Bromowodorek dekstrometorfanu")
            val c3 = Compound(3, "Acyklowir")
            val c4 = Compound(4, "Agomelatyna")
            val c5 = Compound(5, "Akamprozat")
            val c6 = Compound(6, "Alprazolam")
            val c7 = Compound(7, "Papaweryna")

            val i0 = Interaction(0, 0, 1, "Polaczenie paracetamolu z chlorowodorkiem pseudoefedryny jest niebezpieczne dla twojego zdrowia xD", c0, c1)
            val i1 = Interaction(1, 2, 3, "Moze wywolywac zawroty glowy", c2, c3)
            val i2 = Interaction(2, 4, 5, "Lorem ipsum", c4, c5)
            val i3 = Interaction(3, 6, 7, "Papaweryna to taka karyna wsrod skladnikow lekow. Trzymaj sie od niej z daleka", c6, c7)

            val interakcje = ArrayList<Interaction>()
            interakcje.add(i0)
            interakcje.add(i1)
            interakcje.add(i2)
            interakcje.add(i3)
            return interakcje
        }

        fun drugs(): Array<Drug> {
            val d0 = Drug(0, "ACC Optima", 1, "czopek")
            val d1 = Drug(1, "Dentosept A Mini spray", 120, "spray")
            val d2 = Drug(2, "Gripex Intensive", 100, "tabletka")
            val d3 = Drug(3, "Ketonal active", 300, "syrop")
            return arrayOf(d0,d1,d2,d3)
        }
    }
}