package cn.mcres.imiPet.skill;

import cn.inrhor.imipet.ImiPet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public class SkillAnimationCreator {
    public static JavaPlugin getPlugin() {
        return ImiPet.loader.getPlugin();
    }

    private String animationID, type, nextAnimationID;

    private ItemStack itemStack;
    private boolean gravityItem;
    private String castLocationItem, scriptItem;
    private int time, timeConditionItem;
    private String conditionItem;
    private List<String> buffScriptItem, mythicMobsSkills;
    private String modelId, state;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public SkillAnimationCreator(String animationID) {
        this.animationID = animationID;
    }

    public String getAnimationID() {
        return animationID;
    }

    public int getTimeConditionItem() {
        return timeConditionItem;
    }

    public void setTimeConditionItem(int timeConditionItem) {
        this.timeConditionItem = timeConditionItem *20;
    }

    public String getType() {
        return type;
    }

    public String getNextAnimationID() {
        return nextAnimationID;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isGravityItem() {
        return gravityItem;
    }

    public String getCastLocationItem() {
        return castLocationItem;
    }

    public List<String> getMythicMobsSkills() {
        return mythicMobsSkills;
    }

    public void setMythicMobsSkills(List<String> mythicMobsSkills) {
        this.mythicMobsSkills = mythicMobsSkills;
    }

    public String getScriptItem() {
        return scriptItem;
    }

    public int getTime() {
        return time;
    }

    public String getConditionItem() {
        return conditionItem;
    }

    public void setAnimationID(String animationID) {
        this.animationID = animationID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNextAnimationID(String nextAnimationID) {
        this.nextAnimationID = nextAnimationID;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setGravityItem(boolean gravityItem) {
        this.gravityItem = gravityItem;
    }

    public void setCastLocationItem(String castLocationItem) {
        this.castLocationItem = castLocationItem;
    }

    public void setScriptItem(String scriptItem) {
        this.scriptItem = scriptItem;
    }

    public void setTime(int time) {
        this.time = time*20;
    }

    public void setConditionItem(String conditionItem) {
        this.conditionItem = conditionItem;
    }

    public List<String> getBuffScriptItem() {
        return buffScriptItem;
    }

    public void setBuffScriptItem(List<String> buffScriptItem) {
        this.buffScriptItem = buffScriptItem;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
