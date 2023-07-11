package vn.thanhmagics.utils

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.*


class ObjectArrayUtils {

    companion object {

        fun <O : Serializable> objToString(obj : O) : String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objOutputStream.writeObject(obj)
            objOutputStream.close()
            return Base64Coder.encodeLines(byteArrayOutputStream.toByteArray())
        }

        fun <O> toObj(data : String) : O {
            val byteArrayInputStream = ByteArrayInputStream(Base64Coder.decodeLines(data))
            val objInput = ObjectInputStream(byteArrayInputStream)
            val obj = objInput.readObject()
            objInput.close()
            return obj as O
        }

        fun <O : Serializable> objsToString(data: ArrayList<O>): String {
            val outputStream = ByteArrayOutputStream()
            val dataOutput = ObjectOutputStream(outputStream)
            dataOutput.writeInt(data.size)
            for (dt in data) {
                dataOutput.writeObject(dt)
            }
            dataOutput.close()
            return Base64Coder.encodeLines(outputStream.toByteArray())
        }

        fun <O> toObjs(data : String) : ArrayList<O> {
            val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(data))
            val dataInput = ObjectInputStream(inputStream)
            val items = ArrayList<O>(dataInput.readInt())
            for (i in 0 until items.size) {
                items[i] = dataInput.readObject() as O
            }
            dataInput.close()
            return items
        }

    }

}