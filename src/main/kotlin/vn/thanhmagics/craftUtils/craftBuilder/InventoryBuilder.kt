package vn.thanhmagics.craftUtils.craftBuilder

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin
import vn.thanhmagics.utils.ListenerUtils
import vn.thanhmagics.utils.PlayerData
import vn.thanhmagics.utils.ZUtils
import java.util.UUID

class InventoryBuilder(title : String, size : Int, private val plugin : Plugin) {

    private var inventory : Inventory = Bukkit.createInventory(null,size,ZUtils.applyColor(title))

    private val uuid = UUID.randomUUID()

    private var cancelOnClick : Boolean = true

    private val actionItem : MutableMap<Int,Runnable> = HashMap()

    private val actionItemWithSign : MutableMap<Int,SignGUIBuilder> = HashMap()

    init {
        object : ListenerUtils<InventoryClickEvent>(plugin) {
            override fun onEvent(e: InventoryClickEvent) {
                val data = PlayerData.getPlayerData()[(e.whoClicked as Player).uniqueId]!!
                if (data.getInventory() != null) {
                    if (data.getInventory()!! == uuid) {
                        e.isCancelled = cancelOnClick
                        if (actionItem.containsKey(e.slot)) {
                            actionItem[e.slot]!!.run()
                        } else if (actionItemWithSign.containsKey(e.slot)) {
                            e.whoClicked.closeInventory()
                            actionItemWithSign[e.slot]!!.open(e.whoClicked as Player)
                        }
                    }
                }
            }

            override val handlerList: HandlerList
                get() = InventoryClickEvent.getHandlerList()
        }
    }

    fun addActionItemWithSignEdit(slot : Int,item: ItemBuilder?,sign : SignGUIBuilder) : InventoryBuilder {
        inventory.setItem(slot,item!!.build())
        actionItemWithSign[slot] = sign
        return this
    }

    fun addActionItem(slot : Int,item: ItemBuilder?, onClick : Runnable) : InventoryBuilder {
        inventory.setItem(slot,item!!.build())
        actionItem[slot] = onClick
        return this
    }

    fun addDecoItem(item : ItemBuilder?,vararg slot : Int) : InventoryBuilder {
        if (item == null)
            return this
        val itemStack = item.build()
        for (i in slot) {
            inventory.setItem(i,itemStack)
        }
        return this
    }

    fun setCancelOnClick(boolean: Boolean) : InventoryBuilder {
        this.cancelOnClick = boolean
        return this;
    }

    fun getInventory() : Inventory {
        return inventory
    }

    fun getItemAction() : MutableMap<Int,Runnable> {
        return actionItem
    }

    fun getItemActionWithSign() : MutableMap<Int,SignGUIBuilder> {
        return actionItemWithSign
    }

    fun open(player : Player?) {
        PlayerData.getPlayerData()[player!!.uniqueId]!!.setInventory(uuid)
        player.openInventory(inventory)
    }

}