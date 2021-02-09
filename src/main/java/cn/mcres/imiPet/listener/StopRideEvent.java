package cn.mcres.imiPet.listener;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.fastHandle.RideHandle;
import cn.mcres.imiPet.server.ServerInfo;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.UUID;

public class StopRideEvent implements Listener {
    public static ServerInfo getServerInfo() {
        return ImiPet.loader.getServerInfo();
    }

    @EventHandler
    void playerStopRide(EntityDismountEvent event) {
        if (!getServerInfo().isOldVersionMethod()) {
            UUID playerUUID = event.getEntity().getUniqueId();
            Entity entity = event.getDismounted();
            if (entity.hasMetadata("imipet.ride")) {
                RideHandle.ridePetStop(playerUUID, entity, true);
            }
            return;
        }
        UUID playerUUID = event.getDismounted().getUniqueId();
        Entity entity = event.getEntity();
        if (entity.hasMetadata("imipet.ride")) {
            RideHandle.ridePetStop(playerUUID, entity, true);
        }
    }
}
