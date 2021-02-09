package cn.mcres.imiPet.api.data;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SaveDelPet {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 删除玩家的宠物
     * @param playerUUID 玩家
     * @param petUUID 宠物
     */
    public static void removePet(UUID playerUUID, UUID petUUID, String type) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (type.equals("pets")) {
            if (player != null) {
                if (info().havePet(player) && info().getPetFollow(player, petUUID)) {
                    FollowSetHandle.runPetRemove(player, petUUID);
                }
            }
        }
        info().removePet(playerUUID, petUUID, type);
    }
}
