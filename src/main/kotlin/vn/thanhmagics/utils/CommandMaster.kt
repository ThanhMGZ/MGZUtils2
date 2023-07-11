package vn.thanhmagics.utils

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_19_R3.CraftServer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import vn.thanhmagics.craftUtils.item.CommandArg
import vn.thanhmagics.craftUtils.item.CommandArg_v2
import java.util.*
import kotlin.collections.ArrayList

abstract class CommandMaster(name: String,plugin: Plugin) : Command(name) {
    private val command : String = name
    private val commandArgs : MutableList<CommandArg> = ArrayList()
    private val commandArgs_v2 : MutableList<CommandArg_v2> = ArrayList()
    private lateinit var player : PlayerData
    val plugin : Plugin = plugin

    init {
        val craftServer : CraftServer = plugin.server as CraftServer;
        craftServer.commandMap.register("MGZUtils2",this);
    }

    fun addArg(vararg commands : CommandArg) {
        this.commandArgs.addAll(commands);
    }

    fun addArg_v2(vararg commands : CommandArg_v2) {
        this.commandArgs_v2.addAll(commands)
    }

    abstract fun onCommand(player : Player)

    abstract fun unknownCommand() : String

    abstract fun permission() : String?

    abstract fun nonPermission() : String?

    override fun execute(p0: CommandSender, p1: String, args: Array<out String>): Boolean {
        val p : Player = p0 as Player
        if (permission() != null) {
            if (!p.hasPermission(permission()!!)) {
                p.sendMessage(ZUtils.applyColor(nonPermission()!!))
                return true;
            }
        }
        val size : Int = (args.size)
        if (size == 0) {
            onCommand(p)
            return true
        } else {
            this.player = PlayerData.getPlayerData().getValue(p.uniqueId)
            for (cm in commandArgs_v2) {
                cm.setPlayer(player)
                cm.run()
            }
            if (p1.equals(command, ignoreCase = true)) {
                val list = sortCommandArg(size)
                if (list.isEmpty()) {
                    p.sendMessage(ZUtils.applyColor(unknownCommand()))
                    return true;
                }
                for (cm in list) {
                    if (cm != null) {
                        if (cm.getNameValue().equals(args[size - 1], true)) {
                            cm.setPlayer(p)
                            cm.run()
                            return true
                        }
                    }
                }
            }
        }
        p.sendMessage(ZUtils.applyColor(unknownCommand()))
        return true;
    }

    private fun sortCommandArg(length : Int) : Array<CommandArg?> {
        val new : Array<CommandArg?> = arrayOfNulls(commandArgs.size)
        var i = 0;
        for (arg in commandArgs) {
            if (arg.getArgValue() == length) {
                new[i] = arg
                i++
            }
        }
        return new
    }
}