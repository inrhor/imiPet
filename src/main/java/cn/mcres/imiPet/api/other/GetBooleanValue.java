package cn.mcres.imiPet.api.other;

import cn.inrhor.imipet.ImiPet;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class GetBooleanValue {
    /**
     * @param player 玩家
     * @return 是否在禁用宠物的世界
     */
    public static boolean inDisablePetWorld(Player player) {
        World world = player.getWorld();
        List<String> disWorldList = ImiPet.config.getStringList("disablePetWorld");
        if (!disWorldList.isEmpty()) {
            for (String disWorld : disWorldList) {
                if (world.getName().equals(disWorld)) {
                    return true;
                }
            }
        }
        return false;
    }
}
