package cn.mcres.imiPet.skill.model;

import org.bukkit.inventory.ItemStack;

public class SkillAnimationModel {

    private String modelID, animationID, nextAnimationID;
    private ItemStack itemStack;
    private int time;
    private boolean useModelEngine;

    public SkillAnimationModel(String modelID, String animationID) {
        this.modelID = modelID;
        this.animationID = animationID;
    }

    public String getModelID() {
        return modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public String getAnimationID() {
        return animationID;
    }

    public void setAnimationID(String animationID) {
        this.animationID = animationID;
    }

    public String getNextAnimationID() {
        return nextAnimationID;
    }

    public void setNextAnimationID(String nextAnimationID) {
        this.nextAnimationID = nextAnimationID;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time*20; // 加上 *20
    }

    public boolean isUseModelEngine() {
        return useModelEngine;
    }

    public void setUseModelEngine(boolean useModelEngine) {
        this.useModelEngine = useModelEngine;
    }
}
