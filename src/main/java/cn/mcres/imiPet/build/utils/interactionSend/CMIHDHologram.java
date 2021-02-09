package cn.mcres.imiPet.build.utils.interactionSend;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.model.ModelInfoManager;
import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.Zrips.CMI.Modules.Holograms.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class CMIHDHologram {

    public static void send(Player owner, Entity entity, String modelId, UUID petUUID) {
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
        int stayTime = infoManager.getInfoClickTime()*20;
        double x = infoManager.getInfoClickX();
        double y = infoManager.getInfoClickY();
        double z = infoManager.getInfoClickZ();
        List<String> infoList = infoManager.getInfoClickStringList();
        String stringPetUUID =String.valueOf(petUUID);
        CMIHologram cmiHologram = new CMIHologram(stringPetUUID, entity.getLocation());
        CMI.getInstance().getHologramManager().addHologram(cmiHologram);
        cmiHologram.setLines(infoList);
        cmiHologram.update();
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                if (!entity.isDead()) {
                    List<String> newInfoList = ReplaceAll.petReplaceAll(infoList, owner, petUUID);
                    cmiHologram.setLines(newInfoList);
                    cmiHologram.setLoc(entity.getLocation().add(x, y, z));
                    cmiHologram.update();
                    // CMI 无刷新方法
                }else {
                    new HologramManager(CMI.getInstance()).removeHolo(cmiHologram);
                }
            }
        };run.runTaskTimer(ImiPet.loader.getPlugin(), 0, 1);
        Bukkit.getScheduler().runTaskLater(ImiPet.loader.getPlugin(), () -> {
            new HologramManager(CMI.getInstance()).removeHolo(cmiHologram);
            run.cancel();
        }, stayTime);
    }
}
