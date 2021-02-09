package cn.mcres.imiPet.skill.model;

import java.util.List;

public class AnimationModel {
    private String modelId, skillModelID, skillName;
    private int coolDown;
    private boolean canMove;
    private List<String> triggerList;
    private List<SkillAnimationModel> animationModels;

    public AnimationModel(String modelId, String skillModelID) {
        this.modelId = modelId;
        this.skillModelID = skillModelID.toLowerCase();
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName.replace("&", "§");
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getSkillModelID() {
        return skillModelID;
    }

    public void setSkillModelID(String skillModelID) {
        this.skillModelID = skillModelID;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown*20;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public List<String> getTriggerList() {
        return triggerList;
    }

    public void setTriggerList(List<String> triggerList) {
        this.triggerList = triggerList;
    }

    public List<SkillAnimationModel> getAnimationModels() {
        return animationModels;
    }

    public void setAnimationModels(List<SkillAnimationModel> animationModels) {
        this.animationModels = animationModels;
    }

    public SkillAnimationModel getSkillAnimationModel(String animationID) {
        for (SkillAnimationModel sa : this.animationModels) {
            if (sa.getAnimationID().equals(animationID)) return sa;
        }
        return null;
    }
}
