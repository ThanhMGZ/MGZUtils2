package vn.thanhmagics.utils

import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

abstract class ListenerUtils<E : Event>(plugin: Plugin) : Listener {
    abstract fun onEvent(e: E)
    abstract val handlerList: HandlerList

    init {
        for ((_, value) in plugin.pluginLoader.createRegisteredListeners(this, plugin)) {
            handlerList.registerAll(value!!)
        }
    }

    @EventHandler
    fun run(e: E) {
        onEvent(e)
    }
}
