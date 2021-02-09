package cn.mcres.imiPet.pet.listener;

import cn.mcres.imiPet.api.events.PetDamageByEntityEvent;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.UUID;

public class Hurt implements Listener {

    void hurtPet(Player attacher, Entity damager, Entity source, BuildPet target, EntityDamageByEntityEvent ev) {
        if (target.getPet() == null) return;// 宠物已经被删除

        if (!(target.getPet() instanceof Wolf)) return;

        double damage = ev.getFinalDamage();
        // 模拟宠物受到攻击状态
        LivingEntity entity = target.getPet();
        if (entity.getNoDamageTicks() > 0) {// 真正实体无敌中...
            return;
        }

        // 可能是发射器打的
        Bukkit.getPluginManager().callEvent(new PetDamageByEntityEvent(
                target.getPetUUID(), damager == null ? null : damager.getUniqueId(),
                damager, ev.getFinalDamage()));

        if (!entity.getUniqueId().equals(ev.getEntity().getUniqueId())) { // 打到了盔甲架
            ev.setCancelled(true);

            Vector base = entity.getLocation().subtract(source.getLocation())
                    .add(0, 0.5d, 0)
                    .toVector().normalize();

            entity.setHealth(Math.max(0, entity.getHealth() - damage));
            // 向量设置
            if (attacher == source) {
                // 直接攻击
                //noinspection deprecation 支持旧版 1.9-
                final ItemStack hand = attacher.getItemInHand();
                final int lv = hand.getEnchantmentLevel(Enchantment.KNOCKBACK);
                base.add(base.clone().multiply(lv * 2));
            } else if (source instanceof Arrow) {
                base.add(base.clone().multiply(((Arrow) source).getKnockbackStrength()));
            }
            entity.setVelocity(base);
            entity.setNoDamageTicks(entity.getMaximumNoDamageTicks()); // 设置无敌时间
        }
        // 设置宠物攻击目标
        if (damager instanceof LivingEntity) {
            Wolf pet = (Wolf) entity;
            pet.setTarget((LivingEntity) damager);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
        // 最后执行
    void petInjuredByAll(EntityDamageByEntityEvent ev) {
        if (ev.isCancelled())
            return;//事件已经取消, 拒绝执行
        Player attacker;
        Entity damager;
        if (ev.getDamager() instanceof Player) {
            // 来源玩家
            damager = attacker = (Player) ev.getDamager();
        } else if (ev.getDamager() instanceof Projectile) { // 抛射物
            final ProjectileSource shooter = ((Projectile) ev.getDamager()).getShooter();
            if (shooter instanceof Entity) {
                damager = (Entity) shooter;
            } else {
                damager = null;
            }
            if (shooter instanceof Player) {
                attacker = (Player) shooter;
            } else {
                attacker = null;// 抛射物来源不是玩家
            }
        } else {
            attacker = null;// 伤害来源其他实体
            damager = ev.getDamager();
        }
        BuildPet target = null;
        final Entity entity = ev.getEntity();
        if (entity instanceof ArmorStand) {
            // 盔甲架受伤
            if (entity.hasMetadata("imipet.uuid")) {
                UUID pid = UUID.fromString(entity.getMetadata("imipet.uuid").get(0).asString());
                for (BuildPet pet : ModelEntityManager.buildPets) {
                    if (pet.getPetUUID().equals(pid)) {
                        target = pet;
                        break;
                    }
                }
                if (target != null && attacker != null) {
                    // 检测是否是所有者
                    if (target.getPlayer().getUniqueId().equals(attacker.getUniqueId())) {
                        ev.setCancelled(true);
                        return;// 不可伤害自己的宠物
                    }
                }
            }
        }
        if (target == null) {
            for (BuildPet pet : ModelEntityManager.buildPets) {
                if (pet.getPet() != null)
                    if (pet.getPet().getUniqueId().equals(entity.getUniqueId())) {
                        target = pet;
                        if (attacker != null)
                            if (pet.getPlayer().getUniqueId().equals(attacker.getUniqueId())) {
                                ev.setCancelled(true);
                                return; // 不可伤害自己的宠物
                            }
                        break;
                    }
            }
        }
        if (target != null) {
            hurtPet(attacker, damager, ev.getDamager(), target, ev);
        }
    }
    /*void petInjuredByAll(EntityDamageByEntityEvent ev) {
        // 当玩家攻击盔甲架时
        if (ev.getDamager() instanceof Player) {
            // 伤害承受者
            Entity damageTaker = ev.getEntity();
            // 攻击者
            Player attacker = (Player) ev.getDamager();
            if (damageTaker instanceof ArmorStand) {
                if (damageTaker.hasMetadata("imipet.uuid")) {
                    UUID petUUID = UUID.fromString(damageTaker.getMetadata("imipet.uuid").get(0).asString());
                    UUID ownerUUID = UUID.fromString(damageTaker.getMetadata("playerUUID").get(0).asString());
                    double damage = ev.getFinalDamage();
                    final Set<UUID> uuids = info().getFollowingPetUUID(ownerUUID);
                    if (!uuids.contains(petUUID)) {
                        for (BuildPet pets : ImiPet.buildPets) {
                            Wolf pet = pets.getPet();
                            if (pets.getPetUUID().equals(petUUID)) {
                                // 模拟宠物受到攻击状态
                                pet.setHealth(pet.getHealth() - damage);
                                Location loc = pet.getLocation().add(1, 1, 1);
                                pet.teleport(loc);
                                // 设置宠物攻击目标
                                pet.setTarget(attacker);
                            }
                        }
                        Bukkit.getPluginManager().callEvent(new PetDamageByEntityEvent(petUUID, ownerUUID, attacker, damage));
                        return;
                    }
                }
            }
        }
        // 受到伤害的承受者是否是宠物
        for (BuildPet pets : ImiPet.buildPets) {
            Entity pet = pets.getPet();
            if (!ev.getEntity().equals(pet)) return;
            // 攻击者
            Entity attacker = ev.getDamager();
            double damage = ev.getFinalDamage();
            if (attacker instanceof Player) {
                if (pets.getPlayer().equals(attacker)) {
                    ev.setCancelled(true);
                    return;
                }
            }
            Bukkit.getPluginManager().callEvent(new PetDamageByEntityEvent(pets, pets.getPlayer(), attacker, damage));
        }
    }*/
}
