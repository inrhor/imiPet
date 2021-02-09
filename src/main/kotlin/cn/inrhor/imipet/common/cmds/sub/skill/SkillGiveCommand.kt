package cn.inrhor.imipet.common.cmds.sub.skill


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.other.PetUtils
import cn.mcres.imiPet.api.skill.SkillCastManager
import cn.mcres.imiPet.pet.BuildPet
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class SkillGiveCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.RELOAD")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PLAYER"), true),
        Argument("Skill ID", true)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        val target = Bukkit.getPlayerExact(args[0])
        if (target == null) {
            TLocale.sendTo(sender, "PLAYER_IS_NOT_ONLINE")
            return
        }

        if (PetUtils.getFollowPet(target) == null) {
            TLocale.sendTo(sender, "PLAYER_NOT_HAVE_FOLLOWING_PET")
            return
        }

        val skillID = args[1]

        val buildPet: BuildPet = PetUtils.getFollowPet(target)
        if (!SkillCastManager.petExistSkillID(buildPet, skillID)) {
            TLocale.sendTo(sender, "PLAYER_NOT_HAVE_FOLLOWING_PET")
            return
        }
        val targetUUID = target.uniqueId
        val type = "pets"
        val unSkillList =
            info().getPetSkillsUn(targetUUID, buildPet.petUUID, type)
        unSkillList.add(skillID)
        info().setPetSkillsUn(target, unSkillList, buildPet.petUUID, type)
        val mem = buildPet.modelEntityManager
        TLocale.sendTo(sender, "PLAYER_SUCCESSFUL_ADD_UNSKILL",
            target.name,
            buildPet.petName,
            mem.getAnimationModel(skillID).skillName
        )
        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}