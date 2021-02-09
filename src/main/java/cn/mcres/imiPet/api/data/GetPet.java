package cn.mcres.imiPet.api.data;

import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.entity.Player;

public class GetPet {
    /**
     * @deprecated
     *
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
}
