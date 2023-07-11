package vn.thanhmagics.craftUtils.item

class StorageDoubleKey(private val key: Any?, private val key2: Any?) {
    fun getK1() : Any? {
        return key
    }
    fun getK2() : Any? {
        return key2
    }

}