package vn.thanhmagics.utils

import java.sql.PreparedStatement

abstract class SQLiteUtils(sqlite : SQLite) {

    private val sqlite = sqlite

    abstract fun init()

    abstract fun save()

    abstract fun get(primary: String, valueType: String) : String;

    abstract fun set(primary: String, valueType: String, value: String);

    fun updateValue(sql : String,primary : String,value : String) {
        val ps = sqlite.getConnection().prepareStatement(sql)
        ps.setString(1,primary)
        ps.setString(2,value)
        ps.executeUpdate()
    }

    fun getPrepareStatement(sql : String) : PreparedStatement {
        return sqlite.getConnection().prepareStatement(sql)
    }

    fun getSQLite() : SQLite {
        return sqlite
    }

}