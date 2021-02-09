package cn.inrhor.imipet.common.cmds.sub


import cn.inrhor.imipet.ImiPet
import cn.mcres.imiPet.api.data.Info
import cn.mcres.imiPet.api.data.SaveNewPet
import cn.mcres.imiPet.build.utils.MathTool
import cn.mcres.imiPet.instal.lib.APLib
import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class IntoCommand : BaseSubCommand() {

    override fun getDescription(): String {
        return TLocale.asString("COMMAND_DESCRIPTION.INTO")
    }

    override fun getArguments() = arrayOf(
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.WAREHOUSE_SLOT"), true),
        Argument(TLocale.asString("COMMAND_DESCRIPTION.VAR.PACK_SLOT"), false)
    )

    override fun onCommand(sender: CommandSender, command: Command, label : String, args: Array<out String>) {

        if (sender !is Player) return

        val playerUUID = sender.uniqueId
        val warehouseList = info().getPetsWarehouseList(playerUUID)
        val whSlot = args[0]
        if (!MathTool.isIntNumber(whSlot)) {
            TLocale.sendTo(sender, "NOT_INT")
            return
        }
        val index = whSlot.toInt()
        if (!(index > 0 && warehouseList.size >= index)) {
            TLocale.sendTo(sender, "PET_DOES_NOT_EXIST")
            return
        }

        val listIndex = whSlot.toInt() - 1
        val petWhUUID = warehouseList[listIndex]
        val apLibEnable = APLib.APLibEnable

        if (args.size < 2) {
            SaveNewPet.TransferPackWarehouse(
                sender,
                petWhUUID,
                "warehouse",
                apLibEnable,
                apLibEnable)
            TLocale.sendTo(sender, "SUCCESSFULLY_PUT_IN_PACK")
            return
        }

        if (args.size >= 2) {
            val packSlot = args[1]
            if (!MathTool.isIntNumber(packSlot)) {
                TLocale.sendTo(sender, "NOT_INT")
                return
            }
            val indexPack = packSlot.toInt()
            val packList = info().getPetsPackList(playerUUID)
            if (!(indexPack > 0 && packList.size >= indexPack)) {
                TLocale.sendTo(sender, "PET_DOES_NOT_EXIST")
                return
            }
            val packListIndex = packSlot.toInt() - 1
            val packPetUUID = packList[packListIndex]
            SaveNewPet.TransferPackWarehouse(
                sender,
                petWhUUID,
                packPetUUID,
                "warehouse",
                apLibEnable,
                apLibEnable)
            TLocale.sendTo(sender, "SUCCESSFULLY_PUT_IN_PACK")
        }

        return
    }

    private fun info(): Info {
        return ImiPet.loader.getInfo()!!
    }
}