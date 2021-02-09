package cn.mcres.imiPet.skill;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.build.files.CreateFile;
import cn.mcres.imiPet.build.utils.LogUtil;
import cn.mcres.imiPet.instal.lib.MMLib;
import cn.mcres.imiPet.instal.lib.ModelLib;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkillFile {

    private static CreateFile skillsFile = new CreateFile("skills");

    public void run() {
        skillsFile.checkAndCreate();
    }

    /**
     * 已成功注册的技能
     */
    public static List<SkillCreator> skillList = new ArrayList<>();

    public static List<String> skillIDList = new ArrayList<>();

    public static void addSkill() {
        final boolean mmLibEnable = MMLib.MMLibEnable;
        // 循环 skills 文件夹中所有文件
        for (File skillFile : Objects.requireNonNull(skillsFile.getFile().listFiles())) {
            String skillFileName = skillFile.getName();
            if (!skillFileName.endsWith(".yml")) continue;
            // 将YAML文件转化为可解析的文件
            FileConfiguration skillYml = skillFile.exists() ? YamlConfiguration.loadConfiguration(skillFile) : new YamlConfiguration();

            if (!skillYml.contains("skill.id")) {
                LogUtil.send("§c检查到 §f" + skillFileName + "§c 的 §f id §c值为空，已终止注册该技能");
                continue;
            }
            if (!skillYml.contains("skill.startAnimation")) {
                LogUtil.send("§c检查到 §f" + skillFileName + "§c 的 §f startAnimation §c值为空，已终止注册该技能");
                continue;
            }
            if (!skillYml.contains("skill.animation")) {
                LogUtil.send("§c检查到 §f" + skillFileName + "§c 的 §f animation §c值为空，已终止注册该技能");
                continue;
            }

            List<SkillAnimationCreator> animationCreatorList = new ArrayList<>();

            for (String animationID : skillYml.getConfigurationSection("skill.animation").getKeys(false)) {
                String type = getAnimationString(skillYml, animationID, "type");
                SkillAnimationCreator animationCreator = new SkillAnimationCreator(animationID);
                animationCreator.setType(type);
                if (type.equals("item") || type.equals("modelEngine")) {

                    String itemPath = "skill.animation."+animationID+"."+type+".";

                    if (type.equals("item")) {
                        String mainMaterial = getAnimationString(skillYml, animationID, "item.material");

                        ItemStack mainItem = new ItemStack(Material.valueOf(mainMaterial));
                        ItemMeta mainItemMeta = mainItem.getItemMeta();

                        if (skillYml.contains(itemPath + "name")) {
                            String mainName = getAnimationString(skillYml, animationID, "item.name");
                            mainItemMeta.setDisplayName(mainName);
                        }
                        if (skillYml.contains(itemPath + "lore")) {
                            List<String> mainLore = getAnimationStringList(skillYml, animationID, "item.lore");
                            mainItemMeta.setLore(mainLore);
                        }
                        if (skillYml.contains(itemPath + "customModelData")) {
                            if (!ImiPet.loader.getServerInfo().isOldVersion()) {
                                mainItemMeta.setCustomModelData(getAnimationInt(skillYml, animationID, "item.customModelData"));
                            }
                        }

                        mainItem.setItemMeta(mainItemMeta);

                        animationCreator.setItemStack(mainItem);

                    }else {
                        if (!ModelLib.ModelLibEnable) {
                            continue;
                        }
                        animationCreator.setModelId(skillYml.getString(itemPath + "modelId"));
                        animationCreator.setState(skillYml.getString(itemPath + "state"));
                    }

                    if (skillYml.contains(itemPath+"gravity")) {
                        animationCreator.setGravityItem(getAnimationBoolean(skillYml, animationID, "item.gravity"));
                    }

                    if (skillYml.contains(itemPath+"castLocation")) {
                        animationCreator.setCastLocationItem(getAnimationString(skillYml, animationID, "item.castLocation"));
                    }

                    if (skillYml.contains(itemPath+"script")) {
                        animationCreator.setScriptItem(getAnimationString(skillYml, animationID, "item.script"));
                    }

                    if (skillYml.contains(itemPath+"buff.script") && mmLibEnable) {
                        List<String> buffScript = getAnimationStringList(skillYml, animationID, "item.buff.script");
                        animationCreator.setBuffScriptItem(buffScript);
                    }

                    if (skillYml.contains(itemPath+"buff.timeCondition")) {
                        int timeCondition = getAnimationInt(skillYml, animationID, "item.buff.timeCondition");
                        animationCreator.setTimeConditionItem(timeCondition);
                    }

                    if (skillYml.contains(itemPath+"buff.condition")) {
                        String conditionScript = getAnimationString(skillYml, animationID, "item.buff.condition");
                        animationCreator.setConditionItem(conditionScript);
                    }
                }else if (type.equals("mythicMobs")) {
                    if (skillYml.contains("skill.animation."+animationID+".mythicMobs.skills")  && mmLibEnable) {
                        animationCreator.setMythicMobsSkills(getAnimationStringList(skillYml, animationID, "mythicMobs.skills"));
                    }
                }

                String path = "skill.animation."+animationID+".";

                if (skillYml.contains(path+"time")) {
                    animationCreator.setTime(getAnimationInt(skillYml, animationID, "time"));
                }

                if (skillYml.contains(path+"nextAnimationID")) {
                    String nextAnimationID = getAnimationString(skillYml, animationID, "nextAnimationID");
                    animationCreator.setNextAnimationID(nextAnimationID);
                }

                animationCreatorList.add(animationCreator);
            }

            String skillID = skillYml.getString("skill.id").toLowerCase();
            String startAnimation = skillYml.getString("skill.startAnimation");
            SkillCreator skillCreator = new SkillCreator(skillID, startAnimation, animationCreatorList);

            skillIDList.add(skillID);
            skillList.add(skillCreator);
        }
    }

    private static Boolean getAnimationBoolean(FileConfiguration yaml, String animationID, String path) {
        return yaml.getBoolean("skill.animation."+animationID+"."+path);
    }

    private static String getAnimationString(FileConfiguration yaml, String animationID, String path) {
        return yaml.getString("skill.animation."+animationID+"."+path).replaceAll("&", "§");
    }

    private static int getAnimationInt(FileConfiguration yaml, String animationID, String path) {
        return yaml.getInt("skill.animation."+animationID+"."+path);
    }

    private static List<String> getAnimationStringList(FileConfiguration yaml, String animationID, String path) {
        List<String> list = new ArrayList<>();
        for (String s : yaml.getStringList("skill.animation." + animationID + "." + path)) {
            list.add(s.replaceAll("&", "§"));
        }
        return list;
    }
}
