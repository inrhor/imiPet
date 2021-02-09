package cn.mcres.imiPet.api.other;

import cn.mcres.imiPet.api.data.SaveNewPet;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PetUtils {
    /**
     * 是否是该宠物的主人
     * @param player
     * @param entity
     * @return
     */
    public static boolean isOwner(Player player, Entity entity) {
        UUID playerUUID = player.getUniqueId();
        String s = "imipet:playerUUID";
        if (!entity.hasMetadata(s)) return false;
        UUID metadataPlayerUUID = UUID.fromString(entity.getMetadata(s).get(0).asString());
        return playerUUID.equals(metadataPlayerUUID);
    }

    /**
     * @param player 玩家
     * @return 返回跟随玩家的宠物
     */
    public static BuildPet getFollowPet(Player player) {
        for (BuildPet pets : ModelEntityManager.buildPets) {
            if (pets.getPlayer().equals(player)) {
                if (!pets.getPet().isDead()) {
                    return pets;
                }
            }
        }
        return null;
    }

    /**
     * @param playerUUID 玩家
     * @return 返回跟随玩家的宠物
     */
    public static BuildPet getFollowPet(UUID playerUUID) {
        for (BuildPet pets : ModelEntityManager.buildPets) {
            if (pets.getPlayer().getUniqueId().equals(playerUUID)) {
                if (!pets.getPet().isDead()) {
                    return pets;
                }
            }
        }
        return null;
    }

    /**
     * 直接给予默认宠物
     * @param player
     * @param modelInfoManager
     */
    public static void sendPlayerNewPet(Player player, ModelInfoManager modelInfoManager) {
        String name = modelInfoManager.getPetDefaultName();
        int HP = modelInfoManager.getHP();
        double minDamage = modelInfoManager.getMinDamage();
        double maxDamage = modelInfoManager.getMaxDamage();
        int exp = modelInfoManager.getExp();
        int food = modelInfoManager.getFood();
        boolean apLibEnable = APLib.APLibEnable;
        SaveNewPet.createNewPet(player, modelInfoManager.getModelId(), name,
                HP, HP,
                minDamage, maxDamage,
                1,
                exp, 0,
                food, food,
                apLibEnable, modelInfoManager.getApBasisList(),
                apLibEnable, modelInfoManager.getBuffAttributeAP());
    }
}
