package cn.mcres.imiPet.api.skill;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import java.util.List;
import java.util.UUID;

public class SkillCastGui {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 卸载技能
     * @param playerUUID
     * @param petUUID
     * @param skillList
     * @param index
     * @param type
     */
    public static void uninstallSkill(UUID playerUUID, UUID petUUID, List<String> skillList, int index, String type) {
        List<String> unSkills = info().getPetSkillsUn(playerUUID, petUUID, type);
        unSkills.add(skillList.get(index));
        skillList.remove(index);
        info().setPetSkills(playerUUID, skillList, petUUID, type);
        info().setPetSkillsUn(playerUUID, unSkills, petUUID, type);
    }

    /**
     * 装载技能
     * @param playerUUID
     * @param petUUID
     * @param unSkillList
     * @param index
     * @param type
     */
    public static void loadSkill(UUID playerUUID, UUID petUUID, List<String> unSkillList, int index, String type) {
        List<String> skillList = info().getPetSkills(playerUUID, petUUID, type);
        skillList.add(unSkillList.get(index));
        unSkillList.remove(index);
        info().setPetSkillsUn(playerUUID, unSkillList, petUUID, type);
        info().setPetSkills(playerUUID, skillList, petUUID, type);
    }

    /**
     * 删除 装载技能表 或 未装载技能表 指定技能
     * @param playerUUID
     * @param petUUID
     * @param unOrLoadSkillList
     * @param index
     * @param type
     * @param isLoadSkill
     */
    public static void removeSkill(UUID playerUUID, UUID petUUID, List<String> unOrLoadSkillList, int index, String type, boolean isLoadSkill) {
        unOrLoadSkillList.remove(index);
        if (isLoadSkill) {
            info().setPetSkills(playerUUID, unOrLoadSkillList, petUUID, type);
        }else {
            info().setPetSkillsUn(playerUUID, unOrLoadSkillList, petUUID, type);
        }
    }
}
