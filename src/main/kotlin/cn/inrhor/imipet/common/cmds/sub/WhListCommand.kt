package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.build.utils.Msg
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WhListCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.WAREHOUSE_LIST")
    }

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {
        if (sender !is Player) return

        if (!info().havePet(sender)) {
            TLocale.sendTo(sender, "PET_IS_EMPTY")
            return
        }

        TLocale.sendTo(sender, "COMMAND_WAREHOUSE_LIST")
        for (uniqueId in info().getPetsWarehouseList(sender.uniqueId)) {
            Msg.send(sender, "&f  - " + info().getPetName(sender, uniqueId, "warehouse"))
        }

        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}