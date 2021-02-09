package cn.mcres.imiPet.model;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.pet.BuildPet;
import cn.mcres.imiPet.server.ServerInfo;
import org.bukkit.entity.ArmorStand;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class ASI {
    public static JavaPlugin getPlugin() {
        return ImiPet.loader.getPlugin();
    }

    public static ServerInfo getServerInfo() {
        return ImiPet.loader.getServerInfo();
    }

    public static ArmorStand iniArmorStand(ArmorStand a, BuildPet buildPet) {
        setArmorStand(a);
        a.setMetadata("imipet.uuid", new FixedMetadataValue(getPlugin(), buildPet.getPetUUID()));
        a.setMetadata("imipet:playerUUID", new FixedMetadataValue(getPlugin(), buildPet.getPlayer().getUniqueId()));
        a.setMetadata("imiPet", new FixedMetadataValue(getPlugin(), true));
        a.setMetadata("imipet:modelId", new FixedMetadataValue(getPlugin(), buildPet.getModelId()));
        a.setCustomNameVisible(false);
        return a;
    }

    private static void setArmorStand(ArmorStand a) {
        if (!getServerInfo().isOldVersionMethod()) {
            a.setInvulnerable(true);
            a.setSilent(true);
            a.setAI(false);
        }
        a.setArms(true);
        a.setBasePlate(false);
        a.setGravity(false);
        a.setVisible(false);
        a.setSmall(true);
    }

    public static ArmorStand iniArmorStand(ArmorStand a, String modelId) {
        setArmorStand(a);
        a.setMetadata("imipet:modelId", new FixedMetadataValue(getPlugin(), modelId));
        a.setCustomNameVisible(false);
        return a;
    }
}
