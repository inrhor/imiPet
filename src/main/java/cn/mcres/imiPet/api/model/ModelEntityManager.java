package cn.mcres.imiPet.api.model;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.model.ASI;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.pet.BuildPet;
import cn.mcres.imiPet.skill.model.AnimationModel;
import cn.mcres.imiPet.skill.model.SkillAnimationModel;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.component.ModelOption;
import com.ticxo.modelengine.api.model.component.ModelState;
import com.ticxo.modelengine.api.model.component.StateProperty;
import java.util.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class ModelEntityManager {
    private Entity entity;

    private boolean useModelEngine;

    private ArmorStand armorStand;
    private BuildPet buildPet;
    private String modelId;
    private String state;
    private EulerAngle head = new EulerAngle(0, 0, 0);
    private EulerAngle body = new EulerAngle(0, 0, 0);
    private Vector preVec;

    private ItemStack idleItemStack = new ItemStack(Material.DIAMOND_HOE);
    private ItemStack walkItemStack = new ItemStack(Material.DIAMOND_HOE);
    private ItemStack attackItemStack = new ItemStack(Material.DIAMOND_HOE);

    private String animationItemNameIdle;
    private String animationItemNameWalk;
    private String animationItemNameAttack;

    private int animationCustomModelDataIdle;
    private int animationCustomModelDataWalk;
    private int animationCustomModelDataAttack;

    private int animationFrameAttack;
    /**
     * SkillID+AnimationID timeTick
     */
    public HashMap<String, Integer> animationFrameMap = new LinkedHashMap<>();

    private double modelLocationH;

    private List<AnimationModel> animationModelList;

    public static List<ModelEntityManager> modelEntityList = new ArrayList<>();

    public JavaPlugin getPlugin() {
        return ImiPet.loader.getPlugin();
    }

    public void setItemStack(Material idle, Material walk, Material attack) {
        this.idleItemStack = new ItemStack(idle);
        this.walkItemStack = new ItemStack(walk);
        this.attackItemStack = new ItemStack(attack);
    }

    /**
     *
     * 所有BuildPet列表
     */
    public static final List<BuildPet> buildPets = new ArrayList<>();

    public void commonModelManager(Entity entity, String modelId) {
        entity.setMetadata("imipet:model", new FixedMetadataValue(getPlugin(), true));
        this.modelId = modelId;
        this.entity = entity;
        ModelInfoManager mim = ModelInfoManager.petModelList.get(this.modelId);
        if (!mim.isVisible()) {
            setInVisible();
        }
        setState("idle", false);
    }

    public ModelEntityManager(Entity entity, String modelId, BuildPet buildPet) {
        /*entity.setMetadata("imipet:model", new FixedMetadataValue(getPlugin(), true));
        this.modelId = modelId;
        this.entity = entity;*/
        commonModelManager(entity, modelId);
        this.buildPet = buildPet;
        /*setInVisible();
        setState("idle", false);*/
    }

    public ModelEntityManager(Entity entity, String modelId) {
        /*entity.setMetadata("imipet:model", new FixedMetadataValue(getPlugin(), true));
        this.modelId = modelId;
        this.entity = entity;
        setInVisible();
        setState("idle", false);*/
        commonModelManager(entity, modelId);
    }

    public ModelEntityManager(Entity entity, String modelId, BuildPet buildPet,
                              Material idle, Material walk, Material attack) {
        /*entity.setMetadata("imipet:model", new FixedMetadataValue(getPlugin(), true));
        this.modelId = modelId;
        this.entity = entity;*/
        commonModelManager(entity, modelId);
        this.buildPet = buildPet;
        /*setInVisible();
        setState("idle", false);*/
        setItemStack(idle, walk, attack);
    }

    public ModelEntityManager(Entity entity, String modelId, Material idle, Material walk, Material attack) {
        /*entity.setMetadata("imipet:model", new FixedMetadataValue(getPlugin(), true));
        this.modelId = modelId;
        this.entity = entity;
        setInVisible();
        setState("idle", false);*/
        commonModelManager(entity, modelId);
        setItemStack(idle, walk, attack);
    }

    /**
     * 生成模型
     * @param useModelEngine 是否启用ModelEngine模型引擎
     * @param noIsRide 是否为乘骑状态的宠物
     */
    public void spawnModel(boolean useModelEngine, boolean noIsRide) {
        if (!useModelEngine) {
            Location location = this.entity.getLocation().add(0, this.modelLocationH, 0);
//        this.packetEntityModel = new PacketEntityModel(location);
            if (buildPet != null) {
                armorStand = ASI.iniArmorStand((ArmorStand) entity.getWorld().spawnEntity(location, EntityType.ARMOR_STAND), buildPet);
            } else {
                armorStand = ASI.iniArmorStand((ArmorStand) entity.getWorld().spawnEntity(location, EntityType.ARMOR_STAND), this.modelId);
            }
        }else {
            ModelEngineAPI.getModelManager().createModeledEntity(
                    (LivingEntity) this.entity,
                    new ModelOption(this.modelId, noIsRide, true, true, false, true),
                    false);
        }
        modelEntityList.add(this);
    }

    public void tpModel() {
        if (!isUseModelEngine()) {
            if (armorStand == null || entity == null) return;
            updateRotation();
            preVec = entity.getLocation().toVector();
            Location location = entity.getLocation().add(0, this.modelLocationH, 0);
            armorStand.teleport(location);
        }
    }

    public boolean isUseModelEngine() {
        return useModelEngine;
    }

    public void setUseModelEngine(boolean useModelEngine) {
        this.useModelEngine = useModelEngine;
    }

    private void setInVisible() {
        if (this.entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) this.entity;
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 10, true, false));
        }
        if (this.entity instanceof ArmorStand) {
            ArmorStand stand = (ArmorStand) this.entity;
            stand.setVisible(false);
        }
    }

    /**
     * 主要用于非宠物实体，即坐骑和生物怪物
     */
    private static List<ModelEntityManager> commonModel = new ArrayList<>();

    /**
     * 主要用于非宠物实体，即坐骑，固定空闲状态
     * @param entity
     * @param modelId
     */
    public static void fastCommonSpawnModel(Entity entity, String modelId) {
        if (ModelInfoManager.petModelList.get(modelId) == null) return;
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
        ModelEntityManager modelEntityManager = new ModelEntityManager(entity, modelId);
        modelEntityManager.setAnimationItemNameIdle(infoManager.getAnimationItemNameIdle());
        modelEntityManager.setAnimationItemNameWalk(infoManager.getAnimationItemNameWalk());
        modelEntityManager.setAnimationItemNameAttack(infoManager.getAnimationItemNameAttack());
        modelEntityManager.setAnimationCustomModelDataIdle(infoManager.getAnimationCustomModelDataIdle());
        modelEntityManager.setAnimationCustomModelDataWalk(infoManager.getAnimationCustomModelDataWalk());
        modelEntityManager.setAnimationCustomModelDataAttack(infoManager.getAnimationCustomModelDataAttack());
        modelEntityManager.setModelLocationH(infoManager.getModelLocationH());
        modelEntityManager.setUseModelEngine(infoManager.isUseModelEngine());
        modelEntityManager.spawnModel(infoManager.isUseModelEngine(), false);
        modelEntityManager.setAnimationModelList(infoManager.getAnimationModelList());
        commonModel.add(modelEntityManager);

        /*if (modelEntityManager.useModelEngine) {
            ModelEngineAPI.getModelManager().createModeledEntity(
                    (LivingEntity) entity,
                    new ModelOption(modelId, true, true, true, false, true),
                    false);
        }*/
    }

    /**
     * 用于可变状态且非宠物实体
     * @param modelEntityManager
     * @param modelId
     */
    public static void fastSpawnModel(ModelEntityManager modelEntityManager, String modelId, boolean useModelEngine) {
        if (ModelInfoManager.petModelList.get(modelId) == null) return;
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
        modelEntityManager.setAnimationItemNameIdle(infoManager.getAnimationItemNameIdle());
        modelEntityManager.setAnimationItemNameWalk(infoManager.getAnimationItemNameWalk());
        modelEntityManager.setAnimationItemNameAttack(infoManager.getAnimationItemNameAttack());
        modelEntityManager.setAnimationCustomModelDataIdle(infoManager.getAnimationCustomModelDataIdle());
        modelEntityManager.setAnimationCustomModelDataWalk(infoManager.getAnimationCustomModelDataWalk());
        modelEntityManager.setAnimationCustomModelDataAttack(infoManager.getAnimationCustomModelDataAttack());
        modelEntityManager.setModelLocationH(infoManager.getModelLocationH());
        modelEntityManager.spawnModel(useModelEngine, true);
        modelEntityManager.setAnimationModelList(infoManager.getAnimationModelList());
        commonModel.add(modelEntityManager);
    }

    public void setAnimationItemNameIdle(String animationItemNameIdle) {
        this.animationItemNameIdle = animationItemNameIdle;
    }

    public void setAnimationItemNameWalk(String animationItemNameWalk) {
        this.animationItemNameWalk = animationItemNameWalk;
    }

    public void setAnimationItemNameAttack(String animationItemNameAttack) {
        this.animationItemNameAttack = animationItemNameAttack;
    }

    public void setAnimationCustomModelDataAttack(int animationCustomModelDataAttack) {
        this.animationCustomModelDataAttack = animationCustomModelDataAttack;
    }

    public void setAnimationCustomModelDataWalk(int animationCustomModelDataWalk) {
        this.animationCustomModelDataWalk = animationCustomModelDataWalk;
    }

    public void setAnimationCustomModelDataIdle(int animationCustomModelDataIdle) {
        this.animationCustomModelDataIdle = animationCustomModelDataIdle;
    }

    public void setModelLocationH(double modelLocationH) {
        this.modelLocationH = modelLocationH;
    }

    public void addModel() {
        String state = getState();
        if (!isUseModelEngine()) {
            if (armorStand == null) return;
            switch (state) {
                case "idle":
                    armorStand.setHelmet(
                            itemModel(this.animationItemNameIdle, this.animationCustomModelDataIdle, idleItemStack));
                    return;
                case "walk":
                    armorStand.setHelmet(
                            itemModel(this.animationItemNameWalk, this.animationCustomModelDataWalk, walkItemStack));
                    return;
                case "attack":
                    armorStand.setHelmet(
                            itemModel(this.animationItemNameAttack, this.animationCustomModelDataAttack, attackItemStack));
                    return;
            }
            String[] states = state.split("-");
            String skillID = states[1];
            String animationID = states[2];
            AnimationModel am = getAnimationModel(skillID);
            SkillAnimationModel sa = am.getSkillAnimationModel(animationID);
            armorStand.setHelmet(sa.getItemStack());
        }else {
            ModelInfoManager im = ModelInfoManager.petModelList.get(this.modelId);
            UUID entityUUID = this.entity.getUniqueId();
            ModeledEntity moe = ModelEngineAPI.getModelManager().getModeledEntity(entityUUID);
            switch (state) {
                case "idle":
//                    if (animationFrameIdle < im.getModelEngineRetimeIdle()) {
                        for (ActiveModel actm : moe.getModels(modelId)) {
                            actm.setState(ModelState.IDLE);
                        }
//                        this.animationFrameIdle = animationFrameIdle+1;
//                    }else {
//                        animationFrameIdle = 0;
//                    }
                    return;
                case "walk":
//                    if (animationFrameWalk < im.getModelEngineRetimeWalk()) {
                        for (ActiveModel actm : moe.getModels(modelId)) {
                            actm.setState(ModelState.WALK);
                        }
//                        this.animationFrameWalk = animationFrameWalk+1;
/*                    }else {
                        animationFrameWalk = 0;
                    }*/
                    return;
                case "attack":
                    if (animationFrameAttack < im.getModelEngineRetimeAttack()) {
                        for (ActiveModel actm : moe.getModels(modelId)) {
                            actm.addState(state, StateProperty.create(animationFrameAttack, 0, 0));
                        }
                        this.animationFrameAttack = animationFrameAttack+1;
                    }else {
                        animationFrameAttack = 0;
                    }
//                    return;
            }
            // 技能动态...
            /*String[] states = state.split("-");
            String skillID = states[1];
            String animationID = states[2];
            AnimationModel am = getAnimationModel(skillID);
            SkillAnimationModel sa = am.getSkillAnimationModel(animationID);
*/
        }
    }

    public AnimationModel getAnimationModel(String skillID) {
        if (this.animationModelList != null) {
            for (AnimationModel am : this.animationModelList) {
                if (am.getSkillModelID().equals(skillID.toLowerCase())) return am;
            }
        }
        return null;
    }

    public List<AnimationModel> getAnimationModelList() {
        return animationModelList;
    }

    public void setAnimationModelList(List<AnimationModel> animationModelList) {
        this.animationModelList = animationModelList;
    }

    private ItemStack itemModel(String itemName, int customModelData, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        if (!ImiPet.loader.getServerInfo().isOldVersion()) {
            itemMeta.setCustomModelData(customModelData);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void removeModel() {
        if (this.armorStand != null) {
            this.armorStand.remove();
        }
//        this.packetEntityModel.hide();
    }

    public static void removeAllModel() {
        if (!modelEntityList.isEmpty()) {
            for (ModelEntityManager modelEntityManager : modelEntityList) {
                BuildPet buildPet2 = modelEntityManager.buildPet;
                if (buildPet2 != null) {
                    if (buildPet2.getHDHologram() != null) {
                        buildPet2.getHDHologram().delete();
                    }
                    if (buildPet2.getTrHologram() != null) {
                        buildPet2.getTrHologram().delete();
                    }
                    buildPet2.removePet();
                    if (!modelEntityManager.isUseModelEngine()) {
                        modelEntityManager.armorStand.remove();
                    }
                }
            }
            modelEntityList.clear();
        }
        if (!commonModel.isEmpty()) {
            for (ModelEntityManager modelEntityManager : commonModel) {
                modelEntityManager.removeModel();
                modelEntityManager.entity.remove();
            }
            commonModel.clear();
        }
    }

    public void setState(String state, boolean isSkill) {
        if (this.state == null) {
            this.state = state;
            return;
        }
        if (isSkill) {
            this.state = state;
            return;
        }
        if (!this.state.startsWith("skill-")) {
            this.state = state;
        }
    }

    public String getState() {
        return this.state;
    }

    public Entity getEntity() {
        return this.entity;
    }

    private void updateRotation() {
        head = new EulerAngle(Math.toRadians(entity.getLocation().getPitch() % 360), Math.toRadians(entity.getLocation().getYaw() % 360), 0);
        if (getState().equals("attack")) return;
        if (preVec != null && !preVec.equals(entity.getLocation().toVector())) {
            body = new EulerAngle(0, Math.toRadians((entity.getLocation().getYaw() + clamp(angleDiff(Math.toRadians(entity.getLocation().getYaw()), getAngle(entity.getLocation().toVector().subtract(preVec))), -50, 50) + 360) % 360), 0);
            preVec.setY(0);
            setState("walk", false);
        } else {
            body = new EulerAngle(0, Math.toRadians((entity.getLocation().getYaw() + clamp(angleDiff(Math.toRadians(entity.getLocation().getYaw()), body.getY()), -50, 50) + 360) % 360), 0);
            setState("idle", false);
        }
    }

    private double getAngle(Vector vec) {
        Location temp = entity.getLocation();
        temp.setDirection(vec);
        return Math.toRadians(temp.getYaw());
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    private double angleDiff(double pivot, double limb) {
        pivot = Math.toDegrees(pivot) > 180 ? Math.toDegrees(pivot) - 360 : Math.toDegrees(pivot);
        limb = Math.toDegrees(limb) > 180 ? Math.toDegrees(limb) - 360 : Math.toDegrees(limb);

        double diff = limb - pivot;
        if (diff > 180) {
            diff -= 360;
        } else if (diff < -180) {
            diff += 360;
        }
        return diff;
    }

    public String getModelId() {
        return this.modelId;
    }

    public int getAnimationFrameMap(String skillAnimationID) {
        return animationFrameMap.get(skillAnimationID);
    }

    public void setAnimationFrameMap(String skillAnimationID, int time) {
        animationFrameMap.put(skillAnimationID, time);
    }

    public boolean existAnimationFrameMap(String skillAnimationID) {
        return animationFrameMap.containsKey(skillAnimationID);
    }

    public void removeAnimationFrameMap(String skillAnimationID) {
        animationFrameMap.remove(skillAnimationID);
    }
}
