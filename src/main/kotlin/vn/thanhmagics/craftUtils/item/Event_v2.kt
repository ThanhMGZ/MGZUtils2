package vn.thanhmagics.craftUtils.item

import vn.thanhmagics.utils.ListenerUtils_v2
import java.lang.reflect.ParameterizedType




interface Event_v2 : Runnable {

    companion object {
        private val handlerList : HashMap<String,ListenerUtils_v2<out Event_v2>> = HashMap()

        private val fastList : HashMap<String,MutableList<ListenerUtils_v2<out Event_v2>>> = HashMap()

        fun addHandler(k : String,v : ListenerUtils_v2<out Event_v2>) {
            handlerList[k] = v
        }

        fun getHandlerList(key : String) : MutableList<ListenerUtils_v2<out Event_v2>> {
            if (fastList.containsKey(key))
                return fastList[key]!!
            val rs : MutableList<ListenerUtils_v2<out Event_v2>> = ArrayList()
            for (k in handlerList.keys) {
                if (key == k) {
                    rs.add(handlerList[k]!!)
                }
            }
            fastList[key] = rs
            return rs;
        }


        fun callEvent(event : Event_v2) {
            event.run()
        }
    }

}