package cn.mcres.imiPet.skill.cast;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.skill.SkillCastManager;
import cn.mcres.imiPet.build.utils.LocTool;
import cn.mcres.imiPet.instal.lib.ModelLib;
import cn.mcres.imiPet.skill.SkillAnimationCreator;
import cn.mcres.imiPet.skill.SkillCreator;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.component.ModelOption;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SkillCast {

    public static JavaPlugin getPlugin() {
        return ImiPet.loader.getPlugin();
    }

    public static void run(Entity entity, String skillID) {
        if (SkillCastManager.existSkillID(skillID)) {
            SkillCreator skillCreator = SkillCastManager.getSkill(skillID);
            if (skillCreator == null) return;

            String startID = skillCreator.getStartAnimation();
            SkillAnimationCreator animationCreator = skillCreator.getAnimationCreator(startID);

            if (animationCreator.getType().equals("item")) {
                ArmorStand mainStand = newArmorStand(entity, skillCreator);
                runAnimationSkill(entity, skillCreator, animationCreator, mainStand);
            }else if (animationCreator.getType().equals("mythicMobs")) {
                runAnimationMMSkill(entity, skillCreator, animationCreator, null);
            }

        }
    }

    private static void tpStand(SkillAnimationCreator animationCreator, ArmorStand mainStand, Entity entity) {
        if (animationCreator.getCastLocationItem() != null) {
            String[] castLocList = animationCreator.getCastLocationItem().split(":");
            String tpType = castLocList[0];
            String animationID = animationCreator.getAnimationID();
            String mate = "imipet:animationID";
            if (hasAnimationIDMate(mainStand, mate, animationID)) return;
            setMainStandMeta(mainStand, mate, animationID);
            if (tpType.equals("follow")) {
                BukkitRunnable runMain = new BukkitRunnable() {
                    public void run() {
                        if (entity.isDead() || mainStand.isDead()) {
                            SkillManager.remove(mainStand);
                            cancel();
                            return;
                        }
                        if (!hasAnimationIDMate(mainStand, mate, animationID)) {
                            cancel();
                            return;
                        }
                        mainStand.teleport(LocTool.getTpLoc(castLocList, entity.getLocation()));
                    }
                };runMain.runTaskTimer(getPlugin(), 0, 5);
            }else {
                mainStand.teleport(LocTool.getTpLoc(castLocList, entity.getLocation()));
            }
        }
    }

    private static void setMainStandMeta(ArmorStand mainStand, String mate, String value) {
        mainStand.setMetadata(mate, new FixedMetadataValue(getPlugin(), value));
    }

    private static boolean hasAnimationIDMate(ArmorStand mainStand, String mate, String animationID) {
        if (mainStand.hasMetadata(mate)) {
            return mainStand.getMetadata(mate).get(0).asString().equals(animationID);
        }
        return false;
    }

    private static void runAnimationSkill(
            Entity entity,
            SkillCreator skillCreator,
            SkillAnimationCreator animationCreator,
            ArmorStand mainStand) {

        if (entity == null || entity.isDead()) {
            return;
        }

        if (mainStand == null) {
            mainStand = newArmorStand(entity, skillCreator);
        }

        if (animationCreator.getType().equals("item")) {
            mainStand.setHelmet(animationCreator.getItemStack());
        }else {
            if (animationCreator.getType().equals("modelEngine") && ModelLib.ModelLibEnable) {
                ModelEngineAPI.getModelManager().createModeledEntity(
                        mainStand,
                        new ModelOption(animationCreator.getModelId(), true, true, false, false, true),
                        false);
            }
            // 未完成，动态动画部分
        }

        mainStand.setGravity(animationCreator.isGravityItem());

        tpStand(animationCreator, mainStand, entity);

        SkillAnimationCast animationCast = new SkillAnimationCast(mainStand);
        animationCast.setAnimationCreator(animationCreator);

        if (animationCreator.getScriptItem() != null) {
            animationCast.runScript(mainStand);
        }

        buffScriptRun(skillCreator, animationCreator, animationCast, entity, mainStand);

        if (animationCreator.getTime() <= 0) return;
        ArmorStand finalMainStand = mainStand;
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                if (animationCreator.getNextAnimationID() != null) {
                    SkillManager.remove(finalMainStand);
                    runNextAnimation(skillCreator, entity, null, animationCreator.getNextAnimationID());
                }
            }
        };run.runTaskLater(getPlugin(), animationCreator.getTime());
    }

    private static void runAnimationMMSkill(
            Entity entity,
            SkillCreator skillCreator,
            SkillAnimationCreator animationCreator,
            ArmorStand mainStand) {

        if (animationCreator.getType().equals("item")) {
            runAnimationSkill(entity, skillCreator, animationCreator, mainStand);
            return;
        }

        if (mainStand != null) {
            SkillManager.remove(mainStand);
        }

        if (animationCreator.getMythicMobsSkills() == null) return;

        for (String skills : animationCreator.getMythicMobsSkills()) {
            String[] skillList = skills.split(":");
            String mmSkillID = skillList[0];
            int laterTime = Integer.parseInt(skillList[1])*20;
            BukkitRunnable run = new BukkitRunnable() {
                public void run() {
                    if (entity.isDead()) {
                        cancel();
                        return;
                    }
                    new BukkitAPIHelper().castSkill(entity, mmSkillID);
                }
            };run.runTaskLater(getPlugin(), laterTime);
        }

        if (animationCreator.getTime() <= 0) return;
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                if (animationCreator.getNextAnimationID() != null) {
                    runNextAnimation(skillCreator, entity, mainStand, animationCreator.getNextAnimationID());
                }else {
                    if (mainStand == null) return;
                    SkillManager.remove(mainStand);
                }
            }
        };run.runTaskLater(getPlugin(), animationCreator.getTime());
    }

    private static void runNextAnimation(SkillCreator skillCreator, Entity entity, ArmorStand mainStand, String nextAnimationID) {
            if (skillCreator.existAnimationCreator(nextAnimationID)) {
                SkillAnimationCreator nextAnimationCreator = skillCreator.getAnimationCreator(nextAnimationID);
                String type = nextAnimationCreator.getType();
                if (type.equals("item")) {
                    runAnimationSkill(entity, skillCreator, nextAnimationCreator, mainStand);
                }else if (type.equals("mythicMobs")) {
                    runAnimationMMSkill(entity, skillCreator, nextAnimationCreator, mainStand);
                }
            }else {
                SkillManager.remove(mainStand);
            }
    }

    private static void buffScriptRun(SkillCreator skillCreator,
                                      SkillAnimationCreator animationCreator,
                                      SkillAnimationCast animationCast,
                                      Entity entity,
                                      ArmorStand mainStand) {
        if (animationCreator.getBuffScriptItem() != null) {
            for (String buffScript : animationCreator.getBuffScriptItem()) {
                String[] scriptList = buffScript.split(":");
                String type = scriptList[0];
                if (type.equals("MythicMobs")) {
                    String mmSkillID = scriptList[1];
                    int laterTime = Integer.parseInt(scriptList[2])*20;
                    BukkitRunnable run = new BukkitRunnable() {
                        public void run() {
                            if (mainStand.isDead() ||
                                    animationCast.getTarget() == null ||
                                    animationCast.getTarget().isDead()) {
                                cancel();
                                return;
                            }
                            Collection<Entity> entities = new ArrayList<>(Collections.singletonList(animationCast.getTarget()));
                            Collection<Location> entitiesLoc = new ArrayList<>(Collections.singletonList(animationCast.getTarget().getLocation()));
                            new BukkitAPIHelper().castSkill(mainStand, mmSkillID, mainStand.getLocation(), entities, entitiesLoc, 1);
                        }
                    };run.runTaskLater(getPlugin(), laterTime);
                }
            }
        }
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                if (mainStand.isDead()) return;
                if (animationCreator.getConditionItem() != null) {
                    String[] conditionList = animationCreator.getConditionItem().split(":");
                    String conditionType = conditionList[0];
                    if (conditionType.equals("aim")) {

                        boolean nod = Boolean.parseBoolean(conditionList[1]);
                        String targetType = conditionList[2];
                        double range = Double.parseDouble(conditionList[3]);
                        // 扫描并赋予目标
                        animationCast.checkTarget(mainStand, targetType, range, nod);

                        // 扫描发现无目标时执行下一个动态ID
                        // 否则再次触发效果
                        int timeCheck = Integer.parseInt(conditionList[4])*20;
                        BukkitRunnable run = new BukkitRunnable() {
                            public void run() {
                                if (animationCast.getTarget() != null) {
                                    buffScriptRun(skillCreator, animationCreator, animationCast, entity, mainStand);
                                }else {
                                    String nextID = conditionList[5];
                                    runNextAnimation(skillCreator, entity, mainStand, nextID);
                                }
                            }
                        };run.runTaskLater(getPlugin(), timeCheck);
                    }
                }
            }
        };;run.runTaskLater(getPlugin(), animationCreator.getTimeConditionItem());
    }

    private static ArmorStand newArmorStand(Entity entity, SkillCreator skillCreator) {
        Location entityLoc = entity.getLocation();

        ArmorStand mainStand = (ArmorStand) entity.getWorld().spawnEntity(entityLoc, EntityType.ARMOR_STAND);

        mainStand.setInvulnerable(true);
        mainStand.setBasePlate(false);
        mainStand.setSilent(true);
        mainStand.setVisible(false);
        mainStand.setSilent(true);
        mainStand.setAI(false);
        mainStand.setMetadata("imipet.skill", new FixedMetadataValue(getPlugin(), "main"));
        mainStand.setMetadata("imipet.skill.entity", new FixedMetadataValue(getPlugin(), entity.getUniqueId().toString()));
        mainStand.setCustomNameVisible(false);

        if (skillCreator.getBuildPet() != null) {
            mainStand.setMetadata(
                    "imipet.pet",
                    new FixedMetadataValue(getPlugin(), skillCreator.getBuildPet()));
        }

        SkillManager.skillStandList.add(mainStand);

        return mainStand;
    }
}
