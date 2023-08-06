package vn.thanhmagics.utils

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.sql.*

class SQLite {
    private var plugin: JavaPlugin? = null

    private var connection: Connection? = null

    private var file: File? = null

    private val contents: MutableList<String> = ArrayList()

    private var primaryKey: String? = null

    private var tableName : String? = null

    fun MGZSQLite(plugin: JavaPlugin?) {
        this.plugin = plugin
    }

    fun init(tableName: String, primaryKey: String?, vararg sqLiteContents: SQLiteContent) {
        this.tableName = tableName
        this.primaryKey = primaryKey
        file = File(plugin!!.dataFolder, "database.db")
        if (!file!!.exists()) {
            try {
                file!!.createNewFile()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        connection = try {
            DriverManager.getConnection("jdbc:sqlite:$file")
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        try {
            val statement = connection!!.createStatement()
            val stringBuilder = StringBuilder()
            stringBuilder.append("(")
            for (sqLiteContent in sqLiteContents) {
                contents.add(sqLiteContent.name)
                stringBuilder.append(sqLiteContent.name).append(" VARCHAR(2555) NOT NULL,")
            }
            stringBuilder.append("PRIMARY KEY (").append(primaryKey).append("));")
            statement.execute("CREATE TABLE IF NOT EXISTS $tableName$stringBuilder")
            statement.close()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    fun update(type: String, primaryValue: String, value: String?) {
        try {
            val ps = connection!!.prepareStatement("UPDATE $tableName SET $type = ? WHERE $primaryKey = $primaryValue")
            ps.setString(1, value)
            ps.setString(2, primaryValue)
            ps.executeUpdate()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    fun add(primaryValue: String) {
        if (!contains(primaryValue)) {
            val sb = StringBuilder()
            sb.append("(").append(primaryKey).append(",")
            for (pk in contents) {
                sb.append(pk).append(",")
            }
            if (contents.size > 0) sb.deleteCharAt(sb.length - 1)
            sb.append(")").append("VALUES (")
            val sb2 = StringBuilder()
            for (i in contents.indices) {
                sb2.append("?,")
            }
            if (contents.size > 0) sb2.deleteCharAt(sb2.length - 1)
            sb2.append(")")
            sb.append(sb2)
            try {
                val ps = connection!!.prepareStatement("INSERT OR IGNORE INTO $tableName$sb")
                ps.setString(1, primaryValue)
                for (i in 2 until contents.size + 2) {
                    ps.setString(i, "")
                }
                ps.executeUpdate()
            } catch (e: SQLException) {
                throw RuntimeException(e)
            }
        }
    }

    operator fun contains(primaryValue: String): Boolean {
        try {
            val ps = connection!!.prepareStatement("SELECT * FROM $tableName")
            val rs = ps.executeQuery()
            while (rs.next()) {
                if (rs.getString(primaryKey) == primaryValue) return true
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return false
    }


    class SQLiteContent(var name: String)


}