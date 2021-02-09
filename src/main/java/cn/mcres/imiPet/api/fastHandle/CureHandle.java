package cn.mcres.imiPet.api.fastHandle;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.instal.lib.VaultLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import io.izzel.taboolib.module.locale.TLocale;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CureHandle {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 通过经济回复宠物血量
     *
     * @param player 玩家
     * @param petUUID 宠物UUID
     * @param modelId 宠物模型ID
     * @param addHP 回复血量值
     */
    public static void cureMoney(Player player, UUID petUUID, String modelId, double addHP, String type) {
        if (VaultLib.VaultLibEnable) {
            if (info().havePet(player)) {
                ModelInfoManager modelInfoManager = ModelInfoManager.petModelList.get(modelId);
                Economy economy = VaultLib.economy;
                // 四舍五入为整数血量
                double petNowHP = Math.round(info().getPetNowHP(player, petUUID, type));
                double petMaxHP = info().getPetMaxHP(player, petUUID, type);
                double moneyYaml = /*GetPetsYaml.getDouble(modelId, "cureHP.requirement.money")*/modelInfoManager.getCureRequirementHPMoney();
                if (!(petMaxHP == petNowHP)) {
                    // 如果回复血量少于或等于宠物失去的血量值
                    if (addHP <= petMaxHP-petNowHP) {
                        // 回复血量所需经济
                        double needMoney = moneyYaml*addHP;
                        double newHP = info().getPetNowHP(player, petUUID, type)+addHP;
                        if (newHP <= info().getPetMaxHP(player, petUUID, type)) {
                            haveMoneyToCure(economy, player, needMoney, newHP, petUUID, addHP, type);
                        }else {
                            newHP = info().getPetMaxHP(player, petUUID, type);
                            haveMoneyToCure(economy, player, needMoney, newHP, petUUID, addHP, type);
                        }
                    }else { // 如果回复血量大于宠物失去的血量值
                        // 回复血量所需经济
                        double addHP2 = petMaxHP-petNowHP;
                        double needMoney = moneyYaml*addHP2;
                        double newHP = info().getPetNowHP(player, petUUID, type)+addHP2;
                        if (newHP <= info().getPetMaxHP(player, petUUID, type)) {
                            haveMoneyToCure(economy, player, needMoney, newHP, petUUID, addHP2, type);
                        }else {
                            newHP = info().getPetMaxHP(player, petUUID, type);
                            haveMoneyToCure(economy, player, needMoney, newHP, petUUID, addHP2, type);
                        }
                    }
                }else {
//                    Msg.send(player, PET_ALREADY_FULL_HP);
                    TLocale.sendTo(player, "PET_ALREADY_FULL_HP");
                }
            }
        }else {
//            Msg.send(player, DISABLE_FUNCTION);
            TLocale.sendTo(player, "DISABLE_FUNCTION");
        }
    }

    private static void haveMoneyToCure(Economy economy, Player player, double needMoney, double newHP, UUID petUUID, double addHP, String type) {
        if (economy.has(player, needMoney)) {
            economy.withdrawPlayer(player, needMoney);
            info().setPetNowHP(player, newHP, petUUID, type);
            /*for (String string : MONEY_ADD_HP) {
                Msg.send(player, string.replaceAll("%imipet_addHP%", String.valueOf(addHP)).replaceAll("%imipet_needMoney%", String.valueOf(needMoney)));
            }*/
            TLocale.sendTo(player, "MONEY_ADD_HP", String.valueOf(addHP), String.valueOf(needMoney));
        }else {
//            Msg.send(player, NOT_ENOUGH_MONEY);
            TLocale.sendTo(player, "NOT_ENOUGH_MONEY");
        }
    }
}
