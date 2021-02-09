package cn.mcres.imiPet.pet.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class Eat implements Listener {
    @EventHandler
    void eat(EntityRegainHealthEvent ev) {
        if (ev.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.EATING)) {
            if (ev.getEntity().hasMetadata("imiPet")) {
                ev.setCancelled(true);
            }
        }
    }
}
