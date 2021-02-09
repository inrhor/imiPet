package cn.mcres.imiPet.api.buff;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.other.PetUtils;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.serverct.ersha.jd.AttributeAPI;
import org.serverct.ersha.jd.api.EntityAttributeAPI;

import java.util.*;

public class BuffAPManager {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 触发宠物的加成反应 - AP
     * @param buildPet
     */
    public static void setBuffAP(BuildPet buildPet) {
        int i = 0;
        UUID playerUUID = buildPet.getPlayer().getUniqueId();
        UUID petUUID = buildPet.getPetUUID();
        LivingEntity pet = buildPet.getPet();
        for (String buffAP : info().getPetBuffAP(playerUUID, petUUID, "pets")) {
            String[] splitStrList = buffAP.split(";");
            boolean onlyOwner = Boolean.parseBoolean(splitStrList[0]);
            double range = Double.parseDouble(splitStrList[1]);
            String attribute = splitStrList[2];
            List<UUID> newPlayers = new ArrayList<>();
            for (Entity entity : pet.getNearbyEntities(range, range, range)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (onlyOwner) {
                        if (PetUtils.isOwner(player, pet)) {
                            setPlayerBuffAPList(player, attribute, i);
                            newPlayers.add(playerUUID);
                        }
                    }else {
                        setPlayerBuffAPList(player, attribute, i);
                        newPlayers.add(playerUUID);
                    }
                }
            }
            if (buildPet.getBuffAPPlayerList(i) != null) {
                rangeRemoveBuff(buildPet.getBuffAPPlayerList(i), newPlayers, i);
            }
            buildPet.setBuffAPPlayerList(newPlayers, i);
            i++;
        }
    }

    /**
     * 根据模型配置公式强化加成反应属性 - AP
     * @param playerUUID
     * @param petUUID
     * @param infoManager
     * @param type
     */
    public static void upPetBuffAP(UUID playerUUID, UUID petUUID, ModelInfoManager infoManager, String type) {
        for (String yamlBuffAP : infoManager.getBuffAttributeAP()) {
            // 来自宠物配置的BUFF
            String[] splitStrList = yamlBuffAP.split(";");
            String formula = splitStrList[4];
            if (!formula.equals("null")) {
                // 分割升级属性
                String[] attributeUp = formula.split("：");
                // 升级属性的前部标签
                String attributeTag = attributeUp[0];
                // 升级属性的后部数值
                String valueString = attributeUp[1];
                List<String> attributeList = info().getPetBuffAP(playerUUID, petUUID, type);
                // 若 已有属性表 存在 升级属性
                if (has(attributeList, attributeTag)) {
                    // 范围值
                    if (valueString.contains("~")) {
                        attributeValueMathRange(attributeList, attributeTag, valueString);
                    }else { // 无范围值
                        attributeValueMath(attributeList, attributeTag, valueString);
                    }
                }else { // 已有属性表不存在该升级属性
                    attributeList.add(yamlBuffAP);
                }
                info().setPetBuffAP(playerUUID, attributeList, petUUID, type);
            }
        }
    }

    private static boolean has(List<String> attributeList, String attribute) {
        for (String s : attributeList) {
            String attributeTag = s.split(";")[2].split("：")[0];
            if (attribute.equals(attributeTag)) {
                return true;
            }
        }
        return false;
    }

    private static void attributeValueMathRange(List<String> attributeList, String attribute, String valueString) {
        int i = 0;
        for (String s : attributeList) {
            String l = ";";
            // 分割
            String[] splitStrList = s.split(l);
            String[] apSplit = splitStrList[2].split("：");
            // 已有属性的前部标签
            String attributeTag = apSplit[0];
            // 已有标签 同等 升级目标标签
            if (attribute.equals(attributeTag)) {
                // 已有属性的后部的值
                String nowAttribute = apSplit[1];
                // 升级属性的后部的值分割
                String[] valueMathStrList = valueString.split("~");
                // 已有属性的后部的值分割
                String[] mathStrList = nowAttribute.split("~");
                double minValue = Double.parseDouble(mathStrList[0]);
                double maxValue = Double.parseDouble(mathStrList[1]);
                double min = MathTool.stringOperation(valueMathStrList[0],
                        "min_now", minValue);
                double max = MathTool.stringOperation(valueMathStrList[1],
                        "max_now", maxValue);
                String newValue = attribute + "：" + min + "~" + max;
                attributeList.set(i, newBuffAPStr(splitStrList, newValue));
            }
            i++;
        }
    }

    private static String newBuffAPStr(String[] splitStrList, String newValue) {
        String l = ";";
        String onlyOwner = splitStrList[0];
        String range = splitStrList[1];
        String canUp = splitStrList[3];
        String formula = splitStrList[4];
        String newStr = onlyOwner + l + range + l + newValue + l + canUp + l + formula;
        return newStr;
    }

    private static void attributeValueMath(List<String> attributeList, String attribute, String value) {
        int i = 0;
        for (String s : attributeList) {
            String l = ";";
            // 分割
            String[] splitStrList = s.split(l);
            String[] apSplit = splitStrList[2].split("：");
            // 已有属性的前部标签
            String attributeTag = apSplit[0];
            // 已有标签 同等 升级目标标签
            if (attribute.equals(attributeTag)) {
                // 已有属性的后部的值
                double nowAttribute = Double.parseDouble(apSplit[1]);
                double now = MathTool.stringOperation(value, "value_now", nowAttribute);
                String newValue = attribute+"："+now;
                attributeList.set(i, newBuffAPStr(splitStrList, newValue));
            }
            i++;
        }
    }

    private static void setPlayerBuffAPList(Player player, String buffAP, int i) {
        String buffSign = "buffAP"+i;
        if (!EntityAttributeAPI.isSource(player, buffSign)) {
            List<String> apList = new ArrayList<>();
            apList.add(buffAP);
            AttributeAPI.addAttribute(player, buffSign, apList);
        }else {
            List<String> apList = EntityAttributeAPI.getSourceAttribute(player, buffSign);
            if (apList.contains(buffAP)) return;
            apList.add(buffAP);
            AttributeAPI.addAttribute(player, buffSign, apList);
        }
    }

    private static void rangeRemoveBuff(List<UUID> oldList, List<UUID> newList, int i) {
        String buffSign = "buffAP"+i;
        for (UUID playerUUID : oldList) {
            if (!newList.contains(playerUUID)) {
                Player player = Bukkit.getPlayer(playerUUID);
                if (player != null) {
                    EntityAttributeAPI.removeEntityAttribute(player, buffSign);
                }
            }
        }
    }

    /**
     * 消除来自该宠物赋予的加成反应
     * @param buildPet
     */
    public static void removeAllBuff(BuildPet buildPet) {
        if (APLib.APLibEnable) {
            for (int i : buildPet.getAllBuffAPMap()) {
                for (UUID playerUUID : buildPet.getBuffAPPlayerList(i)) {
                    Player player = Bukkit.getPlayer(playerUUID);
                    if (player == null) break;
                    EntityAttributeAPI.removeEntityAttribute(player, "buffAP" + i);
                }
            }
            buildPet.removeBuffAPMap();
        }
    }
}
