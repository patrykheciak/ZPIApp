package com.zpi.zpiapp.utlis

/**
 * Created by sirma on 09.04.2018.
 */
class SimpleBag<T> {
    val list
        get() :List<T> = mList

    private val mList = mutableListOf<T>()

    public fun add( element : T ):Boolean{
        return _add(element)
    }

    public fun remove( element: T ):Boolean{
        return mList.remove(element)
    }

    public fun reload( elementCollection: Collection<T> , comparator: Comparator<T>? = null): Boolean{
        val list = mutableListOf<T>()
        val iterator = elementCollection.iterator()

        var repeat = false
        var next:T

        while (repeat.not() && iterator.hasNext()){
            next = iterator.next()
            if (list.contains(next).not())
                list.add(next)
            else repeat = true
        }

        return if (repeat)
            false
        else{
            mList.clear()
            comparator?.run { mList.addAll(elementCollection.sortedWith(comparator))  }
            true
        }
    }


    private fun _add( element : T ):Boolean{
        if (mList.contains(element))
            return false

        mList.add(0,element)
        return true
    }
}