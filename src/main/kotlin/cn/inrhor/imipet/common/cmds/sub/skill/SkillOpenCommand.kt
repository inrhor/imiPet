package cn.inrhor.imipet.common.cmds.sub.skill

import cn.mcres.imiPet.gui.vanilla.castSkill.GuiCastSkill
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SkillOpenCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.SKILL_OPEN")
    }

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        if (sender !is Player) return
        GuiCastSkill.open(sender)

        return
    }
}