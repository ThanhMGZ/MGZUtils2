package vn.thanhmagics.utils

import org.bukkit.plugin.Plugin
import java.io.File
import java.lang.StringBuilder
import java.sql.Connection
import java.sql.DriverManager

 class SQLite(tableName: String, plugin: Plugin) {

    private val name : String = tableName
    private val file : File = TODO()
    private var contents : MutableList<String>
    private var primaryKey : String
    private var connection : Connection = TODO()
    private var utils : SQLiteUtils

    init {
        this.file = File(plugin.dataFolder,"database.db")
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    fun create(primaryKey: String, vararg contents: String,utils : SQLiteUtils) {
        this.utils = utils
        this.primaryKey = primaryKey
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file)
        val s = this.connection.createStatement()
        val sb = StringBuilder()
        sb.append("(")
        for (k in contents) {
            this.contents.add(k)
            sb.append(k).append(" VARCHAR(2555) NOT NULL,")
        }
        sb.append("PRIMARY KEY ($primaryKey));")
        s.executeUpdate("CREATE TABLE IF NOT EXISTS " + this.name + sb.toString());
        s.close()
    }

    fun getUtils() : SQLiteUtils {
        return utils;
    }

    fun getContents() : MutableList<String> {
        return contents;
    }

    fun getPrimaryKey() : String {
        return this.primaryKey
    }

    fun getTableName() : String {
        return this.name
    }

    fun getConnection() : Connection {
        return this.connection
    }


    fun close() {
        if (!this.connection.isClosed)
            this.connection.close()
    }


}