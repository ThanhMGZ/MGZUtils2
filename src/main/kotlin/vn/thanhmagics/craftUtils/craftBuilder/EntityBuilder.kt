package vn.thanhmagics.craftUtils.craftBuilder

import io.papermc.paper.entity.TeleportFlag
import it.unimi.dsi.fastutil.Hash
import net.kyori.adventure.text.Component
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity
import net.minecraft.server.network.PlayerConnection
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityTypes
import net.minecraft.world.level.World
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R3.CraftServer
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.Plugin
import org.bukkit.util.BoundingBox

class EntityBuilder(entity : Entity,craftServer : CraftServer) : CraftEntity(craftServer,entity) {

    private val originLoc : Location = location


    companion object {
        fun get(spawn : Location,type : EntityType,plugin: Plugin) : EntityBuilder {
            val en = spawn.world.spawnEntity(spawn,type);
            val entity = (en as CraftEntity).handle
            return EntityBuilder(entity,plugin.server as CraftServer)
        }
    }

    private var hide = false

    fun packetHideEntity(connection: PlayerConnection) : EntityBuilder {
        if (hide) return this
        val packet = PacketPlayOutEntityDestroy(entityId)
        connection.a(packet)
        hide = true
        return this
    }

    fun packetSpawnEntity(connection: PlayerConnection) : EntityBuilder {
        if (!hide) return this
        val packet = PacketPlayOutSpawnEntity(handle)
        connection.a(packet)
        hide = false
        return this
    }

    override fun name(): Component {
        return Component.text(name)
    }

    override fun customName(): Component {
        return Component.text(customName!!)
    }

    override fun customName(p0: Component?) {
        customName = p0.toString()
    }

    override fun teleport(p0: Location, p1: PlayerTeleportEvent.TeleportCause, vararg p2: TeleportFlag): Boolean {
        teleport(p0,p1)
        return true
    }

    override fun isFreezeTickingLocked(): Boolean {
        return false
    }

    override fun lockFreezeTicks(p0: Boolean) {
        return
    }

    override fun getType(): EntityType {
        return handle.bukkitEntity.type
    }

    override fun isSneaking(): Boolean {
        return false
    }

    override fun setSneaking(p0: Boolean) {
        return
    }

    override fun teamDisplayName(): Component {
        return Component.text("null")
    }

    override fun getOrigin(): Location {
        return originLoc
    }

    override fun fromMobSpawner(): Boolean {
        return false;
    }

    override fun getEntitySpawnReason(): CreatureSpawnEvent.SpawnReason {
        return CreatureSpawnEvent.SpawnReason.CUSTOM
    }

    override fun isUnderWater(): Boolean {
        return false
    }

    override fun isInRain(): Boolean {
        return false
    }

    override fun isInBubbleColumn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInWaterOrRain(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInWaterOrBubbleColumn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInWaterOrRainOrBubbleColumn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInLava(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTicking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTrackedPlayers(): MutableSet<Player> {
        TODO("Not yet implemented")
    }

    override fun spawnAt(p0: Location, p1: CreatureSpawnEvent.SpawnReason): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInPowderedSnow(): Boolean {
        TODO("Not yet implemented")
    }

    override fun collidesAt(p0: Location): Boolean {
        TODO("Not yet implemented")
    }

    override fun wouldCollideUsing(p0: BoundingBox): Boolean {
        TODO("Not yet implemented")
    }

}