package cn.mcres.imiPet.api.skill;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.pet.BuildPet;
import cn.mcres.imiPet.skill.SkillCreator;
import cn.mcres.imiPet.skill.SkillFile;
import cn.mcres.imiPet.skill.cast.SkillCast;
import cn.mcres.imiPet.skill.cast.SkillManager;
import cn.mcres.imiPet.skill.model.AnimationModel;

import java.util.UUID;

import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SkillCastManager {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * @param skillID
     * @return 是否存在该技能ID，而不是宠物内部技能ID
     */
    public static boolean existSkillID(String skillID) {
        return SkillFile.skillIDList.contains(skillID.toLowerCase());
    }

    public static boolean petExistSkillID(BuildPet buildPet, String skillID) {
        ModelEntityManager mem = buildPet.getModelEntityManager();
        AnimationModel am = mem.getAnimationModel(skillID);
        return am != null;
    }

    public static void petCastSkill(BuildPet buildPet, String skillID) {
        ModelEntityManager mem = buildPet.getModelEntityManager();
        AnimationModel am = mem.getAnimationModel(skillID);
        if ( am.getTriggerList() != null ) {
            if ( !am.getTriggerList().isEmpty() ) {
                Player owner = buildPet.getPlayer();
                UUID petUUID = buildPet.getPetUUID();
                for (String s : am.getTriggerList()) {
                    String[] sp = s.split(":");
                    String type = sp[0];
                    String value = sp[1];
                    if (type.equals("food")) {
                        if (info().getPetNowFood(owner, petUUID, "pets") < Integer.parseInt(value)) {
                            /*for (String msg : GetLangYaml.DISSATISFY_SKILL_TRIGGER) {
                                Msg.send(owner,
                                        msg.replaceAll("@cause",
                                                GetLangYaml.SKILL_FOOD_SHORT.replaceAll("@value", value)));
                                return;
                            }*/
                            TLocale.sendTo(owner, "DISSATISFY_SKILL_TRIGGER", TLocale.asString("SKILL_FOOD_SHORT", value));
                        }
                    }else if (type.equals("permission")) {
                        if (!owner.hasPermission(value)) {
                            /*for (String msg : GetLangYaml.DISSATISFY_SKILL_TRIGGER) {
                                Msg.send(owner,
                                        msg.replaceAll("@cause",
                                                GetLangYaml.SKILL_PERMISSION_SHORT.replaceAll("@value", value)));
                                return;
                            }*/
                            TLocale.sendTo(owner, "DISSATISFY_SKILL_TRIGGER", TLocale.asString("SKILL_PERMISSION_SHORT", value));
                        }
                    }
                }
            }
        }
        if (!buildPet.getSkillCoolDown().containsKey(skillID) || buildPet.getSkillCoolDown().get(skillID) <= 0) {
            String animationID = am.getAnimationModels().get(0).getAnimationID();
            mem.setState("skill-" + skillID + "-" + animationID, true);
            castSkill(buildPet.getPet(), skillID);
            /*buildPet.getPlayer().sendMessage("你的宠物实施了技能 §a" + mem.getAnimationModel(skillID).getSkillName());*/
            buildPet.getSkillCoolDown().put(skillID, mem.getAnimationModel(skillID).getCoolDown());
//            MapAll.skillCoolDownMap.put(buildPet, MapAll.skillCoolDown.put(skillID, coolDown));
        }else {
            /*for (String msg : SKILL_COOLING) {
                Msg.send(buildPet.getPlayer(), msg
                        .replaceAll("%imipet_skill_name%", am.getSkillName())
                        .replaceAll("%imipet_skill_cooldown%", String.valueOf(buildPet.getSkillCoolDown().get(skillID))));
            }*/
            TLocale.sendTo(buildPet.getPlayer(), "SKILL_COOLING",
                    am.getSkillName(),
                    String.valueOf(buildPet.getSkillCoolDown().get(skillID)));
        }
    }

    /**
     *
     * @param skillID
     * @return 根据技能ID返回技能
     */
    public static SkillCreator getSkill(String skillID) {
        for (SkillCreator skillCreator : SkillFile.skillList) {
            if (skillCreator.getSkillID().equals(skillID.toLowerCase())) {
                return skillCreator;
            }
        }
        return null;
    }

    /**
     * 释放技能
     * @param entity
     * @param skillID
     */
    public static void castSkill(Entity entity, String skillID) {
        SkillCast.run(entity, skillID.toLowerCase());
    }

    /**
     * 清除指定宠物的已释放的技能
     * @param petUUID
     */
    public static void removePetSkill(UUID petUUID) {
        SkillManager.removePetSkillStand(petUUID);
    }

    /**
     * 清除全服技能，非彻底
     */
    public static void clearSkills() {
        SkillManager.removeAll();
    }

    /**
     * 强制彻底清除当前世界技能，请注意影响服务器性能
     */
    public static void forceClearSkills(World world) {
        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof ArmorStand)) continue;
            if (entity.hasMetadata("imipet.skill")) entity.remove();
        }
    }
}
