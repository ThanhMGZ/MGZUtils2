package vn.thanhmagics.utils

import vn.thanhmagics.craftUtils.Storage


class StorageV2<K,V> : Storage {

    private val listK : MutableList<K> = ArrayList()

    private val listV : MutableList<V> = ArrayList()

    fun add(key : K,value : V) {
        if (key == null || value == null) return
        listK.add(key)
        listV.add(value)
    }

    fun getValue(key : K) : V? {
        if (!listK.contains(key)) return null;
        return listV[getIntByKey(key)!!]
    }

    fun getKey(value : V) : K? {
        if (!listV.contains(value)) return null;
        return listK[getIntByValue(value)!!]
    }

     fun removee(value : K) {
        if (!listK.contains(value)) return
        val i : Int = getIntByKey(value)!!
        listK.removeAt(i)
        listV.removeAt(i)
    }

     fun replacee(key : K, newValue : V) {
        if (!listK.contains(key)) return
        val i : Int = getIntByKey(key)!!
        listV[i] = newValue
    }

    private fun getIntByKey(key: K): Int? {
        for (i in 0 until listK.size) {
            if (listK[i] != null) {
                if (listK[i] == key) {
                    return i
                }
            }
        }
        return null
    }

    private fun getIntByValue(value: V): Int? {
        for (i in 0 until listV.size) {
            if (listV[i] != null) {
                if (listV[i] == value) {
                    return i
                }
            }
        }
        return null
    }



    override fun get(obj: Any?): Any? {
        if (obj == null) return null
        return getValue(obj as K)
    }

    override fun remove(obj: Any?) {
        if (obj == null) return
        removee(obj as K)
    }

    override fun replace(obj: Any?, new: Any?) {
        if (obj == null || new == null) return
        replacee(obj as K, new as V)
    }

    override fun size() : Int {
        return listK.size
    }

    override fun containsKey(obj: Any?): Boolean {
        if (obj == null) return false
        return containKey(obj as K)
    }

    override fun containsValue(obj: Any?): Boolean {
        if (obj == null) return false
        return containValue(obj as V)
    }

    fun containKey(key : K) : Boolean {
        return listK.contains(key)
    }

    fun containValue(value : V) :Boolean {
        return listV.contains(value)
    }



}