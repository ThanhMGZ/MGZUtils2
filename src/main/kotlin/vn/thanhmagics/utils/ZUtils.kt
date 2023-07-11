package vn.thanhmagics.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.lang.reflect.ParameterizedType
import java.util.UUID
import java.lang.reflect.Type;

class ZUtils {
    companion object {
        fun applyColor(s: String): String {
            return ChatColor.translateAlternateColorCodes('&', s);
        }

        fun applyColor(list: ArrayList<String>): ArrayList<String> {
            val lore: ArrayList<String> = ArrayList();
            for (s in list) {
                lore.add(applyColor(s))
            }
            return lore;
        }

        fun getServerPlayer() : ArrayList<UUID> {
            val rs = arrayListOf<UUID>()
            for (op in  Bukkit.getOnlinePlayers()) {
                if (!rs.contains(op.uniqueId))
                    rs.add(op.uniqueId)
            }
            for (op in Bukkit.getOfflinePlayers()) {
                if (!rs.contains(op.uniqueId))
                    rs.add(op.uniqueId)
            }
            return rs
        }

        inline fun <reified T> getGenericName(superclassType : Type) : String? {
           // val superclassType: Type = object : generictor<T>() {}.javaClass.genericSuperclass
            if (superclassType is ParameterizedType) {
                val typeArguments: Array<Type> = superclassType.actualTypeArguments
                if (typeArguments.isNotEmpty()) {
                    val typeArgument: Type = typeArguments[0]
                    return typeArgument.typeName
                }
            }
            return null
        }

    }
}