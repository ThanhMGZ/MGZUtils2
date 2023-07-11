package vn.thanhmagics.utils

import vn.thanhmagics.craftUtils.Storage
import vn.thanhmagics.craftUtils.item.StorageDoubleKey

class StorageV3<K,E,V> : Storage {

    private val className : String = StorageDoubleKey::javaClass.name

    private val listK : MutableList<K> = ArrayList()

    private val listE : MutableList<E> = ArrayList()

    private val listV : MutableList<V> = ArrayList()

    fun add(key : K,e : E,value : V) {
        if (key == null || value == null || e == null) return
        listK.add(key)
        listE.add(e)
        listV.add(value)
    }

    fun getValue(key : K,e : E) : V? {
        if (!listK.contains(key)) return null;
        if (!listE.contains(e)) return null;
        return listV[getIntByKey(key)!!]
    }


    fun getKey1(value : V) : K? {
        if (!listV.contains(value)) return null;
        return listK[getIntByValue(value)!!]
    }

    fun getKey2(value : V) : E? {
        if (!listV.contains(value)) return null;
        return listE[getIntByValue(value)!!]
    }

    fun remove(value : K,e : E) {
        if (!listK.contains(value)) return
        if (!listE.contains(e)) return
        val i : Int = getIntByKey(value)!!
        listK.removeAt(i)
        listE.removeAt(i)
        listV.removeAt(i)
    }

    fun replace(key : K,e : E,newValue : V) {
        if (!listK.contains(key)) return
        if (!listE.contains(e)) return
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
        if (obj == null) return null;
        return getValue((obj as StorageDoubleKey).getK1() as K,obj.getK2() as E)
    }

    override fun remove(obj: Any?) {
        if (obj == null) return
        remove((obj as StorageDoubleKey).getK1() as K,obj.getK2() as E)
    }

    override fun replace(obj: Any?, new: Any?) {
        if (obj == null || new == null) return
        replace((obj as StorageDoubleKey).getK1() as K,obj.getK2() as E)
    }

    override fun size() : Int {
        return listK.size
    }

    override fun containsKey(obj: Any?): Boolean {
        val b1 = containKey1((obj as StorageDoubleKey).getK1() as K)
        val b2 = containKey2(obj.getK2() as E)
        return b1 && b2
    }

    override fun containsValue(obj: Any?): Boolean {
        return containValue(obj as V)
    }

    fun containKey1(key : K) : Boolean {
        return listK.contains(key)
    }

    fun containKey2(e : E) : Boolean {
        return listE.contains(e)
    }

    fun containValue(value : V) :Boolean {
        return listV.contains(value)
    }


}