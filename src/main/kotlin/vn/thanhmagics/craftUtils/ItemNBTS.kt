package vn.thanhmagics.craftUtils


class ItemNBTS {


    fun addCustomNBT(k: String): ItemNBTS {
        nbts.add(k)
        return this
    }

    companion object {
        private val nbts: MutableList<String> = ArrayList()
        fun getNbts(): MutableList<String> {
            return nbts
        }

    }
}

