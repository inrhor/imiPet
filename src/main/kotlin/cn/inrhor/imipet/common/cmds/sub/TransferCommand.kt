package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.data.SaveNewPet
import cn.mcres.imiPet.build.utils.MathTool
import cn.mcres.imiPet.instal.lib.APLib
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TransferCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.TRANSFER")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PACK_SLOT"), true),
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PLAYER"), true)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        if (sender !is Player) return

        if (!info().havePet(sender)) {
            TLocale.sendTo(sender, "PET_IS_EMPTY")
        }

        val slot = args[0]
        if (!MathTool.isIntNumber(args[0])) {
            TLocale.sendTo(sender, "NOT_INT")
            return
        }

        val list = info().getPetsPackList(sender.uniqueId)

        val index = slot.toInt()
        if (!(index > 0 && list.size >= index)) {
            TLocale.sendTo(sender, "PET_DOES_NOT_EXIST")
            return
        }

        val listIndex = slot.toInt() - 1
        val petUUID = list[listIndex]
        val target = Bukkit.getPlayerExact(args[1])
        if (target == null ) {
            TLocale.sendTo(sender, "PLAYER_IS_NOT_ONLINE")
            return
        }

        val apLibEnable = APLib.APLibEnable
        SaveNewPet.TransferPet(sender, target, petUUID, apLibEnable, apLibEnable)

        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}