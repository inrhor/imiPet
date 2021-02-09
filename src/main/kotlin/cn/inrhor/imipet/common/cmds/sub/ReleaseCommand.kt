package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.data.SaveDelPet
import cn.mcres.imiPet.build.utils.MathTool
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ReleaseCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.RELEASE")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PACK_SLOT"), true),
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PLAYER"), false)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

            if (args.size == 1) {

                if (sender !is Player) return

                if (!info().havePet(sender)) {
                    TLocale.sendTo(sender, "PET_IS_EMPTY")
                    return
                }

                if (!sender.hasPermission("imipet.player.use")) {
                    TLocale.sendTo(sender, "PET_IS_EMPTY")
                    return
                }

                commandRelease(sender, args, sender)
            }else if (args.size >= 2) {

                val p = Bukkit.getPlayerExact(args[1])
                if (p == null) {
                    TLocale.sendTo(sender, "PLAYER_IS_NOT_ONLINE")
                    return
                }

                commandRelease(sender, args, p)
            }

        return
    }

    private fun commandRelease(sender: CommandSender, args: Array<out String>, target: Player) {
        val slot = args[0]
        if (!MathTool.isIntNumber(slot)) {
            TLocale.sendTo(sender, "NOT_INT")
            return
        }

        val playerUUID = target.uniqueId

        val list = info().getPetsPackList(playerUUID)

        val index = slot.toInt()
        if (!(index > 0 && list.size >= index)) {
            TLocale.sendTo(sender, "PET_DOES_NOT_EXIST")
            return
        }

        val listIndex = slot.toInt() - 1
        val petUUID = list[listIndex]
        SaveDelPet.removePet(playerUUID, petUUID, "pets")
        TLocale.sendTo(sender, "SUCCESSFUL_RELEASE_PET")
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}