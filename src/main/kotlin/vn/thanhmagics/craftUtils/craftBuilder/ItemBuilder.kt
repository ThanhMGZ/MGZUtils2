package vn.thanhmagics.craftUtils.craftBuilder

import com.destroystokyo.paper.profile.ProfileProperty
import net.minecraft.nbt.NBTTagCompound
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import vn.thanhmagics.craftUtils.ItemNBTS
import vn.thanhmagics.utils.ZUtils
import java.util.*

class ItemBuilder {

    private var itemStack : ItemStack

    private var meta : ItemMeta

    private var lore = ArrayList<String>()


    constructor(stack : ItemStack) {
        this.itemStack = stack
        meta = stack.itemMeta!!
    }

    constructor(material: Material) {
        this.itemStack = ItemStack(material);
        this.meta = itemStack.itemMeta!!
    }

    constructor(value : String) {
        this.itemStack = itemStackByValue(value);
        this.meta = itemStack.itemMeta!!
    }


    fun getNBTTagCompound() : NBTTagCompound {
        return CraftItemStack.asNMSCopy(itemStack).u()!!
    }

    fun getServerItem() : net.minecraft.world.item.ItemStack
    {return CraftItemStack.asNMSCopy(itemStack)}

    fun addNBT(k : String,v : String) : ItemBuilder {
        val nbt : NBTTagCompound = getNBTTagCompound();
        nbt.a(k,v)
        getServerItem().c(nbt)
        return this;
    }

    fun containsNBT(k : String) : Boolean {
        return getNBTTagCompound().e().contains(k)
    }

    fun removeNBT(k : String) : ItemBuilder {
        if (!containsNBT(k)) return this
        val nbt : NBTTagCompound = getNBTTagCompound();
        nbt.r(k)
        getServerItem().c(nbt)
        return this;
    }

    fun displayName(str: String?): ItemBuilder {
        if (str != null) meta.setDisplayName(ZUtils.applyColor(str))
        return this
    }

    fun addDisplayName(str: String?): ItemBuilder {
        if (str != null) {
            if (meta.displayName.length == 0) {
                meta.setDisplayName(itemStack.getI18NDisplayName() + ZUtils.applyColor(str))
                return this
            }
            meta.setDisplayName(meta.displayName + ZUtils.applyColor(str))
        }
        return this
    }

    fun lore(str: String?): ItemBuilder {
        if (str != null) lore.add(ZUtils.applyColor(str))
        return this
    }

    fun lore(strs: List<String?>): ItemBuilder {
        for (s in strs) lore(s)
        return this
    }

    fun enchant(b: Boolean): ItemBuilder {
        if (b) {
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        } else {
            for (enchantment in meta.enchants.keys) {
                meta.removeEnchant(enchantment!!)
            }
        }
        return this
    }

    fun build(): ItemStack {
        meta.lore = lore
        itemStack.setItemMeta(meta)
        return itemStack
    }



    fun itemStackByValue(value : String) : ItemStack {
        val itemStack = ItemStack(Material.PLAYER_HEAD, 1, 3.toShort())
        val meta = itemStack.itemMeta as SkullMeta

        val profile = Bukkit.createProfile(UUID.randomUUID())
        profile.properties.add(ProfileProperty("textures", value))
        meta.playerProfile = profile
        itemStack.setItemMeta(meta)
        return itemStack;
    }

}