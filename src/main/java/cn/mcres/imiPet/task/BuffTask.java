package cn.mcres.imiPet.task;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.buff.BuffAPManager;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.scheduler.BukkitRunnable;

public class BuffTask {
    public static void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!ModelEntityManager.buildPets.isEmpty()) {
                    for (BuildPet buildPet : ModelEntityManager.buildPets) {
                        BuffAPManager.setBuffAP(buildPet);
                    }
                }
            }
        }.runTaskTimer(ImiPet.loader.getPlugin(), 0, ImiPet.loader.getPlugin().getConfig().getInt("buff.refreshTime")*20);
    }
}
