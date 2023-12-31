package vn.thanhmagics.craftUtils

import io.netty.channel.*
import net.minecraft.network.NetworkManager
import net.minecraft.server.network.PlayerConnection
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import vn.thanhmagics.craftUtils.event.PlayerPacketEvent
import java.lang.reflect.Field
import java.util.UUID


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
            }
            addedPlayer.remove(player.uniqueId)
        }

        private val addedPlayer : MutableList<UUID> = ArrayList()

        fun addPlayer(player: Player) {
            if (addedPlayer.contains(player.uniqueId))
                return
            addedPlayer.add(player.uniqueId)
            val channelDuplexHandler: ChannelDuplexHandler = object : ChannelDuplexHandler() {

                @Throws(java.lang.Exception::class)
                override fun channelRead(channelHandlerContext: ChannelHandlerContext?, packet: Any) {
                    val event = PlayerPacketEvent(player,packet)
                    Bukkit.getPluginManager().callEvent(event)
                    if (event.isCancelled)
                        return
                    super.channelRead(channelHandlerContext, packet)
                }

                @Throws(Exception::class)
                override fun write(
                    channelHandlerContext: ChannelHandlerContext,
                    packet: Any,
                    channelPromise: ChannelPromise,
                ) {
                    val event = PlayerPacketEvent(player,packet)
                    Bukkit.getPluginManager().callEvent(event)
                    if (event.isCancelled)
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