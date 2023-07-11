package vn.thanhmagics.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.codehaus.plexus.util.FileUtils
import java.io.File
import java.nio.file.Files

abstract class AbstractFileConfig(plugin: Plugin,resource: Boolean) {

    abstract fun getFileName() : String;

    private var config : FileConfiguration;

    private var file : File

    init {
        this.file = File(plugin.dataFolder,getFileName())
        if (!file.exists()) {
            file.createNewFile()
            if (resource) {
                plugin.saveResource(getFileName(),true)
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file)
    }

    fun getConfig() : FileConfiguration {
        return config
    }

    fun getFile() : File  {
        return file
    }

    fun save() {
        config.save(file)
    }

    fun delete() {
        file.delete()
    }

}