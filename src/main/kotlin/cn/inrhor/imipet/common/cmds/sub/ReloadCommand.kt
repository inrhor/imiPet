package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle
import cn.mcres.imiPet.build.utils.Msg
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ReloadCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.RELOAD")
    }

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {
        reloadCmd(sender)
        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }

    private fun reloadCmd(sender: CommandSender) {
        for (player in Bukkit.getOnlinePlayers()) {
            if (info().havePet(player)) {
                val followingPetUUID = info().getFollowingPetUUID(player)
                for (petUUID in followingPetUUID) {
                    info().setPetFollow(player, false, petUUID)
                    FollowSetHandle.runPetRemove(player, petUUID)
                }
            }
        }
        ImiPet.loader.doReload()
        Msg.send(sender, TLocale.asStringList("COMMAND_RELOAD"))
    }
}