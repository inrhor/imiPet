package cn.mcres.imiPet.pet.listener;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import cn.mcres.imiPet.pet.BuildPet;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Attack implements Listener {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    @EventHandler
    void onPetAttack(EntityDamageByEntityEvent ev) {
        for (BuildPet pets : ModelEntityManager.buildPets) {
            Entity pet = pets.getPet();
            if (ev.getDamager().equals(pet)) {
                Player e = pets.getPlayer();
                UUID petUUID = pets.getPetUUID();

                /*
                 * 消耗宠物活力值
                 *
                 * 触发条件：
                 * 该宠物开启活力值消耗
                 *
                 * 每次攻击会对活力值计数器+1
                 * 计数达到30次会消耗宠物活力值-1
                 *
                 * 但是如果宠物活力值小于50%则计数器 -> +2
                 * 小于30%则计数器 -> +5
                 */
                ModelInfoManager infoManager = pets.getModelInfoManager();
                if (infoManager.isFoodEnable()) {
                    if (info().getPetNowFood(e, petUUID, "pets") > 0) {
                        MapAll.foodCount.put(pets, getFoodCount(pets) + 1);
                        if (info().getPetNowFood(e, petUUID, "pets") < (info().getPetMaxFood(e, petUUID, "pets") / 0.5) && info().getPetNowFood(e, petUUID, "pets") > (info().getPetMaxFood(e, petUUID, "pets") / 0.3)) {
                            runFoodCount(e, pets, 2);
                        } else if (info().getPetNowFood(e, petUUID, "pets") < (info().getPetMaxFood(e, petUUID, "pets") / 0.3)) {
                            runFoodCount(e, pets, 5);
                        } else {
                            runFoodCount(e, pets, 1);
                        }
                    } else {
                        FollowSetHandle.runPetRemove(e, petUUID);
                        TLocale.sendTo(e, "PET_TIRED");
                        if (info().getPetNowFood(e, petUUID, "pets") < 0) {
                            info().setPetNowFood(e, 0, petUUID, "pets");
                        }
                    }
                }

                // 设置伤害
                double damage = MathTool.random(info().getPetMinDamage(e, petUUID, "pets"), info().getPetMaxDamage(e, petUUID, "pets"));
                ev.setDamage(damage);

                ModelEntityManager modelEntityManager = pets.getModelEntityManager();

                Bukkit.getScheduler().runTaskLater(ImiPet.loader.getPlugin(), () -> {
                    if (ev.getEntity().isDead()) {
                        if (MapAll.runTimeAttack.get(petUUID)!=null) {
//                            MapAll.getModelEntity(pet).removeState("attack");
                            modelEntityManager.setState("idle", false);
                            MapAll.runTimeAttack.get(petUUID).cancel();
                            MapAll.runTimeAttack.remove(petUUID);
                        }
                    }
                }, 10);

                int attackStopTime = 0;

                if (infoManager.isUseModelEngine()) {
                    attackStopTime = infoManager.getModelEngineStopTimeAttack()*20;
                }else {
                    attackStopTime = infoManager.getAnimationAttackTime()*20;
                }

                if (pet.hasMetadata("imiPet.attack")) {
                    if (!pet.getMetadata("imiPet.attack").get(0).asBoolean()) {
                        pet.setMetadata("imiPet.attack", new FixedMetadataValue(ImiPet.loader.getPlugin(), true));
//                        MapAll.getModelEntity(pet).addState("attack");
                        modelEntityManager.setState("attack", false);
                        BukkitRunnable runTime = new BukkitRunnable() {
                            public void run() {
                                if (pets.getPet() != null) {
                                    if (pets.getPet().getHealth() > 0) {
//                                        MapAll.getModelEntity(pet).removeState("attack");
                                        modelEntityManager.setState("idle", false);
                                        pet.setMetadata("imiPet.attack", new FixedMetadataValue(ImiPet.loader.getPlugin(), false));
                                        if (MapAll.runTimeAttack.get(petUUID)!=null) {
                                            MapAll.runTimeAttack.remove(petUUID);
                                        }
                                    }
                                }
                            }
                        };
                        MapAll.runTimeAttack.put(petUUID, runTime);
                        runTime.runTaskLater(ImiPet.loader.getPlugin(), attackStopTime);
                    }
                }
            }
        }
    }

    // 返回活力值计数器
    private static int getFoodCount(BuildPet pets) {
        if (MapAll.foodCount.get(pets) != null) {
            return MapAll.foodCount.get(pets);
        }
        return 0;
    }

    // 活力值计数器有条件扣除活力值
    private static void runFoodCount(Player player, BuildPet pets, int del) {
        UUID petUUID = pets.getPetUUID();
        if (MapAll.foodCount.get(pets) != null) {
            int foodCount = MapAll.foodCount.get(pets);
            if (foodCount < 30) {
                MapAll.foodCount.put(pets, foodCount + del);
            } else {
                MapAll.foodCount.put(pets, 0);
                info().setPetNowFood(player, info().getPetNowFood(player, petUUID, "pets") - 1, petUUID, "pets");
            }
        } else {
            MapAll.foodCount.put(pets, 0);
        }
    }
}
