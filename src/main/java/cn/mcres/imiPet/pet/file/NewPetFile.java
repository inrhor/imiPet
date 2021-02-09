package cn.mcres.imiPet.pet.file;

import cn.inrhor.imipet.ImiPet;
import cn.inrhor.imipet.pack.PackFile;
import cn.inrhor.imipet.utlis.MsgUtil;
import cn.mcres.imiPet.build.files.CreateFile;
import cn.mcres.imiPet.build.utils.LogUtil;
import cn.mcres.imiPet.instal.lib.ModelLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.server.ServerInfo;
import cn.mcres.imiPet.skill.model.AnimationModel;
import cn.mcres.imiPet.skill.model.SkillAnimationModel;
import java.util.ArrayList;
import java.util.List;

import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NewPetFile {
    public static ServerInfo getServerInfo() {
        return ImiPet.loader.getServerInfo();
    }

    private static CreateFile modelsFile = new CreateFile("models");

    public void run() {
        modelsFile.checkAndCreate();
    }

    public static void addCustomModel() {
        Bukkit.getScheduler().runTaskAsynchronously(ImiPet.loader.getPlugin(), () -> {

            // models文件夹
            for (File file : Objects.requireNonNull(modelsFile.getFile().listFiles())) {

                if (!file.isDirectory()) continue;

                // models的子文件夹
                File modelFile = file(file, "model.yml");
                if (!modelFile.exists()) continue;
                FileConfiguration modelYaml = yaml(modelFile);

                File formulaFile = file(file, "formula.yml");
                if (!formulaFile.exists()) continue;
                FileConfiguration formulaYaml = yaml(formulaFile);

                File basisFile = file(file, "basis.yml");
                if (!basisFile.exists()) continue;
                FileConfiguration basisYaml = yaml(basisFile);

                File featureFile = file(file, "feature.yml");
                if (!featureFile.exists()) continue;
                FileConfiguration featureYaml = yaml(featureFile);

                File evolutionFile = file(file, "evolution.yml");
                if (!evolutionFile.exists()) continue;
                FileConfiguration evolutionYaml = yaml(evolutionFile);

                File interactionFile = file(file, "interaction.yml");
                if (!interactionFile.exists()) continue;
                FileConfiguration interactionYaml = yaml(interactionFile);

                File eatFile = file(file, "eat.yml");
                if (!eatFile.exists()) continue;
                FileConfiguration eatYaml = yaml(eatFile);

                String petFileName = file.getName();

                if (!modelYaml.contains("modelId")) {
                    LogUtil.send("§c检查到 §f" + petFileName + "§c 的 §f modelId §c值为空，已终止注册该模型");
                    continue;
                }
                String modelId = modelYaml.getString("modelId");

                if (ModelInfoManager.petModelList.get(modelId) != null) {
                    LogUtil.send("§c检查到 §f" + petFileName + "§c 已被注册过");
                    continue;
                }

                // 判断公式值存在与否
                if (!formulaYaml.contains("addExpByPlayer")) {
                    LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f addExpByPlayer §c值为空，已终止注册该模型");
                    continue;
                }
                if (!formulaYaml.contains("levelExpFormula")) {
                    LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f levelExpFormula §c值为空，已终止注册该模型");
                    continue;
                }
                if (!formulaYaml.contains("addMaxAttackFormula")) {
                    LogUtil.send("§c检查到 §f" + petFileName + "§c 的 §f addMaxAttackFormula §c值为空，已终止注册该模型");
                    continue;
                }
                if (!formulaYaml.contains("addMinAttackFormula")) {
                    LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f addMinAttackFormula §c值为空，已终止注册该模型");
                    continue;
                }
                if (!formulaYaml.contains("addFoodFormula")) {
                    LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f addFoodFormula §c值为空，已终止注册该模型");
                    continue;
                }
                if (!formulaYaml.contains("addMaxHPFormula")) {
                    LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f addMaxHPFormula §c值为空，已终止注册该模型");
                    continue;
                }

                ModelInfoManager modelInfo = new ModelInfoManager(modelId);

                Material dioHoeIdle = Material.DIAMOND_HOE;
                if (!modelYaml.getBoolean("modelEngine.use")) {
                    // 判断动态模型值存在与否
                    String animationIdleItemName = "animation.idle.itemName";
                    String animationWalkItemName = "animation.walk.itemName";
                    String animationAttackItemName = "animation.attack.itemName";
                    String animationIdleCustomModelData = "animation.idle.customModelData";
                    String animationWalkCustomModelData = "animation.walk.customModelData";
                    String animationAttackCustomModelData = "animation.attack.customModelData";
                    String animationIdleMaterial = "animation.idle.material";
                    String animationWalkMaterial = "animation.walk.material";
                    String animationAttackMaterial = "animation.attack.material";
                    String animationAttackTime = "animation.attack.time";
                    if (!modelYaml.contains(animationIdleItemName)) {
                        LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f " + animationIdleItemName + " §c值为空，已终止注册该模型");
                        continue;
                    }
                    if (!modelYaml.contains(animationIdleCustomModelData)) {
                        LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f " + animationIdleCustomModelData + " §c值为空，已终止注册该模型");
                        continue;
                    }
                    if (!modelYaml.contains(animationWalkItemName)) {
                        LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f " + animationWalkItemName + " §c值为空，已终止注册该模型");
                        continue;
                    }
                    if (!modelYaml.contains(animationWalkCustomModelData)) {
                        LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f " + animationWalkCustomModelData + " §c值为空，已终止注册该模型");
                        continue;
                    }
                    if (!modelYaml.contains(animationAttackItemName)) {
                        LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f " + animationAttackItemName + " §c值为空，已终止注册该模型");
                        continue;
                    }
                    if (!modelYaml.contains(animationAttackCustomModelData)) {
                        LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f " + animationAttackCustomModelData + " §c值为空，已终止注册该模型");
                        continue;
                    }
                    if (!modelYaml.contains(animationAttackTime)) {
                        LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f " + animationAttackTime + " §c值为空，已终止注册该模型");
                        continue;
                    }
                    Material dioHoeWalk = Material.DIAMOND_HOE;
                    Material dioHoeAttack = Material.DIAMOND_HOE;
                    if (modelYaml.contains(animationIdleMaterial)) {
                        dioHoeIdle = Material.valueOf(modelYaml.getString(animationIdleMaterial));
                    }
                    if (modelYaml.contains(animationWalkMaterial)) {
                        dioHoeWalk = Material.valueOf(modelYaml.getString(animationWalkMaterial));
                    }
                    if (modelYaml.contains(animationAttackMaterial)) {
                        dioHoeAttack = Material.valueOf(modelYaml.getString(animationAttackMaterial));
                    }


                    modelInfo.setAnimationItemNameIdle(modelYaml.getString(animationIdleItemName));
                    modelInfo.setAnimationCustomModelDataIdle(modelYaml.getInt(animationIdleCustomModelData));
                    modelInfo.setAnimationMaterialIdle(dioHoeIdle);
                    modelInfo.setAnimationItemNameWalk(modelYaml.getString(animationWalkItemName));
                    modelInfo.setAnimationCustomModelDataWalk(modelYaml.getInt(animationWalkCustomModelData));
                    modelInfo.setAnimationMaterialWalk(dioHoeWalk);
                    modelInfo.setAnimationItemNameAttack(modelYaml.getString(animationAttackItemName));
                    modelInfo.setAnimationCustomModelDataAttack(modelYaml.getInt(animationAttackCustomModelData));
                    modelInfo.setAnimationMaterialAttack(dioHoeAttack);
                    modelInfo.setAnimationAttackTime(modelYaml.getInt(animationAttackTime));

                    modelInfo.setModelLocationH(modelYaml.getDouble("animation.location.h"));
                }else {
                    if (ModelLib.ModelLibEnable) {
                        modelInfo.setUseModelEngine(true);

                        String path = "modelEngine.animation.";
                        modelInfo.setModelEngineRetimeAttack(modelYaml.getInt(path + "attack.retime"));
                        modelInfo.setModelEngineStopTimeAttack(modelYaml.getInt(path + "attack.stopTime"));
                        modelInfo.setAnimationItemNameIdle(modelYaml.getString(path + "gui.itemName"));
                        modelInfo.setAnimationCustomModelDataIdle(modelYaml.getInt(path + "gui.customModelData"));
                        dioHoeIdle = Material.valueOf(modelYaml.getString(path + "gui.material"));
                        modelInfo.setAnimationMaterialIdle(dioHoeIdle);

                    }else {
                        LogUtil.send("§c检查到 §f" + petFileName + "§c 的§f 启用ModelEngine但没安装前置插件，已终止注册该模型");
                        continue;
                    }

                }

                modelInfo.setVisible(modelYaml.getBoolean("entity.visible"));

                modelInfo.setAddExpByPlayer(formulaYaml.getString("addExpByPlayer"));
                modelInfo.setLevelExpFormula(formulaYaml.getString("levelExpFormula"));
                modelInfo.setAddMaxAttackFormula(formulaYaml.getString("addMaxAttackFormula"));
                modelInfo.setAddMinAttackFormula(formulaYaml.getString("addMinAttackFormula"));
                modelInfo.setAddFoodFormula(formulaYaml.getString("addFoodFormula"));
                modelInfo.setAddMaxHPFormula(formulaYaml.getString("addMaxHPFormula"));

                modelInfo.setCureRequirementHPMoney(featureYaml.getDouble("food.requirement.money"));
                modelInfo.setCureRequirementHPPotion(featureYaml.getBoolean("food.requirement.potion"));

                modelInfo.setFoodEnable(featureYaml.getBoolean("food.enable"));
                modelInfo.setFoodRequirementMoney(featureYaml.getDouble("food.requirement.money"));

                modelInfo.setEvolutionEnable(evolutionYaml.getBoolean("evolution.enable"));
                modelInfo.setEvolutionRequirementLevel(evolutionYaml.getInt("evolution.requirement.level"));
                modelInfo.setEvolutionRequirementMoney(evolutionYaml.getDouble("evolution.requirement.money"));
                modelInfo.setEvolutionText(evolutionYaml.getStringList("evolution.text"));
                modelInfo.setEvolutionNewModelId(evolutionYaml.getString("evolution.newModelId"));

                String basisDisplay = "basis.display.";
                modelInfo.setPetDefaultName(basisYaml.getString(basisDisplay+"name"));
                modelInfo.setPetDefaultNameForMat(basisYaml.getString(basisDisplay+"format"));
                modelInfo.setShowName(basisYaml.getBoolean(basisDisplay+"show"));
                modelInfo.setShowNameLib(basisYaml.getString(basisDisplay+"useShow"));
                if (modelInfo.isShowName()) {
                    if (modelInfo.getShowNameLib().equals("tr")) {
                        PublicUse.setShowName(modelInfo, basisYaml, "tr");
                    } else if (modelInfo.getShowNameLib().equals("cmi")) {
                        PublicUse.setShowName(modelInfo, basisYaml, "cmi");
                    } else {
                        PublicUse.setShowName(modelInfo, basisYaml, "hd");
                    }
                }

                modelInfo.setHP(basisYaml.getInt("basis.HP"));
                modelInfo.setMinDamage(basisYaml.getDouble("basis.minDamage"));
                modelInfo.setMaxDamage(basisYaml.getDouble("basis.maxDamage"));
                modelInfo.setFood(basisYaml.getInt("basis.food"));
                modelInfo.setExp(basisYaml.getInt("basis.exp"));
                modelInfo.setMaxLevel(basisYaml.getInt("basis.level"));

                File attributeFile = file(file, "attribute.yml");
                FileConfiguration attributeYaml = yaml(attributeFile);

                File buffFile = file(file, "buff.yml");
                FileConfiguration buffYaml = yaml(buffFile);

                modelInfo.setApEnable(attributeYaml.getBoolean("dependAttribute.ap.enable"));
                if (modelInfo.isApEnable()) {
                    modelInfo.setApBasisList(attributeYaml.getStringList("dependAttribute.ap.basis"));
                    modelInfo.setApLevelUpList(attributeYaml.getStringList("dependAttribute.ap.levelUp"));
                }

                modelInfo.setBuffAttributeEnable(buffYaml.getBoolean("buff.attribute.enable"));
                if (modelInfo.isBuffAttributeEnable()) {
                    modelInfo.setBuffAttributeUse(buffYaml.getString("buff.attribute.use"));
                    modelInfo.setBuffAttributeAP(buffYaml.getStringList("buff.attribute.ap"));
                }

                modelInfo.setRideEnable(featureYaml.getBoolean("ride.enable"));
                modelInfo.setRideIsSmall(featureYaml.getString("ride.isSmall"));
                modelInfo.setRideCanFly(featureYaml.getString("ride.canFly"));

                modelInfo.setInfoClickEnable(interactionYaml.getBoolean("interaction.info.enable"));
                modelInfo.setInfoClickLib(interactionYaml.getString("interaction.info.show"));
                if (modelInfo.isInfoClickEnable()) {
                    if (modelInfo.getInfoClickLib().equals("tr")) {
                        PublicUse.setClickIntoShow(modelInfo, interactionYaml, "tr");
                    }else if (modelInfo.getInfoClickLib().equals("cmi")) {
                        PublicUse.setClickIntoShow(modelInfo, interactionYaml, "cmi");
                    }else {
                        PublicUse.setClickIntoShow(modelInfo, interactionYaml, "hd");
                    }
                }

                modelInfo.setEatEnable(eatYaml.getBoolean("eat.enable"));
                if (modelInfo.isEatEnable()) {
                    modelInfo.setEatList(eatYaml.getStringList("eat.list"));
                }

                File vexviewFile = file(file, "vexview.yml");
                if (vexviewFile.exists()) {
                    FileConfiguration vexviewYaml = yaml(vexviewFile);

                    modelInfo.setVgHomeDrawBigX(vexviewYaml.getInt("entityDraw.vgHome.big.x"));
                    modelInfo.setVgHomeDrawBigY(vexviewYaml.getInt("entityDraw.vgHome.big.y"));
                    modelInfo.setVgHomeDrawBigSize(vexviewYaml.getInt("entityDraw.vgHome.big.size"));
                    modelInfo.setVgHomeDrawSmallX(vexviewYaml.getInt("entityDraw.vgHome.small.x"));
                    modelInfo.setVgHomeDrawSmallY(vexviewYaml.getInt("entityDraw.vgHome.small.y"));
                    modelInfo.setVgHomeDrawSmallAddX(vexviewYaml.getInt("entityDraw.vgHome.small.addX"));
                    modelInfo.setVgHomeDrawSmallSize(vexviewYaml.getInt("entityDraw.vgHome.small.size"));

                    modelInfo.setVgTPWDrawSmallX(vexviewYaml.getInt("entityDraw.vgTransferPackWarehouse.small.x"));
                    modelInfo.setVgTPWDrawSmallY(vexviewYaml.getInt("entityDraw.vgTransferPackWarehouse.small.y"));
                    modelInfo.setVgTPWDrawSmallAddX(vexviewYaml.getInt("entityDraw.vgTransferPackWarehouse.small.addX"));
                    modelInfo.setVgTPWDrawSmallSize(vexviewYaml.getInt("entityDraw.vgTransferPackWarehouse.small.size"));

                    modelInfo.setVgEvolutionBigX(vexviewYaml.getInt("entityDraw.vgEvolution.big.x"));
                    modelInfo.setVgEvolutionBigY(vexviewYaml.getInt("entityDraw.vgEvolution.big.y"));
                    modelInfo.setVgEvolutionBigSize(vexviewYaml.getInt("entityDraw.vgEvolution.big.size"));
                    modelInfo.setVgEvolutionSmallFirstX(vexviewYaml.getInt("entityDraw.vgEvolution.small.firstX"));
                    modelInfo.setVgEvolutionSmallFirstY(vexviewYaml.getInt("entityDraw.vgEvolution.small.firstY"));
                    modelInfo.setVgEvolutionSmallX(vexviewYaml.getInt("entityDraw.vgEvolution.small.x"));
                    modelInfo.setVgEvolutionSmallY(vexviewYaml.getInt("entityDraw.vgEvolution.small.y"));
                    modelInfo.setVgEvolutionSmallAddX(vexviewYaml.getInt("entityDraw.vgEvolution.small.addX"));
                    modelInfo.setVgEvolutionSmallSize(vexviewYaml.getInt("entityDraw.vgEvolution.small.size"));
                    modelInfo.setVgEvolutionShowX(vexviewYaml.getInt("entityDraw.vgEvolution.show.x"));
                    modelInfo.setVgEvolutionShowY(vexviewYaml.getInt("entityDraw.vgEvolution.show.y"));
                    modelInfo.setVgEvolutionShowSize(vexviewYaml.getInt("entityDraw.vgEvolution.show.size"));

                    modelInfo.setVgExpDrawSmallFirstX(vexviewYaml.getInt("entityDraw.vgExp.small.firstX"));
                    modelInfo.setVgExpDrawSmallFirstY(vexviewYaml.getInt("entityDraw.vgExp.small.firstY"));
                    modelInfo.setVgExpDrawSmallX(vexviewYaml.getInt("entityDraw.vgExp.small.x"));
                    modelInfo.setVgExpDrawSmallY(vexviewYaml.getInt("entityDraw.vgExp.small.y"));
                    modelInfo.setVgExpDrawSmallAddX(vexviewYaml.getInt("entityDraw.vgExp.small.addX"));
                    modelInfo.setVgExpDrawSmallSize(vexviewYaml.getInt("entityDraw.vgExp.small.size"));

                    modelInfo.setVgUpdateInfoBigX(vexviewYaml.getInt("entityDraw.vgUpdateInfo.big.x"));
                    modelInfo.setVgUpdateInfoBigY(vexviewYaml.getInt("entityDraw.vgUpdateInfo.big.y"));
                    modelInfo.setVgUpdateInfoBigSize(vexviewYaml.getInt("entityDraw.vgUpdateInfo.big.size"));
                    modelInfo.setVgUpdateInfoSmallFirstX(vexviewYaml.getInt("entityDraw.vgUpdateInfo.small.firstX"));
                    modelInfo.setVgUpdateInfoSmallFirstY(vexviewYaml.getInt("entityDraw.vgUpdateInfo.small.firstY"));
                    modelInfo.setVgUpdateInfoSmallX(vexviewYaml.getInt("entityDraw.vgUpdateInfo.small.x"));
                    modelInfo.setVgUpdateInfoSmallY(vexviewYaml.getInt("entityDraw.vgUpdateInfo.small.y"));
                    modelInfo.setVgUpdateInfoSmallAddX(vexviewYaml.getInt("entityDraw.vgUpdateInfo.small.addX"));
                    modelInfo.setVgUpdateInfoSmallSize(vexviewYaml.getInt("entityDraw.vgUpdateInfo.small.size"));
                }

                List<AnimationModel> animationModelList = new ArrayList<>();
                File skillModelFile = file(file, "skill.yml");
                if (skillModelFile.exists()) {
                    FileConfiguration skillModelYaml = yaml(skillModelFile);
                    for (String skillID : skillModelYaml.getConfigurationSection("skillModel").getKeys(false)) {
                        AnimationModel am = new AnimationModel(modelId, skillID);
                        String path = "skillModel."+skillID+".";
                        am.setSkillName(skillModelYaml.getString(path+"name"));
                        am.setCoolDown(skillModelYaml.getInt(path+"coolDown"));
                        am.setCanMove(skillModelYaml.getBoolean(path+"canMove"));
                        am.setTriggerList(skillModelYaml.getStringList(path+"triggerList"));
                        List<SkillAnimationModel> skillAnimationModels = new ArrayList<>();
                        for (String animationID : skillModelYaml.getConfigurationSection("skillModel."+skillID+".animation").getKeys(false)) {
                            String path2 = path+"animation."+animationID+".";
                            SkillAnimationModel sa = new SkillAnimationModel(modelId, animationID);
                            boolean isUseModelEngine = skillModelYaml.getBoolean(path2+"useModelEngine");
                            if (!isUseModelEngine) {
                                String itemName = skillModelYaml.getString(path2 + "itemName");
                                String material = skillModelYaml.getString(path2 + "material");
                                ItemStack itemStack = new ItemStack(Material.valueOf(material));
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.setDisplayName(itemName);
                                if (!getServerInfo().isOldVersion()) {
                                    int customModelData = skillModelYaml.getInt(path2 + "customModelData");
                                    itemMeta.setCustomModelData(customModelData);
                                }
                                itemStack.setItemMeta(itemMeta);
                                sa.setItemStack(itemStack);
                            }else {
                                sa.setUseModelEngine(true);
                            }
                            int time = skillModelYaml.getInt(path2+"time");
                            String nextAnimationID = skillModelYaml.getString(path2+"nextAnimationID");
                            sa.setTime(time);
                            sa.setNextAnimationID(nextAnimationID);
                            skillAnimationModels.add(sa);
                        }
                        am.setAnimationModels(skillAnimationModels);
                        animationModelList.add(am);
                    }
                }
                modelInfo.setAnimationModelList(animationModelList);

                new PackFile().run(file, modelInfo);

                modelInfo.register();
            }

        });
    }

    private static FileConfiguration yaml(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    private static File file(File file, String yaml) {
        return (new File(file.getPath() + File.separator + yaml));
    }
}
