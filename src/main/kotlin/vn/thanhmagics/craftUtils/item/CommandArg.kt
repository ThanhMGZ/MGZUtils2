package vn.thanhmagics.craftUtils.item

import org.bukkit.entity.Player
import vn.thanhmagics.utils.PlayerData

abstract class CommandArg(argValue: Int, nameValue: String) {

    private val argField: Int = argValue
    private val nameField: String = nameValue
    private var player: Player? = null

    abstract fun run()

    fun getPlayer(): Player {
        return player!!
    }

    fun setPlayer(player: Player) {
        //this.player = getPlayerData().getValue(player.uniqueId)!!
        this.player = player
    }

    fun getArgValue(): Int {
        return argField
    }

    fun getNameValue(): String {
        return nameField
    }
}