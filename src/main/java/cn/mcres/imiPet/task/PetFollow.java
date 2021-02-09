package cn.mcres.imiPet.task;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.pet.BuildPet;
import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.HologramManager;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;

public class PetFollow {

    public static void petNameFollowTask() {
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                if (ModelEntityManager.buildPets.isEmpty()) return;
                for (BuildPet buildPet : ModelEntityManager.buildPets) {
                    if (!buildPet.isEnableShow()) continue;
                    LivingEntity pet = buildPet.getPet();
                    if (!pet.isDead()) {
                        List<String> newNameList = ReplaceAll.petReplaceAll(buildPet.getShowPetName(), buildPet.getPlayer(), buildPet.getPetUUID());
                        Location newLoc = pet.getLocation().add(buildPet.getX(), buildPet.getHeight(), buildPet.getZ());
                        String useShow = buildPet.getUseShow();
                        switch (useShow) {
                            case "tr":
                                if (buildPet.getTrHologram() != null) {
                                    buildPet.getTrHologram().updateLines(newNameList);
                                    buildPet.getTrHologram().updateLocation(newLoc);
                                    buildPet.getTrHologram().display();
                                }
                                break;
                            case "hd":
                                if (buildPet.getHDHologram() != null) {
                                    buildPet.getHDHologram().clearLines();
                                    for (String string : newNameList) {
                                        buildPet.getHDHologram().appendTextLine(string);
                                    }
                                    buildPet.getHDHologram().teleport(newLoc);
                                }
                                break;
                            case "cmi":
                                if (buildPet.getCmiHologram()!= null) {
                                    buildPet.getCmiHologram().setLines(newNameList);
                                    buildPet.getCmiHologram().setLoc(newLoc);
                                    buildPet.getCmiHologram().update();
                                    // CMI 无刷新方法
                                }
                                break;
                        }
                    }else {
                        if (buildPet.getHDHologram() != null) {
                            buildPet.getHDHologram().delete();
                        }
                        if (buildPet.getTrHologram() != null) {
                            buildPet.getTrHologram().delete();
                        }
                        if (buildPet.getCmiHologram()!= null) {
                            new HologramManager(CMI.getInstance()).removeHolo(buildPet.getCmiHologram());
                        }
                    }
                }
            }
        };
        run.runTaskTimer(ImiPet.loader.getPlugin(), 0, 1);
    }

}
