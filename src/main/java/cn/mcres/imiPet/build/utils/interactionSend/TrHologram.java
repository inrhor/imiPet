package cn.mcres.imiPet.build.utils.interactionSend;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.model.ModelInfoManager;
import me.arasple.mc.trhologram.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TrHologram {
    public static void send(Player owner, Player clickPlayer, Entity entity, String modelId, UUID petUUID) {
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
        int stayTime = infoManager.getInfoClickTime()*20;
        double x = infoManager.getInfoClickX();
        double y = infoManager.getInfoClickY();
        double z = infoManager.getInfoClickZ();
        List<String> infoList = infoManager.getInfoClickStringList();
        String stringPetUUID =String.valueOf(petUUID);
        final Hologram hologram = Hologram.Companion.createHologram(
                ImiPet.loader.getPlugin()
                , stringPetUUID,
                entity.getLocation(),
                Collections.emptyList());
        hologram.display(clickPlayer);
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                if (!entity.isDead()) {
                    List<String> newInfoList = ReplaceAll.petReplaceAll(infoList, owner, petUUID);
                    hologram.updateLines(newInfoList);
                    hologram.updateLocation(entity.getLocation().add(x, y, z));
                }else {
                    hologram.delete();
                }
            }
        };
        run.runTaskTimer(ImiPet.loader.getPlugin(), 0, 1);
        Bukkit.getScheduler().runTaskLater(ImiPet.loader.getPlugin(), () -> {
            hologram.delete();
            run.cancel();
        }, stayTime);
    }
}
