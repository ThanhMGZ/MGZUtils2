package vn.thanhmagics.craftUtils.craftBuilder

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.Plugin
import vn.thanhmagics.utils.ListenerUtils
import vn.thanhmagics.utils.ZUtils
import java.io.Serializable
import java.lang.RuntimeException
import java.util.UUID

abstract class ChatEditingBuilder2 (
    private val player: Player,
    private val message : MutableList<String>,
    private val plugin: Plugin
) : Serializable {

    companion object {
        private val editor: MutableMap<UUID, ChatEditingBuilder2> = HashMap()

        fun getEditor(): MutableMap<UUID, ChatEditingBuilder2> {
            return editor
        }
    }

    fun start() {
        if (getEditor().containsKey(player.uniqueId)) {
            throw PlayerExistsException("Player Đã Tồn Tại Trong Editor Map!, Vui Lòng Hoàn Thành Tác Vụ Trước Đó Hoặc Reload Plugin!")
        } else {
            player.closeInventory()
            getEditor()[player.uniqueId] = this
            for (mess in message) {
                player.sendMessage(ZUtils.applyColor(mess))
            }
//            object : ListenerUtils_v2<PlayerPacketEvent>() {
//                override fun onEvent(event: PlayerPacketEvent) {
//                    if (event.getPlayer().uniqueId == player.uniqueId && event.getPacket() is ClientboundPlayerChatPacket) {
//                        val packet = event.getPacket() as ClientboundPlayerChatPacket
//                        if (editor.containsKey(player.uniqueId)) {
//                            val ceb = getEditor()[player.uniqueId]!!
//                            getEditor().remove(player.uniqueId)
//                            if (packet.body.content == "cancel") {
//                                onCancel()
//                            } else {
//                                ceb.onComplete(packet.body.content)
//                            }
//                        }
//                    }
//                }
//            }
            object : ListenerUtils<AsyncPlayerChatEvent>(plugin) {
                override fun onEvent(e: AsyncPlayerChatEvent) {
                    if (editor.containsKey(e.player.uniqueId)) {
                        val ceb = editor[e.player.uniqueId]!!
                        editor.remove(e.player.uniqueId)
                        if (e.message.equals("cancel",true)) {
                            ceb.onCancel()
                        } else {
                            ceb.onComplete(e.message)
                        }
                        e.isCancelled = true
                    }
                }
            }
        }
    }

    abstract fun onComplete(message: String)

    abstract fun onCancel()

    class PlayerExistsException(message: String) : RuntimeException(message)
}