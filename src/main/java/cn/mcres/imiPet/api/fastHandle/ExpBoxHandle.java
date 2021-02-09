package cn.mcres.imiPet.api.fastHandle;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.events.PetLevelChangeEvent;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.model.ModelInfoManager;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;



public class ExpBoxHandle {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * @param player  玩家
     * @param giveExp 给予指定经验值
     *                <p>
     *                处理经验及升级
     */
    public static void givePet(Player player, UUID petUUID, int giveExp) {
        UUID playerUUID = player.getUniqueId();
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, "pets"));
        if (info().getPetLevel(player, petUUID, "pets") < infoManager.getMaxLevel()) {
            // 积累给定后的经验
            int addExpAfter = info().getPetNowExp(player, petUUID, "pets") + giveExp;
            int boxExp = (info().getExpBox(playerUUID));
            // 经验存储盒有足够的经验
            if (!(boxExp >= giveExp)) {
//                Msg.send(player, EXPBOX_NOT_ENOUGH_EXP);
                TLocale.sendTo(player, "EXPBOX_NOT_ENOUGH_EXP");
                return;
            }
            // 并防止给定的经验值超过数值
            if (addExpAfter < info().getPetMaxExp(player, petUUID, "pets")) {
                info().setPetNowExp(player, addExpAfter, petUUID, "pets");
                info().delExpBox(playerUUID, giveExp);
                /*for (String msg : GIVE_EXP_TO_PET) {
                    Msg.send(player, msg.replaceAll("%imipet_give_exp%", String.valueOf(giveExp))
                            .replaceAll("%imipet_nowexp%", String.valueOf(info().getPetNowExp(player, petUUID, "pets")))
                            .replaceAll("%imipet_maxexp%", String.valueOf(info().getPetMaxExp(player, petUUID, "pets"))));
                }*/
                TLocale.sendTo(player, "GIVE_EXP_TO_PET",
                        String.valueOf(giveExp),
                        String.valueOf(info().getPetNowExp(player, petUUID, "pets")),
                        String.valueOf(info().getPetMaxExp(player, petUUID, "pets")));
            } else if (!(addExpAfter < info().getPetMaxExp(player, petUUID, "pets"))) {
                // 仅给予达到上限经验的值
                int giveExp2 = info().getPetMaxExp(player, petUUID, "pets") - info().getPetNowExp(player, petUUID, "pets");
                // 获得宠物当前等级
                int petLevel = info().getPetLevel(player, petUUID, "pets");
                info().setPetNowExp(player, 0, petUUID, "pets");
                info().delExpBox(playerUUID, giveExp2);
                // 升级
                int newLevel = petLevel + 1;
                info().setPetLevel(player, newLevel, petUUID, "pets");
                Bukkit.getScheduler().runTask(ImiPet.loader.getPlugin(), () -> {
                    Bukkit.getPluginManager().callEvent(new PetLevelChangeEvent(player, petLevel, newLevel, petUUID));
                });
                /*Bukkit.getScheduler().runTask(ImiPet.loader.getPlugin(), () -> {
                });*/
            } else {
//                Msg.send(player, PET_LEVEL_IS_FULL);
                TLocale.sendTo(player, "PET_LEVEL_IS_FULL");
            }
        } else {
//            Msg.send(player, PET_LEVEL_IS_FULL);
            TLocale.sendTo(player, "PET_LEVEL_IS_FULL");
        }
    }
}
