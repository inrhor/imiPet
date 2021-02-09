package cn.mcres.imiPet.api.other;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.pet.BuildPet;
import cn.mcres.imiPet.skill.model.AnimationModel;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReplaceAll {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     *
     * @param stringList
     * @param player
     * @return 得到经验盒的值
     */
    public static List<String> expBoxReplace(List<String> stringList, Player player) {
        List<String> text = new ArrayList<>();
        for (String string : stringList) {
            text.add(string.replaceAll("&", "§")
                    .replaceAll("%imipet_expbox%", String.valueOf(info().getExpBox(player.getUniqueId()))));
        }
        return text;
    }

    public static List<String> commonReplace(List<String> stringList) {
        List<String> text = new ArrayList<>();
        for (String string : stringList) {
            text.add(string.replaceAll("&", "§"));
        }
        return text;
    }

    public static List<String> needEvolutionLevel(List<String> stringList, Player player, UUID petUUID, String type) {
        List<String> text = new ArrayList<>();
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, type));
//        String need = GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "evolution.requirement.level");
        String need = String.valueOf(infoManager.getEvolutionRequirementLevel());
        for (String string : stringList) {
            text.add(string.replaceAll("&", "§").replaceAll("%imipet_evolutionlevel%", need));
        }
        return text;
    }

    /**
     *
     * @param stringList 字符
     * @param player 玩家
     * @param petUUID 宠物
     * @return 快速替换字符
     */
    public static List<String> petReplaceAll(List<String> stringList, Player player, UUID petUUID) {
        List<String> newName = new ArrayList<>();
        String type = "pets";
        for (String string : stringList) {
            newName.add(string.replaceAll("&", "§")
                    .replaceAll("%imipet_owner%", player.getName())
                    .replaceAll("%imipet_name%", info().getPetName(player, petUUID, type))
                    .replaceAll("%imipet_level%", String.valueOf(info().getPetLevel(player, petUUID, type)))
                    .replaceAll("%imipet_nowhp%", String.valueOf(info().getPetNowHP(player, petUUID, type)))
                    .replaceAll("%imipet_maxhp%", String.valueOf(info().getPetMaxHP(player, petUUID, type)))
                    .replaceAll("%imipet_nowexp%", String.valueOf(info().getPetNowExp(player, petUUID, type)))
                    .replaceAll("%imipet_maxexp%", String.valueOf(info().getPetMaxExp(player, petUUID, type)))
                    .replaceAll("%imipet_nowfood%", String.valueOf(info().getPetNowFood(player, petUUID, type)))
                    .replaceAll("%imipet_maxfood%", String.valueOf(info().getPetMaxFood(player, petUUID, type)))
                    .replaceAll("%imipet_damage%", getDamageString(player, petUUID, type)));
        }
        return newName;
    }

    /**
     *
     * @param string 字符
     * @param player 玩家
     * @param petUUID 宠物
     * @return 快速替换字符(imipet_owner imipet_name)
     */
    public static String petNameReplaceAll(String string, Player player, UUID petUUID) {
        return string
                .replaceAll("&", "§")
                .replaceAll("%imipet_owner%", player.getName())
                .replaceAll("%imipet_name%", info().getPetName(player, petUUID, "pets"));
    }

    public static List<String> petWarehouseReplaceAll(List<String> stringList, Player player, UUID petUUID) {
        List<String> newString = new ArrayList<>();
        for (String s : stringList) {
            newString.add(petWarehouseReplaceAll(s, player, petUUID));
        }
        return newString;
    }

    public static String petWarehouseReplaceAll(String string, Player player, UUID petUUID) {
        String type = "warehouse";
        return string.replaceAll("&", "§")
                .replaceAll("%warehouse_name%", info().getPetName(player, petUUID, type))
                .replaceAll("%warehouse_level%", String.valueOf(info().getPetLevel(player, petUUID, type)))
                .replaceAll("%warehouse_nowexp%", String.valueOf(info().getPetNowExp(player, petUUID, type)))
                .replaceAll("%warehouse_maxexp%", String.valueOf(info().getPetMaxExp(player, petUUID, type)))
                .replaceAll("%warehouse_nowhp%", String.valueOf(info().getPetNowHP(player, petUUID, type)))
                .replaceAll("%warehouse_maxhp%", String.valueOf(info().getPetMaxHP(player, petUUID, type)))
                .replaceAll("%warehouse_nowfood%", String.valueOf(info().getPetNowFood(player, petUUID, type)))
                .replaceAll("%warehouse_maxfood%", String.valueOf(info().getPetMaxFood(player, petUUID, type)))
                .replaceAll("%warehouse_minDamage%", String.valueOf(info().getPetMinDamage(player, petUUID, type)))
                .replaceAll("%warehouse_maxDamage%", String.valueOf(info().getPetMaxDamage(player, petUUID, type)))
                .replaceAll("%warehouse_damage%", getDamageString(player, petUUID, type));
    }

    public static String getDamageString(Player player, UUID petUUID, String type) {
        double minDamage = info().getPetMinDamage(player, petUUID, type);
        double maxDamage = info().getPetMaxDamage(player, petUUID, type);
        if (minDamage == maxDamage) {
            return String.valueOf(maxDamage);
        }
        return (minDamage+"-"+maxDamage);
    }

    public static List<String> replaceSkillCastGui(BuildPet buildPet, List<String> lore, List<String> skillList, int index) {
        List<String> newString = new ArrayList<>();
        // 格式是 MechaEngineering
        // 技能ID
        String skillID = skillList.get(index);
        AnimationModel am = buildPet.getModelEntityManager().getAnimationModel(skillID);
        for (String s : lore) {
            newString.add(s.replaceAll("&", "§").replaceAll("@skill_name", am.getSkillName()));
        }
        return newString;
    }
}
