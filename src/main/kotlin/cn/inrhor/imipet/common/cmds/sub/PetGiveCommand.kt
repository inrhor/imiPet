package cn.inrhor.imipet.common.cmds.sub

import cn.mcres.imiPet.api.other.PetUtils
import cn.mcres.imiPet.model.ModelInfoManager
import cn.mcres.imiPet.pet.file.PetFile
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PetGiveCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.PET_GIVE")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PLAYER"), true),
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.MODEL_ID"), true)  { ModelInfoManager.petModelList.map { it.key } }
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        if (args.size != 2) return

        val modelId = args[1]

        if (!hasModelId(modelId)) {
            TLocale.sendTo(sender, "MODELID_DOES_NOT_EXIST")
            return
        }

        val target = Bukkit.getPlayerExact(args[0])

        if (target == null) {
            TLocale.sendTo(sender, "PLAYER_IS_NOT_ONLINE")
            return
        }

        val modelInfoManager = ModelInfoManager.petModelList[modelId]
        val name = modelInfoManager!!.petDefaultName
        PetUtils.sendPlayerNewPet(target, modelInfoManager)
        if (sender is Player && sender == target) {
            TLocale.sendTo(sender, "GET_A_PET", name)
        } else {
            TLocale.sendTo(target, "GET_A_PET", name)
            TLocale.sendTo(sender, "SUCCESSFULLY_GIVE_PET", target.name)
        }

        return
    }

    private fun hasModelId(modelId: String): Boolean {
        return PetFile.modelIdList.contains(modelId)
    }
}