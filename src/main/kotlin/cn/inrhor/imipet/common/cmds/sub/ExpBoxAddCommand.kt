package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.build.utils.MathTool
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import java.util.*

class ExpBoxAddCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.EXP_BOX_ADD")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PLAYER"), true),
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.INT"), true)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        val target = Bukkit.getPlayerExact(args[0])
        if (target == null) {
            TLocale.sendTo(sender, "PLAYER_IS_NOT_ONLINE")
            return
        }

        val pUUID: UUID = target.uniqueId
        val getExpString = args[1]

        if (MathTool.isIntNumber(getExpString)) {
            info().addExpBox(pUUID, getExpString.toInt())
        } else {
            TLocale.sendTo(sender, "NOT_INT")
        }

        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}