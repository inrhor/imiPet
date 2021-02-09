package cn.mcres.imiPet.api.fastHandle;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.instal.lib.VaultLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import io.izzel.taboolib.module.locale.TLocale;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class EvolutionHandle {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 触发宠物进化方法
     *
     * @param player 玩家
     * @param petUUID 宠物
     */
    public static void run(Player player, UUID petUUID, String type) {
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, "pets"));
        if (/*!GetPetsYaml.getBoolean(info().getPetModelId(player, petUUID, "pets"), "evolution.enable")*/!infoManager.isEvolutionEnable()) {
//            Msg.send(player, CAN_NOT_EVOLVED_SEND);
            TLocale.sendTo(player, "CAN_NOT_EVOLVED_SEND");
            return;
        }
        ModelInfoManager infoManager1 = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, type));
        if (/*GetPetsYaml.getInt(info().getPetModelId(player, petUUID, type), "evolution.requirement.level")*/
                infoManager1.getEvolutionRequirementLevel() > info().getPetLevel(player, petUUID, type)) {
            TLocale.sendTo(player, "EVOLUTION_NOT_ENOUGH_LEVEL");
//            Msg.send(player, EVOLUTION_NOT_ENOUGH_LEVEL);
            return;
        }
        double needMoney = infoManager1.getEvolutionRequirementMoney();
        if (needMoney > 0) {
            if (VaultLib.VaultLibEnable) {
                Economy economy = VaultLib.economy;
                if (!economy.has(player, needMoney)) {
//                    Msg.send(player, NOT_ENOUGH_MONEY);
                    TLocale.sendTo(player, "NOT_ENOUGH_MONEY");
                    return;
                }
                economy.withdrawPlayer(player, needMoney);
            }
        }
        info().setPetFollow(player, false, petUUID);
        FollowSetHandle.runPetRemove(player, petUUID);
        info().setPetModelId(player, Objects.requireNonNull(infoManager1.getEvolutionNewModelId()), petUUID, type);
//        Msg.send(player, PET_HAVE_EVOLVED);
        TLocale.sendTo(player, "PET_HAVE_EVOLVED");
    }
}
