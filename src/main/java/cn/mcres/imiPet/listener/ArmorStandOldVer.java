package cn.mcres.imiPet.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ArmorStandOldVer implements Listener {
    @EventHandler
    void playerKillModel(EntityDamageEvent ev) {
        if (ev.getEntity() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) ev.getEntity();
            if (armorStand.hasMetadata("imipet:modelId")) ev.setCancelled(true);
        }
    }
}
