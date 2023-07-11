package vn.thanhmagics.craftUtils

interface Storage {
    fun get(obj : Any?) : Any?
    fun remove(obj : Any?)
    fun replace(obj : Any?,new : Any?)
    fun size() : Int
    fun containsKey(obj: Any?) : Boolean
    fun containsValue(obj : Any?) : Boolean
}