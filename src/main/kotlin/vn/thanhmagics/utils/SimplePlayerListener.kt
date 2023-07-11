package vn.thanhmagics.utils

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin
import vn.thanhmagics.craftUtils.PacketEventCall

class SimplePlayerListener {

    companion object {
        fun joinEvent(plugin: Plugin) {
            object : ListenerUtils<PlayerJoinEvent>(plugin) {
                override fun onEvent(e: PlayerJoinEvent) {
                    if (!PlayerData.getPlayerData().containsKey(e.player.uniqueId)) {
                        PlayerData(e.player.uniqueId)
                        PlayerData.getPlayerData()[e.player.uniqueId]!!.setPlayer(e.player)
                    }
                    PacketEventCall.addPlayer(e.player)
                }

                override val handlerList: HandlerList
                    get() = PlayerJoinEvent.getHandlerList()

            }
        }

        fun quitEvent(plugin: Plugin) {
            object : ListenerUtils<PlayerQuitEvent>(plugin) {
                override fun onEvent(e: PlayerQuitEvent) {
                    PlayerData.getPlayerData()[e.player.uniqueId]!!.setPlayer(null)
                    PacketEventCall.removePlayer(e.player)
                }

                override val handlerList: HandlerList
                    get() = PlayerQuitEvent.getHandlerList()

            }
        }

        fun invCloseEvent(plugin: Plugin) {
            object : ListenerUtils<InventoryClickEvent>(plugin) {
                override fun onEvent(e: InventoryClickEvent) {
                    val pd = PlayerData.getPlayerData()[(e.whoClicked as Player).uniqueId]!!
                    if (pd.getInventory() != null) {
                        pd.setInventory(null)
                    }
                }

                override val handlerList: HandlerList
                    get() = InventoryClickEvent.getHandlerList()

            }
        }
    }

}