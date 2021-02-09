package cn.mcres.imiPet.task;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SaveData {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 即时保存宠物血量
     */
    public static void run() {
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                for (BuildPet pets : ModelEntityManager.buildPets) {
                    if (pets.getPet() != null && pets.getPlayer() != null) {
                        Player e = pets.getPlayer();
                        UUID petUUID = pets.getPetUUID();
                        double maxHP = pets.getPet().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                        info().setPetMaxHP(e, maxHP, petUUID, "pets");
                        info().setPetNowHP(e, pets.getPet().getHealth(), petUUID, "pets");

                    }
                }
            }
        };
        run.runTaskTimer(ImiPet.loader.getPlugin(), 0, 1);
    }

}
