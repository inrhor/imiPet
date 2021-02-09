package cn.mcres.imiPet.model;

import cn.mcres.imiPet.pet.file.PetFile;
import cn.mcres.imiPet.skill.model.AnimationModel;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ModelInfoManager {
    private String modelId;

    private boolean useModelEngine;
    private int modelEngineRetimeAttack, modelEngineStopTimeAttack;

    private String levelExpFormula;
    private String addMinAttackFormula;
    private String addMaxAttackFormula;
    private String addFoodFormula;
    private String addMaxHPFormula;
    private String addExpByPlayer;

    private boolean apEnable;
    private List<String> apBasisList, apLevelUpList;

    private boolean buffAttributeEnable;
    private String buffAttributeUse;
    private List<String> buffAttributeAP;

    private String animationItemNameIdle, animationItemNameWalk, animationItemNameAttack;
    private int animationCustomModelDataIdle, animationCustomModelDataWalk, animationCustomModelDataAttack;
    private int animationAttackTime;
    private double modelLocationH;
    private Material animationMaterialIdle, animationMaterialWalk, animationMaterialAttack;

    private double cureRequirementHPMoney;
    private boolean cureRequirementHPPotion;

    private boolean foodEnable;
    private double foodRequirementMoney;

    private boolean evolutionEnable;
    private int evolutionRequirementLevel;
    private double evolutionRequirementMoney;
    private List<String> evolutionText;
    private String evolutionNewModelId;

    private String petDefaultName;
    private String petDefaultNameForMat;
    private boolean showName;
    private String showNameLib;
    private double showNameH;
    private double showNameX;
    private double showNameZ;
    private List<String> showNameFormatList;
    private int HP;
    private double minDamage;
    private double maxDamage;
    private int food;
    private int exp;
    private int maxLevel;

    private boolean rideEnable;
    private String rideIsSmall;
    private String rideCanFly;

    private boolean infoClickEnable;
    private String infoClickLib;
    private int infoClickTime;
    private List<String> infoClickStringList;
    private double infoClickX;
    private double infoClickY;
    private double infoClickZ;

    private boolean eatEnable;
    private List<String> eatList;

    private int vgHomeDrawBigX,vgHomeDrawBigY,vgHomeDrawBigSize;
    private int vgHomeDrawSmallX,vgHomeDrawSmallY,vgHomeDrawSmallAddX,vgHomeDrawSmallSize;

    private int vgExpDrawSmallFirstX,vgExpDrawSmallFirstY,vgExpDrawSmallX,vgExpDrawSmallY,
            vgExpDrawSmallAddX, vgExpDrawSmallSize;

    private int vgTPWDrawSmallX,vgTPWDrawSmallY,vgTPWDrawSmallAddX,vgTPWDrawSmallSize;

    private int vgEvolutionBigX, vgEvolutionBigY, vgEvolutionBigSize;
    private int vgEvolutionSmallFirstX, vgEvolutionSmallFirstY;
    private int vgEvolutionSmallX, vgEvolutionSmallY, vgEvolutionSmallAddX;
    private int vgEvolutionSmallSize, vgEvolutionShowX, vgEvolutionShowY, vgEvolutionShowSize;

    private int vgUpdateInfoBigX, vgUpdateInfoBigY, vgUpdateInfoBigSize,
            vgUpdateInfoSmallFirstX, vgUpdateInfoSmallFirstY, vgUpdateInfoSmallX,
            vgUpdateInfoSmallY, vgUpdateInfoSmallAddX, vgUpdateInfoSmallSize;

    private List<AnimationModel> animationModelList;

    private boolean visible;

    public List<AnimationModel> getAnimationModelList() {
        return animationModelList;
    }

    public void setAnimationModelList(List<AnimationModel> animationModelList) {
        this.animationModelList = animationModelList;
    }

    /**
     * 根据modelId获取指定模型
     */
    public static HashMap<String, ModelInfoManager> petModelList = new LinkedHashMap<>();

    public ModelInfoManager(String modelId) {
        this.modelId = modelId;
    }

    public void register() {
        petModelList.put(this.modelId, this);
        PetFile.modelIdList.add(this.modelId);
    }

    public static void clear() {
        petModelList.clear();
        PetFile.modelIdList.clear();
    }

    public String getLevelExpFormula() {
        return levelExpFormula;
    }

    public double getModelLocationH() {
        return modelLocationH;
    }

    public void setModelLocationH(double modelLocationH) {
        this.modelLocationH = modelLocationH;
    }

    public void setLevelExpFormula(String levelExpFormula) {
        this.levelExpFormula = levelExpFormula;
    }

    public String getAddMinAttackFormula() {
        return addMinAttackFormula;
    }

    public void setAddMinAttackFormula(String addMinAttackFormula) {
        this.addMinAttackFormula = addMinAttackFormula;
    }

    public String getAddMaxAttackFormula() {
        return addMaxAttackFormula;
    }

    public void setAddMaxAttackFormula(String addMaxAttackFormula) {
        this.addMaxAttackFormula = addMaxAttackFormula;
    }

    public String getAddFoodFormula() {
        return addFoodFormula;
    }

    public void setAddFoodFormula(String addFoodFormula) {
        this.addFoodFormula = addFoodFormula;
    }

    public String getAddMaxHPFormula() {
        return addMaxHPFormula;
    }

    public void setAddMaxHPFormula(String addMaxHPFormula) {
        this.addMaxHPFormula = addMaxHPFormula;
    }

    public String getAnimationItemNameIdle() {
        return animationItemNameIdle;
    }

    public void setAnimationItemNameIdle(String animationItemNameIdle) {
        this.animationItemNameIdle = animationItemNameIdle;
    }

    public String getAnimationItemNameWalk() {
        return animationItemNameWalk;
    }

    public void setAnimationItemNameWalk(String animationItemNameWalk) {
        this.animationItemNameWalk = animationItemNameWalk;
    }

    public int getAnimationAttackTime() {
        return animationAttackTime;
    }

    public void setAnimationAttackTime(int animationAttackTime) {
        this.animationAttackTime = animationAttackTime;
    }

    public String getAnimationItemNameAttack() {
        return animationItemNameAttack;
    }

    public void setAnimationItemNameAttack(String animationItemNameAttack) {
        this.animationItemNameAttack = animationItemNameAttack;
    }

    public int getAnimationCustomModelDataIdle() {
        return animationCustomModelDataIdle;
    }

    public void setAnimationCustomModelDataIdle(int animationCustomModelDataIdle) {
        this.animationCustomModelDataIdle = animationCustomModelDataIdle;
    }

    public Material getAnimationMaterialAttack() {
        return animationMaterialAttack;
    }

    public Material getAnimationMaterialIdle() {
        return animationMaterialIdle;
    }

    public Material getAnimationMaterialWalk() {
        return animationMaterialWalk;
    }

    public void setAnimationMaterialAttack(Material animationMaterialAttack) {
        if (animationMaterialAttack != null) {
            this.animationMaterialAttack = animationMaterialAttack;
            return;
        }
        this.animationMaterialAttack = Material.DIAMOND_HOE;
    }

    public void setAnimationMaterialIdle(Material animationMaterialIdle) {
        if (animationMaterialIdle != null) {
            this.animationMaterialIdle = animationMaterialIdle;
            return;
        }
        this.animationMaterialIdle = Material.DIAMOND_HOE;
    }

    public void setAnimationMaterialWalk(Material animationMaterialWalk) {
        if (animationMaterialWalk != null) {
            this.animationMaterialWalk = animationMaterialWalk;
            return;
        }
        this.animationMaterialWalk = Material.DIAMOND_HOE;
    }

    public int getAnimationCustomModelDataWalk() {
        return animationCustomModelDataWalk;
    }

    public void setAnimationCustomModelDataWalk(int animationCustomModelDataWalk) {
        this.animationCustomModelDataWalk = animationCustomModelDataWalk;
    }

    public int getAnimationCustomModelDataAttack() {
        return animationCustomModelDataAttack;
    }

    public void setAnimationCustomModelDataAttack(int animationCustomModelDataAttack) {
        this.animationCustomModelDataAttack = animationCustomModelDataAttack;
    }

    public void setApEnable(boolean apEnable) {
        this.apEnable = apEnable;
    }

    public boolean isApEnable() {
        return apEnable;
    }

    public void setApBasisList(List<String> apBasisList) {
        this.apBasisList = apBasisList;
    }

    public List<String> getApBasisList() {
        return apBasisList;
    }

    public void setApLevelUpList(List<String> apLevelUpList) {
        this.apLevelUpList = apLevelUpList;
    }

    public List<String> getApLevelUpList() {
        return apLevelUpList;
    }

    public void setBuffAttributeEnable(boolean buffAttributeEnable) {
        this.buffAttributeEnable = buffAttributeEnable;
    }

    public boolean isBuffAttributeEnable() {
        return buffAttributeEnable;
    }

    public List<String> getBuffAttributeAP() {
        return buffAttributeAP;
    }

    public void setBuffAttributeAP(List<String> buffAttributeAP) {
        this.buffAttributeAP = buffAttributeAP;
    }

    public void setBuffAttributeUse(String buffAttributeUse) {
        this.buffAttributeUse = buffAttributeUse;
    }

    public String getBuffAttributeUse() {
        return buffAttributeUse;
    }

    public double getCureRequirementHPMoney() {
        return cureRequirementHPMoney;
    }

    public boolean isCureRequirementHPPotion() {
        return cureRequirementHPPotion;
    }

    public boolean isEvolutionEnable() {
        return evolutionEnable;
    }

    public boolean isFoodEnable() {
        return foodEnable;
    }

    public double getFoodRequirementMoney() {
        return foodRequirementMoney;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public double getEvolutionRequirementMoney() {
        return evolutionRequirementMoney;
    }

    public void setCureRequirementHPMoney(double cureRequirementHPMoney) {
        this.cureRequirementHPMoney = cureRequirementHPMoney;
    }

    public void setCureRequirementHPPotion(boolean cureRequirementHPPotion) {
        this.cureRequirementHPPotion = cureRequirementHPPotion;
    }

    public void setFoodEnable(boolean foodEnable) {
        this.foodEnable = foodEnable;
    }

    public int getEvolutionRequirementLevel() {
        return evolutionRequirementLevel;
    }

    public void setFoodRequirementMoney(double foodRequirementMoney) {
        this.foodRequirementMoney = foodRequirementMoney;
    }

    public boolean isEatEnable() {
        return eatEnable;
    }

    public void setEvolutionEnable(boolean evolutionEnable) {
        this.evolutionEnable = evolutionEnable;
    }

    public boolean isInfoClickEnable() {
        return infoClickEnable;
    }

    public List<String> getEvolutionText() {
        return evolutionText;
    }

    public String getRideCanFly() {
        return rideCanFly;
    }

    public boolean isRideEnable() {
        return rideEnable;
    }

    public String getRideIsSmall() {
        return rideIsSmall;
    }

    public void setEvolutionRequirementLevel(int evolutionRequirementLevel) {
        this.evolutionRequirementLevel = evolutionRequirementLevel;
    }

    public boolean isShowName() {
        return showName;
    }

    public double getInfoClickX() {
        return infoClickX;
    }

    public double getInfoClickY() {
        return infoClickY;
    }

    public double getInfoClickZ() {
        return infoClickZ;
    }

    public double getShowNameH() {
        return showNameH;
    }

    public double getShowNameX() {
        return showNameX;
    }

    public double getShowNameZ() {
        return showNameZ;
    }

    public int getExp() {
        return exp;
    }

    public int getFood() {
        return food;
    }

    public int getHP() {
        return HP;
    }

    public int getInfoClickTime() {
        return infoClickTime;
    }

    public double getMaxDamage() {
        return maxDamage;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setEvolutionRequirementMoney(double evolutionRequirementMoney) {
        this.evolutionRequirementMoney = evolutionRequirementMoney;
    }

    public double getMinDamage() {
        return minDamage;
    }

    public List<String> getEatList() {
        return eatList;
    }

    public List<String> getInfoClickStringList() {
        return infoClickStringList;
    }

    public String getEvolutionNewModelId() {
        return evolutionNewModelId;
    }

    public List<String> getShowNameFormatList() {
        return showNameFormatList;
    }

    public String getInfoClickLib() {
        return infoClickLib;
    }

    public String getPetDefaultName() {
        return petDefaultName;
    }

    public String getPetDefaultNameForMat() {
        return petDefaultNameForMat;
    }

    public String getShowNameLib() {
        return showNameLib;
    }

    public void setEatEnable(boolean eatEnable) {
        this.eatEnable = eatEnable;
    }

    public void setEatList(List<String> eatList) {
        this.eatList = eatList;
    }

    public void setEvolutionNewModelId(String evolutionNewModelId) {
        this.evolutionNewModelId = evolutionNewModelId;
    }

    public void setEvolutionText(List<String> evolutionText) {
        this.evolutionText = evolutionText;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setInfoClickEnable(boolean infoClickEnable) {
        this.infoClickEnable = infoClickEnable;
    }

    public void setInfoClickLib(String infoClickLib) {
        this.infoClickLib = infoClickLib;
    }

    public void setInfoClickStringList(List<String> infoClickStringList) {
        this.infoClickStringList = infoClickStringList;
    }

    public void setInfoClickTime(int infoClickTime) {
        this.infoClickTime = infoClickTime;
    }

    public void setInfoClickX(double infoClickX) {
        this.infoClickX = infoClickX;
    }

    public void setInfoClickY(double infoClickY) {
        this.infoClickY = infoClickY;
    }

    public void setInfoClickZ(double infoClickZ) {
        this.infoClickZ = infoClickZ;
    }

    public void setMaxDamage(double maxDamage) {
        this.maxDamage = maxDamage;
    }

    public void setMinDamage(double minDamage) {
        this.minDamage = minDamage;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setPetDefaultName(String petDefaultName) {
        this.petDefaultName = petDefaultName;
    }

    public void setPetDefaultNameForMat(String petDefaultNameForMat) {
        this.petDefaultNameForMat = petDefaultNameForMat;
    }

    public void setRideCanFly(String rideCanFly) {
        this.rideCanFly = rideCanFly;
    }

    public void setRideEnable(boolean rideEnable) {
        this.rideEnable = rideEnable;
    }

    public void setRideIsSmall(String rideIsSmall) {
        this.rideIsSmall = rideIsSmall;
    }

    public void setShowName(boolean showName) {
        this.showName = showName;
    }

    public void setShowNameFormatList(List<String> showNameFormatList) {
        this.showNameFormatList = showNameFormatList;
    }

    public void setShowNameH(double showNameH) {
        this.showNameH = showNameH;
    }

    public void setShowNameLib(String showNameLib) {
        this.showNameLib = showNameLib;
    }

    public void setShowNameX(double showNameX) {
        this.showNameX = showNameX;
    }

    public void setShowNameZ(double showNameZ) {
        this.showNameZ = showNameZ;
    }

    public int getVgEvolutionBigSize() {
        return vgEvolutionBigSize;
    }

    public int getVgEvolutionBigX() {
        return vgEvolutionBigX;
    }

    public int getVgEvolutionBigY() {
        return vgEvolutionBigY;
    }

    public int getVgEvolutionShowSize() {
        return vgEvolutionShowSize;
    }

    public int getVgEvolutionShowX() {
        return vgEvolutionShowX;
    }

    public int getVgEvolutionShowY() {
        return vgEvolutionShowY;
    }

    public int getVgEvolutionSmallAddX() {
        return vgEvolutionSmallAddX;
    }

    public int getVgEvolutionSmallFirstX() {
        return vgEvolutionSmallFirstX;
    }

    public int getVgEvolutionSmallFirstY() {
        return vgEvolutionSmallFirstY;
    }

    public int getVgEvolutionSmallSize() {
        return vgEvolutionSmallSize;
    }

    public int getVgEvolutionSmallX() {
        return vgEvolutionSmallX;
    }

    public int getVgEvolutionSmallY() {
        return vgEvolutionSmallY;
    }

    public int getVgExpDrawSmallAddX() {
        return vgExpDrawSmallAddX;
    }

    public int getVgExpDrawSmallFirstX() {
        return vgExpDrawSmallFirstX;
    }

    public int getVgExpDrawSmallFirstY() {
        return vgExpDrawSmallFirstY;
    }

    public int getVgExpDrawSmallSize() {
        return vgExpDrawSmallSize;
    }

    public int getVgExpDrawSmallX() {
        return vgExpDrawSmallX;
    }

    public int getVgExpDrawSmallY() {
        return vgExpDrawSmallY;
    }

    public int getVgHomeDrawBigSize() {
        return vgHomeDrawBigSize;
    }

    public int getVgHomeDrawBigX() {
        return vgHomeDrawBigX;
    }

    public int getVgHomeDrawBigY() {
        return vgHomeDrawBigY;
    }

    public int getVgHomeDrawSmallSize() {
        return vgHomeDrawSmallSize;
    }

    public int getVgHomeDrawSmallX() {
        return vgHomeDrawSmallX;
    }

    public int getVgHomeDrawSmallY() {
        return vgHomeDrawSmallY;
    }

    public int getVgTPWDrawSmallAddX() {
        return vgTPWDrawSmallAddX;
    }

    public int getVgTPWDrawSmallSize() {
        return vgTPWDrawSmallSize;
    }

    public int getVgTPWDrawSmallX() {
        return vgTPWDrawSmallX;
    }

    public int getVgTPWDrawSmallY() {
        return vgTPWDrawSmallY;
    }

    public int getVgUpdateInfoBigSize() {
        return vgUpdateInfoBigSize;
    }

    public int getVgUpdateInfoBigX() {
        return vgUpdateInfoBigX;
    }

    public int getVgUpdateInfoBigY() {
        return vgUpdateInfoBigY;
    }

    public int getVgUpdateInfoSmallAddX() {
        return vgUpdateInfoSmallAddX;
    }

    public int getVgUpdateInfoSmallFirstX() {
        return vgUpdateInfoSmallFirstX;
    }

    public int getVgUpdateInfoSmallFirstY() {
        return vgUpdateInfoSmallFirstY;
    }

    public int getVgUpdateInfoSmallSize() {
        return vgUpdateInfoSmallSize;
    }

    public int getVgUpdateInfoSmallX() {
        return vgUpdateInfoSmallX;
    }

    public int getVgUpdateInfoSmallY() {
        return vgUpdateInfoSmallY;
    }

    public void setVgEvolutionBigSize(int vgEvolutionBigSize) {
        this.vgEvolutionBigSize = vgEvolutionBigSize;
    }

    public void setVgEvolutionBigX(int vgEvolutionBigX) {
        this.vgEvolutionBigX = vgEvolutionBigX;
    }

    public void setVgEvolutionBigY(int vgEvolutionBigY) {
        this.vgEvolutionBigY = vgEvolutionBigY;
    }

    public void setVgEvolutionShowSize(int vgEvolutionShowSize) {
        this.vgEvolutionShowSize = vgEvolutionShowSize;
    }

    public void setVgUpdateInfoBigSize(int vgUpdateInfoBigSize) {
        this.vgUpdateInfoBigSize = vgUpdateInfoBigSize;
    }

    public void setVgEvolutionShowX(int vgEvolutionShowX) {
        this.vgEvolutionShowX = vgEvolutionShowX;
    }

    public void setVgEvolutionShowY(int vgEvolutionShowY) {
        this.vgEvolutionShowY = vgEvolutionShowY;
    }

    public void setVgEvolutionSmallAddX(int vgEvolutionSmallAddX) {
        this.vgEvolutionSmallAddX = vgEvolutionSmallAddX;
    }

    public void setVgEvolutionSmallFirstX(int vgEvolutionSmallFirstX) {
        this.vgEvolutionSmallFirstX = vgEvolutionSmallFirstX;
    }

    public void setVgEvolutionSmallFirstY(int vgEvolutionSmallFirstY) {
        this.vgEvolutionSmallFirstY = vgEvolutionSmallFirstY;
    }

    public void setVgEvolutionSmallSize(int vgEvolutionSmallSize) {
        this.vgEvolutionSmallSize = vgEvolutionSmallSize;
    }

    public void setVgEvolutionSmallX(int vgEvolutionSmallX) {
        this.vgEvolutionSmallX = vgEvolutionSmallX;
    }

    public void setVgEvolutionSmallY(int vgEvolutionSmallY) {
        this.vgEvolutionSmallY = vgEvolutionSmallY;
    }

    public void setVgExpDrawSmallAddX(int vgExpDrawSmallAddX) {
        this.vgExpDrawSmallAddX = vgExpDrawSmallAddX;
    }

    public void setVgExpDrawSmallFirstX(int vgExpDrawSmallFirstX) {
        this.vgExpDrawSmallFirstX = vgExpDrawSmallFirstX;
    }

    public void setVgExpDrawSmallFirstY(int vgExpDrawSmallFirstY) {
        this.vgExpDrawSmallFirstY = vgExpDrawSmallFirstY;
    }

    public void setVgExpDrawSmallSize(int vgExpDrawSmallSize) {
        this.vgExpDrawSmallSize = vgExpDrawSmallSize;
    }

    public void setVgExpDrawSmallX(int vgExpDrawSmallX) {
        this.vgExpDrawSmallX = vgExpDrawSmallX;
    }

    public void setVgExpDrawSmallY(int vgExpDrawSmallY) {
        this.vgExpDrawSmallY = vgExpDrawSmallY;
    }

    public void setVgHomeDrawBigSize(int vgHomeDrawBigSize) {
        this.vgHomeDrawBigSize = vgHomeDrawBigSize;
    }

    public void setVgHomeDrawBigX(int vgHomeDrawBigX) {
        this.vgHomeDrawBigX = vgHomeDrawBigX;
    }

    public void setVgHomeDrawBigY(int vgHomeDrawBigY) {
        this.vgHomeDrawBigY = vgHomeDrawBigY;
    }

    public void setVgHomeDrawSmallSize(int vgHomeDrawSmallSize) {
        this.vgHomeDrawSmallSize = vgHomeDrawSmallSize;
    }

    public void setVgHomeDrawSmallX(int vgHomeDrawSmallX) {
        this.vgHomeDrawSmallX = vgHomeDrawSmallX;
    }

    public void setVgHomeDrawSmallY(int vgHomeDrawSmallY) {
        this.vgHomeDrawSmallY = vgHomeDrawSmallY;
    }

    public void setVgTPWDrawSmallAddX(int vgTPWDrawSmallAddX) {
        this.vgTPWDrawSmallAddX = vgTPWDrawSmallAddX;
    }

    public void setVgTPWDrawSmallSize(int vgTPWDrawSmallSize) {
        this.vgTPWDrawSmallSize = vgTPWDrawSmallSize;
    }

    public void setVgTPWDrawSmallX(int vgTPWDrawSmallX) {
        this.vgTPWDrawSmallX = vgTPWDrawSmallX;
    }

    public void setVgTPWDrawSmallY(int vgTPWDrawSmallY) {
        this.vgTPWDrawSmallY = vgTPWDrawSmallY;
    }

    public void setVgUpdateInfoBigX(int vgUpdateInfoBigX) {
        this.vgUpdateInfoBigX = vgUpdateInfoBigX;
    }

    public void setVgUpdateInfoBigY(int vgUpdateInfoBigY) {
        this.vgUpdateInfoBigY = vgUpdateInfoBigY;
    }

    public void setVgUpdateInfoSmallAddX(int vgUpdateInfoSmallAddX) {
        this.vgUpdateInfoSmallAddX = vgUpdateInfoSmallAddX;
    }

    public void setVgUpdateInfoSmallFirstX(int vgUpdateInfoSmallFirstX) {
        this.vgUpdateInfoSmallFirstX = vgUpdateInfoSmallFirstX;
    }

    public void setVgUpdateInfoSmallFirstY(int vgUpdateInfoSmallFirstY) {
        this.vgUpdateInfoSmallFirstY = vgUpdateInfoSmallFirstY;
    }

    public void setVgUpdateInfoSmallSize(int vgUpdateInfoSmallSize) {
        this.vgUpdateInfoSmallSize = vgUpdateInfoSmallSize;
    }

    public void setVgUpdateInfoSmallX(int vgUpdateInfoSmallX) {
        this.vgUpdateInfoSmallX = vgUpdateInfoSmallX;
    }

    public void setVgUpdateInfoSmallY(int vgUpdateInfoSmallY) {
        this.vgUpdateInfoSmallY = vgUpdateInfoSmallY;
    }

    public int getVgHomeDrawSmallAddX() {
        return vgHomeDrawSmallAddX;
    }

    public void setVgHomeDrawSmallAddX(int vgHomeDrawSmallAddX) {
        this.vgHomeDrawSmallAddX = vgHomeDrawSmallAddX;
    }

    public String getAddExpByPlayer() {
        return addExpByPlayer;
    }

    public void setAddExpByPlayer(String addExpByPlayer) {
        this.addExpByPlayer = addExpByPlayer;
    }

    public boolean isUseModelEngine() {
        return useModelEngine;
    }

    public void setUseModelEngine(boolean useModelEngine) {
        this.useModelEngine = useModelEngine;
    }

    public int getModelEngineRetimeAttack() {
        return modelEngineRetimeAttack;
    }

    public void setModelEngineRetimeAttack(int modelEngineRetimeAttack) {
        this.modelEngineRetimeAttack = modelEngineRetimeAttack;
    }

    public int getModelEngineStopTimeAttack() {
        return modelEngineStopTimeAttack;
    }

    public void setModelEngineStopTimeAttack(int modelEngineStopTimeAttack) {
        this.modelEngineStopTimeAttack = modelEngineStopTimeAttack;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
