package vn.thanhmagics.craftUtils.event

import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.PacketListenerPlayOut
import org.bukkit.entity.Player
import vn.thanhmagics.craftUtils.item.Event_v2
import vn.thanhmagics.utils.ListenerUtils_v2

class PlayerPacketEvent(private val packet: (Any), private val player: Player) : Event_v2 {

    private var cancel : Boolean = false

    fun cancelEvent() {
        this.cancel = true
    }

    fun getPlayer() : Player {
        return player
    }

    fun isCancel() : Boolean {
        return cancel
    }

    fun getPacket() : Any {
        return packet
    }

    override fun run() {
        for (e in Event_v2.getHandlerList(javaClass.name)) {
            (e as ListenerUtils_v2<PlayerPacketEvent>).onEvent(this)
        }
    }
}