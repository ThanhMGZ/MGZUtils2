package vn.thanhmagics.craftUtils.event

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import vn.thanhmagics.utils.PlayerData

class PlayerPacketEvent(player: Player, any: Any) : Event(), Cancellable{
    private val handlers = HandlerList()

    private var canncel = false

    private var player : Player? = player

    private var packet : Any? = any

    fun getPlayer() : Player {
        return player!!
    }

    fun getPacket() : Any {
        return packet!!
    }

    override fun getHandlers(): HandlerList {
        return handlers
    }

    override fun isCancelled(): Boolean {
        return canncel
    }

    override fun setCancelled(p0: Boolean) {
        this.canncel = p0
    }

}