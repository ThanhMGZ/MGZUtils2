package vn.thanhmagics.utils

import vn.thanhmagics.craftUtils.SaveFile
import vn.thanhmagics.craftUtils.Storage

abstract class AbstractSaveFile {

    abstract fun storage() : Storage

    abstract fun getStorageFile() : AbstractFileConfig

    abstract fun saveFile() : SaveFile<*>

    private val saveFile : SaveFile<*> = saveFile()

    abstract fun storageRow() : String



}