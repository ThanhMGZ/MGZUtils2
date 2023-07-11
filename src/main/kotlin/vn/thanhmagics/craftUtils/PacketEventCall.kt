package vn.thanhmagics.craftUtils

import io.netty.channel.*
import net.minecraft.network.NetworkManager
import net.minecraft.server.network.PlayerConnection
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import vn.thanhmagics.craftUtils.event.PlayerPacketEvent
import vn.thanhmagics.craftUtils.item.Event_v2
import java.lang.reflect.Field


class PacketEventCall {

    companion object {

         fun getNetworkManager(connection : PlayerConnection) : NetworkManager {
            val field : Field = connection.javaClass.getDeclaredField("h")
            field.isAccessible = true
            return field.get(connection) as NetworkManager
        }

        fun removePlayer(player: Player) {
            val channel: Channel = getNetworkManager((player as CraftPlayer).handle.b).m
            channel.eventLoop().submit {
                channel.pipeline().remove(player.getName())
                null
            }
        }

        fun addPlayer(player: Player) {
            val channelDuplexHandler: ChannelDuplexHandler = object : ChannelDuplexHandler() {

                @Throws(java.lang.Exception::class)
                override fun channelRead(channelHandlerContext: ChannelHandlerContext?, packet: Any) {
                    val event = PlayerPacketEvent(packet,player)
                    Event_v2.callEvent(event)
                    if (event.isCancel())
                        return
                    super.channelRead(channelHandlerContext, packet)
                }

                @Throws(Exception::class)
                override fun write(
                    channelHandlerContext: ChannelHandlerContext,
                    packet: Any,
                    channelPromise: ChannelPromise,
                ) {
                    val event = PlayerPacketEvent(packet,player)
                    Event_v2.callEvent(event)
                    if (event.isCancel())
                        return
                    super.write(channelHandlerContext, packet, channelPromise)
                }
            }
            val pipeline: ChannelPipeline =
                getNetworkManager((player as CraftPlayer).handle.b).m.pipeline()
            pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler)
        }
    }

}