package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.build.utils.Msg
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class ExpBoxLookCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.EXP_BOX_LOOK")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PLAYER"), false)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        if (args.isEmpty()) {
            if (sender !is Player) return
            if (!sender.hasPermission("imipet.player.use")) {
                TLocale.sendTo(sender, "COMMAND_NO_PERMISSION")
                return
            }
            /*for (text in TLocale.asStringList("COMMAND_EXPBOX_LOOK")) {
                Msg.send(
                    sender,
                    text.replace("%imipet_expbox%".toRegex(), info().getExpBox(sender.uniqueId).toString())
                )
            }*/
            TLocale.sendTo(sender, "COMMAND_EXPBOX_LOOK", info().getExpBox(sender.uniqueId).toString())
            return
        }

        if (args.isNotEmpty()) {
            if (!sender.hasPermission("imipet.admin.expbox.look")) return
            val target = Bukkit.getPlayerExact(args[0])
            if (target == null) {
                TLocale.sendTo(sender, "PLAYER_IS_NOT_ONLINE")
                return
            }
            val pUUID: UUID = target.uniqueId
            /*for (text in TLocale.asStringList("COMMAND_EXPBOX_LOOK_OP")) {
                Msg.send(
                    sender,
                    text.replace("%imipet_expbox%".toRegex(), info().getExpBox(pUUID).toString())
                )
            }*/
            TLocale.sendTo(sender, "COMMAND_EXPBOX_LOOK_OP", info().getExpBox(pUUID).toString())
        }

        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}