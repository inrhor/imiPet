package cn.mcres.imiPet.pet.listener;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.model.ModelInfoManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.Set;
import java.util.UUID;

public class OwnerGetExp implements Listener {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    @EventHandler
    void onPlayerChangeExp(PlayerExpChangeEvent ev) {
        Player player = ev.getPlayer();
        UUID playerUUID = player.getUniqueId();
        int exp = ev.getAmount();
        // 如果玩家有宠物
        if (info().havePet(player)) {
            final Set<UUID> followingPetUUID = info().getFollowingPetUUID(player);
            double adds = 0;
            for (UUID petUUID : followingPetUUID) {
                if (petUUID == null) return;
                // 开启经验算法
                ModelInfoManager infoManager = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, "pets"));
//                String JavaScriptCode = GetPetsYaml.getString(info().getPetModelId(player, petUUID, "pets"), "addExpByPlayer");
                String JavaScriptCode = infoManager.getAddExpByPlayer();
                /*if (addExp.contains("~")) { // 如果使用了范围值
                    int min = MathTool.rounding(MathTool.stringOperation(MathTool.getTargetBefore(addExp, "~")));
                    int max = MathTool.rounding(MathTool.stringOperation(MathTool.getTargetAfter(addExp, "~")));
                    int random = MathTool.random(min, max);
                    info().addExpBox(playerUUID, random);
                } else { // 不使用范围值
                }*/ // JavaScript Support Random Eval.
                double process = (double) exp / followingPetUUID.size();
                if (JavaScriptCode == null) {
                    // UN Process.
                    adds += process;
                } else {
                    adds += MathTool.stringOperation(JavaScriptCode, "add_exp", process);
                }
            }
            info().addExpBox(playerUUID, MathTool.rounding(adds));
        }
    }
}
