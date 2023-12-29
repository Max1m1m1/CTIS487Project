package com.orhanefegokdemir.project.syste
import java.util.Collections
class SupplementSys {
    companion object {
        lateinit var supplementlist: ArrayList<Supplement>
        lateinit var supplementFav: ArrayList<Supplement>
        fun prepareData() {
            supplementlist = ArrayList()
            supplementFav = ArrayList()
            Collections.addAll(
                supplementlist,
                Supplement("Pre-Workout", 1),
                Supplement("L-Carnitine", 2),
            )

        }
    }
}