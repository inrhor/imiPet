package cn.mcres.imiPet.api.attributePlus;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.GetPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.MathTool;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.serverct.ersha.jd.api.EntityAttributeAPI;

public class AttributeManager {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 设置宠物 AP 属性
     * @param entity
     * @param attributeList
     */
    public static void entitySet(Entity entity, List<String> attributeList) {
        EntityAttributeAPI.addEntityAttribute(entity, "imiPet", attributeList);
    }

    /**
     * 根据模型配置公式强化 AP 属性
     * @param playerUUID
     * @param petUUID
     * @param attributeList
     * @param attributeUp
     * @param type
     */
    public static void entityUp(UUID playerUUID, UUID petUUID, List<String> attributeList, List<String> attributeUp, String type) {
        // 对数据进行操作
        for (String s : attributeUp) {
            // 分割升级属性
            String[] splitStrList = s.split("：");
            // 升级属性的前部标签
            String attributeTag = splitStrList[0];
            // 升级属性的后部数值
            String valueString = splitStrList[1];
            // 分割 | 字符
            String[] splitValueStrList = valueString.split("\\|");
            // 真数值
            String value = splitValueStrList[0];
            // 初始值
            String valueBasis = splitValueStrList[1];
            // 若 已有属性表 存在 升级属性
            if (has(attributeList, attributeTag)) {
                // 范围值
                if (value.contains("~")) {
                    attributeValueMathRange(attributeList, attributeTag, value);
                }else { // 无范围值
                    attributeValueMath(attributeList, attributeTag, value);
                }
            }else { // 已有属性表不存在该升级属性
                attributeList.add(attributeTag+"： "+valueBasis);
            }
        }
        info().setPetApAttribute(playerUUID, attributeList, petUUID, type);
        // 若实体处于跟随状态，则对实体属性进行实际操作
        if (info().getPetFollow(playerUUID, petUUID)) {
            EntityAttributeAPI.addEntityAttribute(GetPet.getFollowPet(Bukkit.getPlayer(playerUUID)).getPet(), "imiPet", attributeList);
        }
    }

    private static boolean has(List<String> attributeList, String attribute) {
        for (String s : attributeList) {
            String attributeTag = s.split("：")[0];
            if (attribute.equals(attributeTag)) {
                return true;
            }
        }
        return false;
    }

    private static void attributeValueMathRange(List<String> attributeList, String attribute, String value) {
        int i = 0;
        for (String s : attributeList) {
            // 分割已有属性
            String[] splitStrList = s.split("：");
            // 已有属性的前部标签
            String attributeTag = splitStrList[0];
            // 已有标签 同等 升级目标标签
            if (attribute.equals(attributeTag)) {
                // 已有属性的后部的值
                String nowAttribute = splitStrList[1];
                // 升级属性的后部的值分割
                String[] valueMathStrList = value.split("~");
                // 已有属性的后部的值分割
                String[] mathStrList = nowAttribute.split("~");
                double minValue = Double.parseDouble(mathStrList[0]);
                double maxValue = Double.parseDouble(mathStrList[1]);
                double min = MathTool.stringOperation(/*mathStrList[0]+valueMathStrList[0]*/valueMathStrList[0],
                        "min_now", minValue);
                double max = MathTool.stringOperation(/*mathStrList[1]+valueMathStrList[1]*/valueMathStrList[1],
                        "max_now", maxValue);
                String newValue = attribute+"："+min+"~"+max;
                attributeList.set(i, newValue);
            }
            i++;
        }
    }

    private static void attributeValueMath(List<String> attributeList, String attribute, String value) {
        int i = 0;
        for (String s : attributeList) {
            // 分割已有属性
            String[] splitStrList = s.split("：");
            // 已有属性的前部标签
            String attributeTag = splitStrList[0];
            // 已有标签 同等 升级目标标签
            if (attribute.equals(attributeTag)) {
                // 已有属性的后部的值
                double nowAttribute = Double.parseDouble(splitStrList[1]);
                double now = MathTool.stringOperation(value, "value_now", nowAttribute);
                String newValue = attribute+"："+now;
                attributeList.set(i, newValue);
            }
            i++;
        }
    }
}
