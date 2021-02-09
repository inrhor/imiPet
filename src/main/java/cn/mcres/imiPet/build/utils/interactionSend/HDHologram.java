package cn.mcres.imiPet.build.utils.interactionSend;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.model.ModelInfoManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class HDHologram {
    public static void send(Player owner, Player clickPlayer, Entity entity, String modelId, UUID petUUID) {
        // 全息
        final Hologram hologram = HologramsAPI.createHologram(ImiPet.loader.getPlugin(), entity.getLocation());
        VisibilityManager visibilityManager = hologram.getVisibilityManager();
        visibilityManager.showTo(clickPlayer);
        visibilityManager.setVisibleByDefault(false);
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
        int stayTime = infoManager.getInfoClickTime()*20;
        double x = infoManager.getInfoClickX();
        double y = infoManager.getInfoClickY();
        double z = infoManager.getInfoClickZ();
        List<String> infoList = infoManager.getInfoClickStringList();
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                if (!entity.isDead()) {
                    hologram.clearLines();
                    List<String> newInfoList = ReplaceAll.petReplaceAll(infoList, owner, petUUID);
                    for (String string : newInfoList) {
                        hologram.appendTextLine(string);
                    }
                    hologram.teleport(entity.getLocation().add(x, y, z));
                }else {
                    hologram.delete();
                }
            }
        };run.runTaskTimer(ImiPet.loader.getPlugin(), 0, 1);
        Bukkit.getScheduler().runTaskLater(ImiPet.loader.getPlugin(), () -> {
            hologram.delete();
            run.cancel();
        }, stayTime);
    }
}
