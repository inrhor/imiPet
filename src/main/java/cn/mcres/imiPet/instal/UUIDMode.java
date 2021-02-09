package cn.mcres.imiPet.instal;

import org.bukkit.entity.Player;

public class UUIDMode {
    protected static boolean uuid = true;

    public static boolean isUUIDMode() {
        return uuid;
    }

    public static String uid(Player player) {
        if (uuid) {
            return player.getUniqueId().toString();
        }
        return player.getName();
    }
}
