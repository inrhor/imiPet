package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.fastHandle.RideHandle
import cn.mcres.imiPet.build.utils.MathTool
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RideCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.RIDE")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PACK_SLOT"), false)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        if (sender !is Player) return

        if (!info().havePet(sender)) {
            TLocale.sendTo(sender, "PET_IS_EMPTY")
        }

        if (!args.isEmpty()) {
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

            RideHandle.ridePet(sender, petUUID)
        }else {
            for (follow in info().getFollowingPetUUID(sender)) {
                RideHandle.ridePet(sender, follow)
                break
            }
        }


        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}