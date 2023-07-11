package vn.thanhmagics.craftUtils.craftBuilder

import com.mysql.cj.protocol.ExportControlled.sign
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPipeline
import io.netty.handler.codec.MessageToMessageDecoder
import net.minecraft.core.BlockPosition
import net.minecraft.network.NetworkManager
import net.minecraft.network.chat.IChatBaseComponent
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor
import net.minecraft.world.level.block.entity.TileEntitySign
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import vn.thanhmagics.craftUtils.PacketEventCall
import vn.thanhmagics.utils.PlayerData
import vn.thanhmagics.utils.ZUtils
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.internal.Reflection.function


abstract class SignGUIBuilder {

    private val lines : MutableList<String> = ArrayList(4)

    fun setLine(i: Int,content: String?) : SignGUIBuilder {
        if (i > 3)
            return this
        if (content != null) {
            this.lines[i] = content
            return this
        }
        this.lines[i] = ""
        return this
    }

    abstract fun onClose(player : Player, lines : MutableList<String>)

    fun open(player : Player) {
        val playerData = PlayerData.getPlayerData()[player.uniqueId]!!
        var signLoc = player.getLocation()
        signLoc = signLoc.clone().subtract(0.0, 63.0, 0.0)
        val material : Material = player.world.getBlockAt(signLoc).type
        val position = BlockPosition(signLoc.blockX, signLoc.blockY, signLoc.blockZ)
        val tileEntity = TileEntitySign(position, null)
        for (i in 0 until lines.size) {
            tileEntity.a(i, IChatBaseComponent.a(ZUtils.applyColor(lines[i])))
        }
        val networkManager = PacketEventCall.getNetworkManager((player as CraftPlayer).handle.b)
        player.sendBlockChange(signLoc, Material.OAK_SIGN.createBlockData())
        playerData.sendPacket(tileEntity.f())
        playerData.sendPacket(PacketPlayOutOpenSignEditor(position))
        val pipeline: ChannelPipeline = networkManager.m.pipeline()
        if (pipeline.names().contains("SignGUI")) pipeline.remove("SignGUI")
        pipeline.addAfter("decoder", "SignGUI", object : MessageToMessageDecoder<Packet<*>>() {
            override fun decode(chc: ChannelHandlerContext, packet: Packet<*>, out: MutableList<Any>) {
                try {
                    if (packet is PacketPlayInUpdateSign) {
                        if (packet.a() == position) {
                            pipeline.remove("SignGUI")
                            player.sendBlockChange(signLoc, signLoc.block.blockData)
                            signLoc.block.type = material
                            onClose(player,packet.c().toMutableList());
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                out.add(packet)
            }
        })
    }
}