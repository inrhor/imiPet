package cn.mcres.imiPet.listener;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle;
import cn.mcres.imiPet.api.fastHandle.RideHandle;
import cn.mcres.imiPet.api.other.GetBooleanValue;
import cn.mcres.imiPet.other.MapAll;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerJoinQuit implements Listener {

    @EventHandler
    void onJoin(PlayerJoinEvent ev) {
        final Player player = ev.getPlayer();
        if (!GetBooleanValue.inDisablePetWorld(player)) {
            // 修复名字盔甲架不会隐藏的问题
            new BukkitRunnable() {
                public void run() {
                    FollowSetHandle.runPetSpawn(player);
                }
            }.runTaskLater(ImiPet.loader.getPlugin(), 1);
        }
    }

    @EventHandler
    void onQuit(PlayerQuitEvent ev) {
        final Player player = ev.getPlayer();
        FollowSetHandle.runPetRemove(player, null);
        UUID playerUUID = player.getUniqueId();
        if (MapAll.ridePetList.get(playerUUID)!=null) {
            RideHandle.ridePetStop(playerUUID, MapAll.ridePet.get(playerUUID), false);
            MapAll.ridePetList.remove(playerUUID);
        }
        ImiPet.loader.getInfo().forceSave(player.getUniqueId());
    }
}
