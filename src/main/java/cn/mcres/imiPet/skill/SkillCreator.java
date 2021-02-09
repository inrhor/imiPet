package cn.mcres.imiPet.skill;

import cn.mcres.imiPet.pet.BuildPet;

import java.util.List;

public class SkillCreator {
    private String skillID, startAnimation;
    private List<SkillAnimationCreator> animationList;
    private BuildPet buildPet;

    public SkillCreator(String skillID, String startAnimation, List<SkillAnimationCreator> animationList) {
        this.skillID = skillID;
        this.startAnimation = startAnimation;
        this.animationList = animationList;
    }

    public SkillAnimationCreator getAnimationCreator(String animationID) {
        for (SkillAnimationCreator skillAnimationCreator : this.animationList) {
            if (skillAnimationCreator.getAnimationID().equals(animationID)) return skillAnimationCreator;
        }
        return null;
    }

    public boolean existAnimationCreator(String animationID) {
        for (SkillAnimationCreator skillAnimationCreator : this.animationList) {
            if (skillAnimationCreator.getAnimationID().equals(animationID)) return true;
        }
        return false;
    }

    public String getSkillID() {
        return skillID;
    }

    public void setSkillID(String skillID) {
        this.skillID = skillID;
    }

    public String getStartAnimation() {
        return startAnimation;
    }

    public List<SkillAnimationCreator> getAnimationList() {
        return animationList;
    }

    public void setAnimationList(List<SkillAnimationCreator> animationList) {
        this.animationList = animationList;
    }

    public void setStartAnimation(String startAnimation) {
        this.startAnimation = startAnimation;
    }

    public BuildPet getBuildPet() {
        return buildPet;
    }

    public void setBuildPet(BuildPet buildPet) {
        this.buildPet = buildPet;
    }
}
