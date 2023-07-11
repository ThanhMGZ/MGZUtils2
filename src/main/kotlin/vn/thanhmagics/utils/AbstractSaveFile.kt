package vn.thanhmagics.utils

import vn.thanhmagics.craftUtils.Storage

abstract class AbstractSaveFile {

    abstract fun storage() : Storage

    abstract fun getStorageFile() : AbstractFileConfig

    abstract fun storageRow() : String



}