package cn.inrhor.imipet.common.cmds

import cn.inrhor.imipet.common.cmds.sub.*
import cn.inrhor.imipet.common.cmds.sub.other.LotteryCommand
import cn.inrhor.imipet.common.cmds.sub.skill.SkillGiveCommand
import cn.inrhor.imipet.common.cmds.sub.skill.SkillOpenCommand
import io.izzel.taboolib.module.command.base.*

@BaseCommand(name = "imiPet", permission = "imipet.view")
class Command : BaseMainCommand() {
    @SubCommand(permission = "imipet.reload")
    val reload : BaseSubCommand = ReloadCommand()

    @SubCommand(permission = "imipet.admin.pet.give")
    val petGive : BaseSubCommand = PetGiveCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val open : BaseSubCommand = OpenCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val openSkill : BaseSubCommand = SkillOpenCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val packList : BaseSubCommand = PackListCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val whList : BaseSubCommand = WhListCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val follow : BaseSubCommand = FollowCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val ride : BaseSubCommand = RideCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val cure : BaseSubCommand = CureCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val food : BaseSubCommand = FoodCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val evolution : BaseSubCommand = EvolutionCommand()

    @SubCommand()
    val expBoxLook : BaseSubCommand = ExpBoxLookCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val rename : BaseSubCommand = RenameCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val into : BaseSubCommand = IntoCommand()

    @SubCommand(permission = "imipet.player.use", type = CommandType.PLAYER)
    val transfer : BaseSubCommand = TransferCommand()

    @SubCommand
    val release : BaseSubCommand = ReleaseCommand()

    @SubCommand(permission = "imipet.admin.takeback")
    val takeBack : BaseSubCommand = TakeBackCommand()

    @SubCommand(permission = "imipet.admin.expbox.set")
    val expBoxSet : BaseSubCommand = ExpBoxSetCommand()

    @SubCommand(permission = "imipet.admin.expbox.give")
    val expBoxGive: BaseSubCommand = ExpBoxAddCommand()

    @SubCommand(permission = "imipet.admin.expbox.del")
    val expBoxDel : BaseSubCommand = ExpBoxDelCommand()

    @SubCommand(permission = "imipet.lottery")
    val lottery : BaseSubCommand = LotteryCommand()

    @SubCommand(permission = "imipet.admin.skillgive")
    val skillGive : BaseSubCommand = SkillGiveCommand()

}