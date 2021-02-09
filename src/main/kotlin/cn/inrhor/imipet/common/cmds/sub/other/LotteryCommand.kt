package cn.inrhor.imipet.common.cmds.sub.other


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.other.PetUtils
import cn.mcres.imiPet.build.utils.MathTool
import cn.mcres.imiPet.model.ModelInfoManager
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import java.util.concurrent.ThreadLocalRandom

class LotteryCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.LOTTERY")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PLAYER"), true),
        Argument("Model ID", true),
        Argument("Chance", true)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        // imipet lottery player modelId 0-1
        val player = Bukkit.getPlayer(args[0])
        if (player == null) {
            TLocale.sendTo(sender, "PLAYER_IS_NOT_ONLINE")
            return
        }

        val modelId = args[1]
        if (!ModelInfoManager.petModelList.containsKey(modelId)) {
            TLocale.sendTo(sender, "MODELID_DOES_NOT_EXIST")
            return
        }

        val chanceCmd = args[2]
        if (!MathTool.isNumber(chanceCmd)) {
            TLocale.sendTo(sender, "NOT_IS_NUMBER_CHANCE")
            return
        }
        val chance= chanceCmd.toDouble()
        if (ThreadLocalRandom.current().nextDouble() >= chance) {
            TLocale.sendTo(player, "LOTTERY_UNSUCCESSFUL_PET")
            return
        }
        val modelInfoManager = ModelInfoManager.petModelList[modelId]
        val name = modelInfoManager!!.petDefaultName
        PetUtils.sendPlayerNewPet(player, modelInfoManager)
        TLocale.sendTo(player, "LOTTERY_WINNING_PET", name)

        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}