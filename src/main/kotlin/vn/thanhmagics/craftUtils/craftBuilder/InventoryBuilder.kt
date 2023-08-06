package vn.thanhmagics.craftUtils.craftBuilder

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin
import vn.thanhmagics.utils.*
import java.util.UUID

class InventoryBuilder<P : PlayerData>(title : String, size : Int, private val plugin : Plugin,private val playerMap : MutableMap<UUID,P>) {

    private var inventory : Inventory = Bukkit.createInventory(null,size,ZUtils.applyColor(title))

    private var uuid = UUID.randomUUID()

    private var cancelOnClick : Boolean = true

    private val leftClickAction : MutableMap<Int,Runnable> = HashMap()

    private val rightClickAction : MutableMap<Int,Runnable> = HashMap()

    init {
        object : ListenerUtils<InventoryClickEvent>(plugin) {
            override fun onEvent(e: InventoryClickEvent) {
                val data = playerMap[(e.whoClicked as Player).uniqueId]!!
                if (data.getInventory() != null) {
                    if (data.getInventory()!! == uuid) {
                        e.isCancelled = cancelOnClick
                        if (leftClickAction.containsKey(e.slot) || rightClickAction.containsKey(e.slot)) {
                            if (e.click == ClickType.RIGHT) {
                                rightClickAction[e.slot]!!.run()
                            } else if (e.click == ClickType.LEFT) {
                                leftClickAction[e.slot]!!.run()
                            }
                        }
                    }
                }
            }
        }
    }

    fun addItemAction(slot: Int,item: ItemBuilder?,leftClick: Runnable,rightClick : Runnable) : InventoryBuilder<P> {
        inventory.setItem(slot,item!!.build())
        leftClickAction[slot] = leftClick
        rightClickAction[slot] = rightClick
        return this
    }

    fun addItemActionLeftClick(slot: Int,item: ItemBuilder?,leftClick: Runnable) : InventoryBuilder<P> {
        inventory.setItem(slot,item!!.build())
        leftClickAction[slot] = leftClick
        return this
    }

    fun addItemActionRightClick(slot: Int,item: ItemBuilder?,rightClick : Runnable) : InventoryBuilder<P> {
        inventory.setItem(slot,item!!.build())
        rightClickAction[slot] = rightClick
        return this
    }

    fun addItemAction(slot: Int,item: ItemBuilder?,runnable : Runnable) : InventoryBuilder<P> {
        inventory.setItem(slot,item!!.build())
        leftClickAction[slot] = runnable
        rightClickAction[slot] = runnable
        return this
    }

    fun addDecoItem(item : ItemBuilder?,vararg slot : Int) : InventoryBuilder<P> {
        if (item == null)
            return this
        val itemStack = item.build()
        for (i in slot) {
            inventory.setItem(i,itemStack)
        }
        return this
    }

    fun setCancelOnClick(boolean: Boolean) : InventoryBuilder<P> {
        this.cancelOnClick = boolean
        return this;
    }

    fun getInventory() : Inventory {
        return inventory
    }



    fun open(player : PlayerData,plugin: Plugin) {
        Bukkit.getScheduler().runTask(plugin, Runnable {
            player.getPlayer()!!.closeInventory()
            player.setInventory(uuid)
            player.getPlayer()!!.openInventory(inventory)
        })
    }


}