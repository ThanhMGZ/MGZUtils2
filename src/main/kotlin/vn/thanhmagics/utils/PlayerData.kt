package vn.thanhmagics.utils

import net.minecraft.network.protocol.Packet
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import vn.thanhmagics.craftUtils.craftBuilder.TextComponentBuilder
import java.io.Serializable
import java.util.*


open class PlayerData(uuid: UUID) : Serializable {

    companion object {
        private val playerData : MutableMap<UUID, PlayerData> = HashMap()

        fun getPlayerData() : MutableMap<UUID, PlayerData> {
            return playerData
        }
    }

    private val id : UUID = uuid;

    private var player : Player? = null

    private var inventory : UUID? = null

    init {
        if (!getPlayerData().containsKey(id)) {
            getPlayerData()[id] = this
        }
    }


    fun getUUID() : UUID {
        return id
    }

    fun getPlayer() : Player? {
        return player
    }

    fun setPlayer(player: Player?) {
        this.player = player
    }


    fun getInventory() : UUID? {
        return this.inventory
    }

    fun setInventory(inventory: UUID) {
        this.inventory = inventory
    }

    fun sendMessage(str : String) {
        if (player == null) return
        player?.sendMessage(str)
    }

    fun sendMessage(int : Int) {
        if (player == null) return
        player?.sendMessage(int.toString())
    }

    fun sendMessage(listOfStr : MutableList<String>) {
        for (v in listOfStr)
            sendMessage(v)
    }

    fun sendMessage(vararg mess : TextComponentBuilder) {
        for (message in mess)
            player!!.spigot().sendMessage(message.build())
    }

    fun sendPacket(packet : Packet<*>) {
        (player as CraftPlayer).handle.b.a(packet)
    }

}