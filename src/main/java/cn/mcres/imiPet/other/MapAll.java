package cn.mcres.imiPet.other;

import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MapAll {

    // 活力
    public static HashMap<BuildPet, Integer> foodCount = new LinkedHashMap<>();

    // 主界面选择的宠物
    public static HashMap<Player, Integer> guiHomeSelectPet = new LinkedHashMap<>();

    // 选择框选择的宠物
    public static HashMap<Player, Integer> guiSelectPet = new LinkedHashMap<>();

    // 页数
    public static HashMap<Player, String> guiVgPet = new LinkedHashMap<>();
    public static HashMap<Player, Integer> vgHomePagePlayer = new LinkedHashMap<>();
    public static HashMap<Player, UUID> vgHomePagePetUUID = new LinkedHashMap<>();

    // 背包与仓库选择的宠物
    public static  HashMap<Player, UUID> vgPackOrWarehouseTarget = new LinkedHashMap<>();

    // 忘了
    public static HashMap<Player, UUID> takeBackPet = new LinkedHashMap<>();

    // 正在骑行的宠物
    public static HashMap<UUID, List<String>> ridePetList = new LinkedHashMap<>();
    public static HashMap<UUID, ArmorStand> ridePet = new LinkedHashMap<>();

    // 交互宠物冷却
    public static HashMap<UUID, Boolean> clickPetCool = new LinkedHashMap<>();

    // 攻击动作动态
    public static HashMap<UUID, BukkitRunnable> runTimeAttack = new LinkedHashMap<>();

    // 正在召唤的非ModelAPI宠物
    public static HashMap<UUID, Entity> followingCemEntity = new LinkedHashMap<>();

    public static HashMap<Entity, Location> canNotMoveLoc = new LinkedHashMap<>();
    public static HashMap<Entity, Integer> petSkillTime = new LinkedHashMap<>();
//    public static HashMap<BuildPet, Integer> skillCoolDown = new LinkedHashMap<>();

    /**
     * BuildPet键 对于 指定 SkillID值的Map-skillCoolDown
     */
//    public static HashMap<BuildPet, HashMap<String, Integer>> skillCoolDownMap = new LinkedHashMap<>();
    /**
     * SkillID键 对于 指定 BuildPet值的冷却
     */
//    public static HashMap<String, Integer> skillCoolDown = new LinkedHashMap<>();

    // 获取实体而为了给予动作状态或删除该实体
    /*public static ModelEntity getModelEntity(Entity ent) {
        if (ent != null) {
            if (ent.hasMetadata("modeled")) {
                for (int i = 0; i < ModelManager.getEntityList().size(); i++) {
                    if (ent.equals(ModelManager.getEntityList().get(i).getEntity()))
                        return ModelManager.getEntityList().get(i);
                }
            }
        }
        return null;
    }*/
}
