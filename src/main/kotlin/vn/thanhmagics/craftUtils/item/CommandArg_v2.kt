package vn.thanhmagics.craftUtils.item

import org.codehaus.plexus.util.cli.Arg
import vn.thanhmagics.utils.PlayerData

abstract class CommandArg_v2 : Runnable {

    private lateinit var player : PlayerData

    fun getPLayer() : PlayerData {
        return player
    }

    fun setPlayer(player : PlayerData) {
        this.player = player
    }
}