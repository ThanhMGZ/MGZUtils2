package vn.thanhmagics.craftUtils.craftBuilder

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import vn.thanhmagics.utils.ZUtils

class TextComponentBuilder (message : String){

    private val message : String = message

    private var description : TextComponent? = null

    private var underline : Boolean = false

    private var onClick : ClickEvent? = null

    fun setDescription(string: String) : TextComponentBuilder {
        this.description = TextComponent(ZUtils.applyColor(string))
        return this
    }

    fun setUnderline(boolean: Boolean) : TextComponentBuilder {
        this.underline = boolean
        return this
    }

    fun setClickEvent(clickEvent: ClickEvent) : TextComponentBuilder {
        this.onClick = clickEvent
        return this
    }

    fun build() : TextComponent {
        val text = TextComponent(message);
        if (description != null)
            text.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(description!!))
        if (underline)
            text.isUnderlined = underline
        if (onClick != null)
            text.clickEvent = onClick
        return text
    }

}