package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.vexgui.VexHomeCore
import cn.mcres.imiPet.gui.vanilla.home.GuiHome
import cn.mcres.imiPet.instal.lib.VexLib
import cn.mcres.imiPet.other.MapAll
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class OpenCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.OPEN")
    }

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        if (sender !is Player) return

        val playerUUID = sender.uniqueId
        val openType = ImiPet.config.getString("gui.open")
        if (openType == "vexview") {
            if (VexLib.VexLibEnable) {
                val i: Int
                val follows: Set<UUID> = info().getFollowingPetUUID(playerUUID)
                if (follows.isNotEmpty()) {
                    val first = follows.iterator().next()
                    i = info().getPetsPackList(playerUUID).indexOf(first)
                    MapAll.guiHomeSelectPet[sender] = i
                }
                /*val first = follows.iterator().next()
                i = info().getPetsPackList(playerUUID).indexOf(first)*/
                MapAll.guiSelectPet.remove(sender)
                MapAll.vgHomePagePlayer[sender] = 1
                VexHomeCore(sender).addCompToVg()
            }
        } else if (openType == "vanilla") {
            GuiHome.open(sender)
        }

        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}