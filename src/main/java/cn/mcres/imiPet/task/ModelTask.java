package cn.mcres.imiPet.task;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.other.MapAll;
import cn.mcres.imiPet.pet.BuildPet;
import cn.mcres.imiPet.skill.model.AnimationModel;
import cn.mcres.imiPet.skill.model.SkillAnimationModel;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.component.StateProperty;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class ModelTask {

    public static void renderModel() {
        new BukkitRunnable() {
            public void run() {
                if (ModelEntityManager.modelEntityList.isEmpty()) return;
                for (ModelEntityManager mem : ModelEntityManager.modelEntityList) {
                    Entity entity = mem.getEntity();
                    if (!entity.isDead()) {
                        mem.tpModel();
                        mem.addModel();
                        if (!mem.getState().contains("skill-")) continue;
                        if (!MapAll.canNotMoveLoc.containsKey(entity)) continue;
                        entity.teleport(MapAll.canNotMoveLoc.get(entity));
                    } else {
                        mem.removeModel();
                    }
                }
            }
        }.runTaskTimer(ImiPet.loader.getPlugin(), 0, 1);
    }

    public static void skillModel() {
        new BukkitRunnable() {
            public void run() {
                if (!ModelEntityManager.modelEntityList.isEmpty()) {
                    for (ModelEntityManager mem : ModelEntityManager.modelEntityList) {
                        if (!mem.getState().contains("skill-")) continue;
                        String[] sp = mem.getState().split("-");
                        String skillID = sp[1];
                        AnimationModel am = mem.getAnimationModel(skillID);
                        SkillAnimationModel sa = am.getSkillAnimationModel(sp[2]);
                        int time = sa.getTime();
                        Entity entity = mem.getEntity();

                        /*if (sa.isUseModelEngine()) {
                            if (MapAll. < time) {
                                for (ActiveModel actm : moe.getModels(modelId)) {
                                    actm.addState(state, StateProperty.create(animationFrameAttack, 0, 10));
                                }
                                this.animationFrameAttack = animationFrameAttack+1;
                            }else {
                                animationFrameAttack = 0;
                            }
                        }*/

                        if (MapAll.petSkillTime.containsKey(entity)) {
                            time = MapAll.petSkillTime.get(entity);
                        }

                        if (sa.isUseModelEngine()) {
                            String modelId = mem.getModelId();
                            String animationID = sa.getAnimationID();
                            String skillAnimationID = skillID+animationID;
//                            ModelInfoManager im = ModelInfoManager.petModelList.get(modelId);
                            int animationFrameTime = 0;
                            if (mem.existAnimationFrameMap(skillAnimationID)) {
                                animationFrameTime = mem.getAnimationFrameMap(skillAnimationID);
                            }
                            ModeledEntity moe = ModelEngineAPI.getModelManager().getModeledEntity(entity.getUniqueId());
                            if (animationFrameTime < sa.getTime()) {
                                String state = "skill-"+skillID+animationID;
                                for (ActiveModel actm : moe.getModels(modelId)) {
                                    actm.addState(state, StateProperty.create(animationFrameTime, 0, 0));
                                }
                                mem.setAnimationFrameMap(skillAnimationID, animationFrameTime+1);
                            } else {
                                mem.removeAnimationFrameMap(skillAnimationID);
                            }
                        }

                        if (time > 0) {
                            if (!am.isCanMove()) {
                                if (!MapAll.canNotMoveLoc.containsKey(entity)) {
                                    MapAll.canNotMoveLoc.put(entity, entity.getLocation());
                                }
                            } else {
                                MapAll.canNotMoveLoc.remove(entity);
                            }
                            MapAll.petSkillTime.put(entity, time - 1);
                        } else {
                            MapAll.petSkillTime.remove(entity);
                            if (sa.getNextAnimationID() != null) {
                                mem.setState("skill-" + skillID + "-" + sa.getNextAnimationID(), true);
                            } else {
                                MapAll.canNotMoveLoc.remove(entity);
                                mem.setState("idle", true);
                            }
                        }
                    }
                }
                if (!ModelEntityManager.buildPets.isEmpty()) {
                    for (BuildPet buildPet : ModelEntityManager.buildPets) {
                        ModelEntityManager mem = buildPet.getModelEntityManager();
                        if (!mem.getState().contains("skill-")) continue;
                        String[] sp = mem.getState().split("-");
                        String skillID = sp[1];
                        if (buildPet.getSkillCoolDown().containsKey(skillID) && buildPet.getSkillCoolDown().get(skillID) > 0) {
                            buildPet.getSkillCoolDown().put(skillID, buildPet.getSkillCoolDown().get(skillID)-1);
                        }else {
                            buildPet.getSkillCoolDown().remove(skillID);
                        }
                    }
                }
            }
        }.runTaskTimer(ImiPet.loader.getPlugin(), 0, 1); // 20改为了1
    }
}
