package vn.thanhmagics.utils

import vn.thanhmagics.craftUtils.item.Event_v2

abstract class ListenerUtils_v2<E : Event_v2> {

    abstract fun onEvent(event: E)

    init {
        val sct = javaClass.genericSuperclass
        Event_v2.addHandler(ZUtils.getGenericName<ListenerUtils_v2<E>>(sct)!!,this)

    }

}