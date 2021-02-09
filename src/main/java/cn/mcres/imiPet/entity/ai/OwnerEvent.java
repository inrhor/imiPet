package cn.mcres.imiPet.entity.ai;

import cn.mcres.imiPet.other.MapAll;
import java.util.UUID;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OwnerEvent implements Listener {
    @EventHandler
    void ownerHurtTarget(EntityDamageByEntityEvent ev) {
        // 受伤的实体
        Entity entity = ev.getEntity();

        // 攻击者
        if (!(ev.getDamager() instanceof Player)) return;

        Player player = (Player) ev.getDamager();
        UUID playerUUID = player.getUniqueId();

        if (entity.hasMetadata("imipet.pet")) {
            // 确认主人
            if (UUID.fromString(entity.getMetadata("imipet.owner").get(0).asString()).equals(playerUUID)) {
                ev.setCancelled(true);
            }
        }else { // 攻击的实体如果不是Cem宠物
            if (MapAll.followingCemEntity.get(playerUUID) != null) { // 如果有跟随的Cem宠物
                IronGolem pet = (IronGolem)  MapAll.followingCemEntity.get(playerUUID);
                pet.setTarget((LivingEntity) entity);
            }
        }
    }

    @EventHandler
    void entityHurtOwner(EntityDamageByEntityEvent ev) {
        if (!(ev.getEntity() instanceof Player)) return;

        Player player = (Player) ev.getEntity();
        UUID playerUUID = player.getUniqueId();

        if (MapAll.followingCemEntity.get(playerUUID) != null) { // 如果有跟随的Cem宠物
            IronGolem pet = (IronGolem)  MapAll.followingCemEntity.get(playerUUID);
            pet.setTarget((LivingEntity) ev.getDamager());
        }
    }

    @EventHandler
    void entityHurtPet(EntityDamageByEntityEvent ev) {
        // 受伤的实体
        Entity entity = ev.getEntity();
        // 攻击者
        Entity attacker = ev.getDamager();

        if (entity.hasMetadata("imipet.pet")) {
            IronGolem pet = (IronGolem)  entity;
            if ((ev.getDamager() instanceof Player)) {
                Player player = (Player) attacker;
                UUID playerUUID = player.getUniqueId();
                if (UUID.fromString(entity.getMetadata("imipet.owner").get(0).asString()).equals(playerUUID)) {
                    ev.setCancelled(true);
                    return;
                }
            }
            pet.setTarget((LivingEntity) attacker);
        }
    }
}
