package cn.inrhor.imipet.utlis

import io.izzel.taboolib.module.locale.TLocale

object CheckUtil {
    fun getPluginState(state : Boolean): String {
        if (state) {
            return TLocale.asString("LOADER.PLUGIN.ENABLE")
        }
        return TLocale.asString("LOADER.PLUGIN.DISABLE")
    }
}