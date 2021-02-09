package cn.inrhor.imipet

import cn.inrhor.imipet.utlis.CheckUtil
import cn.inrhor.imipet.utlis.MsgUtil
import cn.inrhor.imipet.utlis.PublicUse
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.model.ModelEntityManager
import cn.mcres.imiPet.api.skill.SkillCastManager
import cn.mcres.imiPet.bstats.Metrics
import cn.mcres.imiPet.build.utils.LogUtil
import cn.mcres.imiPet.entity.RegisterEntity
import cn.mcres.imiPet.instal.Load
import cn.mcres.imiPet.instal.Storage
import cn.mcres.imiPet.instal.YamlLoad
import cn.mcres.imiPet.instal.lib.*
import cn.mcres.imiPet.model.ModelInfoManager
import cn.mcres.imiPet.other.UpdateChecker
import cn.mcres.imiPet.pet.file.NewPetFile
import cn.mcres.imiPet.server.ServerInfo
import cn.mcres.imiPet.skill.cast.SkillManager
import cn.mcres.imiPet.yaml.YamlUpdate
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class PluginLoader {

    private var reloading = false
    private var serverInfo: ServerInfo? = null

    private var info: Info? = null

    // Info
    fun getInfo(): Info? {
        return info
    }

    fun setInfo(info: Info?) {
        this.info = info
    }

    fun getServerInfo(): ServerInfo {
        return serverInfo!!
    }

    fun isReloading(): Boolean {
        return reloading
    }

    private var plugin: JavaPlugin? = null

    fun getPlugin(): JavaPlugin {
        return plugin!!
    }


    fun doReload() {
        if (reloading) {
            throw RuntimeException("ImiPet is reloading..")
        }
        reloading = true
        SkillCastManager.clearSkills()
        ModelInfoManager.clear()
        ModelEntityManager.buildPets.clear()
        YamlLoad().reload()
        NewPetFile.addCustomModel()
        reloading = false
    }


    fun init() {
        plugin = ImiPet.plugin

        serverInfo = ServerInfo(
            Bukkit.getServer().javaClass.getPackage().name.replace(
                ".",
                ","
            ).split(",").toTypedArray()[3], ImiPet.plugin.getServer().getName()
        )
        LogUtil.load(ImiPet)

        Load.run()

        // 加载统计
        // 加载统计
        Metrics(ImiPet.plugin)

        val serverVersion = getServerInfo().serverVersion
        RegisterEntity().run(serverVersion)
    }

    fun active() {
        // 更新检查器
        if (ImiPet.config.getBoolean("update.check.enable")) {
            UpdateChecker(ImiPet.plugin).checkForUpdate()
        }
    }

    fun cancel() {
        // 确保取消调度的所有任务
        // 确保取消调度的所有任务
        Bukkit.getScheduler().cancelTasks(ImiPet.plugin)
        // 卸载模型加载器，删除服务器存在的模型
        // 卸载模型加载器，删除服务器存在的模型
        ModelEntityManager.removeAllModel()
//        ModelManager.removeAllModels();
        //        ModelManager.removeAllModels();
        SkillManager.removeAll()
        if (!reloading) {
            info!!.storeAll() // 保存全部数据
            /*SDEEventHook.removeAll();
            SDEEventHook.shutdown();*/
        }
    }

    private fun logoSend() {
        val logo = listOf(
            "  _                 _   _______         _    " ,
            " (_)               (_) |_   __ \\       / |_  " ,
            " __   _ .--..--.   __    | |__) |.---.`| |-' " ,
            "[  | [ `.-. .-. | [  |   |  ___// /__\\\\| |   " ,
            " | |  | | | | | |  | |  _| |_   | \\__.,| |,  " ,
            "[___][___||__||__][___]|_____|   '.__.'\\__/  ")
        for (s in logo) {
            MsgUtil.send(s)
        }
    }

    fun updateYaml() {
        val langName = "lang/${ImiPet.config.getString("setting.lang")}.yml"
        val langFile = File(plugin?.dataFolder, langName)
        YamlUpdate.update(plugin, langName, langFile, emptyList())
    }

    fun loadInfo() {
        val pluginCon = ImiPet.plugin.description

        logoSend()

        val lang = ImiPet.config.getString("setting.lang")

        TLocale.sendToConsole("LOADER.INFO",
            PublicUse.tag,
            pluginCon.name,
            pluginCon.version,
            lang,
            Storage.storage)

        TLocale.sendToConsole(
            "LOADER.PLUGIN.PlaceholderAPI",
            CheckUtil.getPluginState(PapiLib.papiLibEnable))
        TLocale.sendToConsole(
            "LOADER.PLUGIN.Vault",
            CheckUtil.getPluginState(VaultLib.VaultLibEnable))
        TLocale.sendToConsole(
            "LOADER.PLUGIN.HolographicDisplays",
            CheckUtil.getPluginState(HDLib.HDLibEnable))
        TLocale.sendToConsole(
            "LOADER.PLUGIN.TrHologram",
            CheckUtil.getPluginState(TrHDLib.TrHDLibEnable))
        TLocale.sendToConsole(
            "LOADER.PLUGIN.CMI",
            CheckUtil.getPluginState(CMILib.CMILibEnable))
        TLocale.sendToConsole(
            "LOADER.PLUGIN.AttributePlus",
            CheckUtil.getPluginState(APLib.APLibEnable))
        TLocale.sendToConsole(
            "LOADER.PLUGIN.MythicMobs",
            CheckUtil.getPluginState(MMLib.MMLibEnable))
        TLocale.sendToConsole(
            "LOADER.PLUGIN.ModelEngine",
            CheckUtil.getPluginState(ModelLib.ModelLibEnable))
        TLocale.sendToConsole(
            "LOADER.PLUGIN.VexView",
            CheckUtil.getPluginState(VexLib.VexLibEnable))
    }
}