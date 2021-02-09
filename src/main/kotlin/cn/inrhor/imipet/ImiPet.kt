package cn.inrhor.imipet

import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject

object ImiPet : Plugin() {
    @TInject(locale = "setting.lang")
    lateinit var config : TConfig

    @TInject(state = TInject.State.STARTING, init = "init", active = "active", cancel = "cancel")
    lateinit var loader : PluginLoader
}