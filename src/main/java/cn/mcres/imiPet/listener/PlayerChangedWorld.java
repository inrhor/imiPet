package cn.mcres.imiPet.listener;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle;
import cn.mcres.imiPet.api.other.GetBooleanValue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.UUID;


public class PlayerChangedWorld implements Listener {

    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    @EventHandler
    void playerChangeWorld(PlayerChangedWorldEvent ev) {
        Player player = ev.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (info().getPetsPackList(playerUUID).isEmpty()) {
            return;
        }
        FollowSetHandle.runPetRemove(player, null);
        if (GetBooleanValue.inDisablePetWorld(player)) {
            for (UUID petUUID : info().getFollowingPetUUID(player)) {
                info().setPetFollow(player, false, petUUID);
            }
            return;
        }
        FollowSetHandle.runPetSpawn(player);
    }

}
