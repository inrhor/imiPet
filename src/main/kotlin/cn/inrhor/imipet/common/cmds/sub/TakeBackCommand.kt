package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle
import cn.mcres.imiPet.other.MapAll
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.scheduler.BukkitRunnable

class TakeBackCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.TAKE_BACK")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PLAYER"), true)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        if (args.isEmpty()) return

        val target = Bukkit.getPlayerExact(args[0])

        if (target == null) {
            TLocale.sendTo(sender, "PLAYER_IS_NOT_ONLINE")
            return
        }

        if (!info().havePet(target)) return
        for (targetPetUUID in info().getFollowingPetUUID(target)) {
            info().setPetFollow(target, false, targetPetUUID)
            FollowSetHandle.runPetRemove(target, targetPetUUID)
            if (!ImiPet.config.getBoolean("takeBack.enable")) return
            MapAll.takeBackPet[target] = targetPetUUID
            object : BukkitRunnable() {
                override fun run() {
                    if (target.isOnline) {
                        FollowSetHandle.runInfo(target, targetPetUUID)
                    }
                }
            }.runTaskLater(ImiPet.plugin, ImiPet.config.getInt("takeBack.time") * 20.toLong())
        }

        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}