package cn.mcres.imiPet.api.data;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author inrhor
 * @version 1.0.0
 */
public interface Info {

    /**
     * 获得指定玩家背包是否有宠物
     *
     * @param player 玩家
     * @return 获得指定玩家背包是否有宠物
     */
    @Contract(pure = true)
    boolean havePet(@NotNull UUID player);

    /**
     * 获得指定玩家仓库是否有宠物
     *
     * @param player 玩家
     * @return 获得指定玩家仓库是否有宠物
     */
    @Contract(pure = true)
    boolean haveWarehousePet(@NotNull UUID player);

    @Contract(pure = true)
    default boolean havePet(@NotNull Player player) {
        return havePet(player.getUniqueId());
    }

    default void storeAll() {
    }

    default void forceSave(@NotNull UUID player) {
    }

    /**
     * 获得指定玩家的宠物模型ID
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物模型ID
     */
    String getPetModelId(@NotNull UUID player, @NotNull UUID uuid, String type);

    default String getPetModelId(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetModelId(player.getUniqueId(), uuid, type);
    }

    default UUID definePet(@NotNull PetDefiner info) {
        UUID rand = info.petUUID;
        setPetModelId(info.player, info.modelId, rand, "pets");
        setPetName(info.player, info.petName, rand, "pets");
        setPetMaxHP(info.player, info.petMaxHP, rand, "pets");
        setPetNowHP(info.player, info.petNowHP, rand, "pets");
        setPetMinDamage(info.player, info.petMinDamage, rand, "pets");
        setPetMaxDamage(info.player, info.petMaxDamage, rand, "pets");
        setPetLevel(info.player, info.petLevel, rand, "pets");
        setPetMaxExp(info.player, info.petMaxExp, rand, "pets");
        setPetNowExp(info.player, info.petNowExp, rand, "pets");
        setPetMaxFood(info.player, info.petMaxFood, rand, "pets");
        setPetNowFood(info.player, info.petNowFood, rand, "pets");
        setPetApAttribute(info.player, info.apAttribute, rand, "pets");
        setPetBuffAP(info.player, info.buffAPList, rand, "pets");
        return rand;
    }

    default UUID warehouseDefinePet(@NotNull PetDefiner info) {
        UUID rand = info.petUUID;
        setPetModelId(info.player, info.modelId, rand, "warehouse");
        setPetName(info.player, info.petName, rand, "warehouse");
        setPetMaxHP(info.player, info.petMaxHP, rand, "warehouse");
        setPetNowHP(info.player, info.petNowHP, rand, "warehouse");
        setPetMinDamage(info.player, info.petMinDamage, rand, "warehouse");
        setPetMaxDamage(info.player, info.petMaxDamage, rand, "warehouse");
        setPetLevel(info.player, info.petLevel, rand, "warehouse");
        setPetMaxExp(info.player, info.petMaxExp, rand, "warehouse");
        setPetNowExp(info.player, info.petNowExp, rand, "warehouse");
        setPetMaxFood(info.player, info.petMaxFood, rand, "warehouse");
        setPetNowFood(info.player, info.petNowFood, rand, "warehouse");
        setPetApAttribute(info.player, info.apAttribute, rand, "warehouse");
        setPetBuffAP(info.player, info.buffAPList, rand, "warehouse");
        return rand;
    }

    /**
     * 设置玩家的宠物模型ID
     *
     * @param player  玩家
     * @param modelId 宠物模型ID
     * @param uuid    宠物UUID
     */
    void setPetModelId(@NotNull UUID player, @NotNull String modelId, @NotNull UUID uuid, String type);

    default void setPetModelId(@NotNull Player player, @NotNull String modelId, @NotNull UUID uuid, String type) {
        setPetModelId(player.getUniqueId(), modelId, uuid, type);
    }

    /**
     * 获得指定玩家的宠物名称
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物名称
     */
    @Contract(pure = true)
    String getPetName(@NotNull UUID player, @NotNull UUID uuid, String type);

    @Contract(pure = true)
    default String getPetName(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetName(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物名称
     *
     * @param player  玩家
     * @param petName 宠物名称
     * @param uuid    宠物UUID
     */
    void setPetName(@NotNull UUID player, String petName, @NotNull UUID uuid, String type);

    default void setPetName(@NotNull Player player, String petName, @NotNull UUID uuid, String type) {
        setPetName(player.getUniqueId(), petName, uuid, type);
    }

    /**
     * 获得指定玩家的宠物当前经验
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物当前经验
     */
    int getPetNowExp(@NotNull UUID player, @NotNull UUID uuid, String type);

    default int getPetNowExp(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetNowExp(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物当前经验
     *
     * @param player    玩家
     * @param petNowExp 宠物当前经验
     * @param uuid      宠物UUID
     */
    void setPetNowExp(@NotNull UUID player, int petNowExp, @NotNull UUID uuid, String type);

    default void setPetNowExp(@NotNull Player player, int petNowExp, @NotNull UUID uuid, String type) {
        setPetNowExp(player.getUniqueId(), petNowExp, uuid, type);
    }

    /**
     * 获得指定玩家的宠物经验上限
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物经验上限
     */
    int getPetMaxExp(@NotNull UUID player, @NotNull UUID uuid, String type);

    default int getPetMaxExp(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetMaxExp(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物上限经验
     *
     * @param player    玩家
     * @param petMaxExp 宠物上限经验
     * @param uuid      宠物UUID
     */
    void setPetMaxExp(@NotNull UUID player, int petMaxExp, @NotNull UUID uuid, String type);

    default void setPetMaxExp(@NotNull Player player, int petMaxExp, @NotNull UUID uuid, String type) {
        setPetMaxExp(player.getUniqueId(), petMaxExp, uuid, type);
    }

    /**
     * 获得指定玩家的宠物等级
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物等级
     */
    int getPetLevel(@NotNull UUID player, @NotNull UUID uuid, String type);

    default int getPetLevel(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetLevel(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物等级
     *
     * @param player   玩家
     * @param petLevel 宠物等级
     * @param uuid     宠物UUID
     */
    void setPetLevel(@NotNull UUID player, int petLevel, @NotNull UUID uuid, String type);

    default void setPetLevel(@NotNull Player player, int petLevel, @NotNull UUID uuid, String type) {
        setPetLevel(player.getUniqueId(), petLevel, uuid, type);
    }

    /**
     * 获得指定玩家的宠物的跟随与否状态
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物的跟随与否状态
     */
    boolean getPetFollow(@NotNull UUID player, @NotNull UUID uuid);

    default boolean getPetFollow(@NotNull Player player, @NotNull UUID uuid) {
        return getPetFollow(player.getUniqueId(), uuid);
    }

    /**
     * 设置玩家的宠物的跟随与否状态
     *
     * @param player 玩家
     * @param follow 宠物等级
     * @param uuid   宠物UUID
     */
    void setPetFollow(@NotNull UUID player, boolean follow, @NotNull UUID uuid);

    default void setPetFollow(@NotNull Player player, boolean follow, @NotNull UUID uuid) {
        setPetFollow(player.getUniqueId(), follow, uuid);
    }

    /**
     * 获得指定玩家的宠物当前血量
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物当前血量
     */
    double getPetNowHP(@NotNull UUID player, @NotNull UUID uuid, String type);

    default double getPetNowHP(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetNowHP(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物当前血量
     *
     * @param player   玩家
     * @param petNowHP 宠物当前血量
     * @param uuid     宠物UUID
     */
    void setPetNowHP(@NotNull UUID player, double petNowHP, @NotNull UUID uuid, String type);

    default void setPetNowHP(@NotNull Player player, double petNowHP, @NotNull UUID uuid, String type) {
        setPetNowHP(player.getUniqueId(), petNowHP, uuid, type);
        if (GetPet.getFollowPet(player)!=null) {
            GetPet.getFollowPet(player).getPet().setHealth(petNowHP);
        }
    }

    /**
     * 获得指定玩家的宠物上限血量
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物上限血量
     */
    double getPetMaxHP(@NotNull UUID player, @NotNull UUID uuid, String type);

    default double getPetMaxHP(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetMaxHP(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物上限血量
     *
     * @param player   玩家
     * @param petMaxHP 宠物上限血量
     * @param uuid     宠物UUID
     */
    void setPetMaxHP(@NotNull UUID player, double petMaxHP, @NotNull UUID uuid, String type);

    default void setPetMaxHP(@NotNull Player player, double petMaxHP, @NotNull UUID uuid, String type) {
        setPetMaxHP(player.getUniqueId(), petMaxHP, uuid, type);
        if (GetPet.getFollowPet(player)!=null) {
            GetPet.getFollowPet(player).getPet().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(petMaxHP);
        }
    }

    /**
     * 获得指定玩家的宠物当前活力值
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物当前活力值
     */
    int getPetNowFood(@NotNull UUID player, @NotNull UUID uuid, String type);

    default int getPetNowFood(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetNowFood(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物当前活力值
     *
     * @param player     玩家
     * @param petNowFood 宠物当前活力值
     * @param uuid       宠物UUID
     */
    void setPetNowFood(@NotNull UUID player, int petNowFood, @NotNull UUID uuid, String type);

    default void setPetNowFood(@NotNull Player player, int petNowFood, @NotNull UUID uuid, String type) {
        setPetNowFood(player.getUniqueId(), petNowFood, uuid, type);
    }

    /**
     * 获得指定玩家的宠物最大活力值
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物最大活力值
     */
    int getPetMaxFood(@NotNull UUID player, @NotNull UUID uuid, String type);

    default int getPetMaxFood(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetMaxFood(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物当前活力值
     *
     * @param player     玩家
     * @param petMaxFood 宠物最大活力值
     * @param uuid       宠物UUID
     */
    void setPetMaxFood(@NotNull UUID player, int petMaxFood, @NotNull UUID uuid, String type);

    default void setPetMaxFood(@NotNull Player player, int petMaxFood, @NotNull UUID uuid, String type) {
        setPetMaxFood(player.getUniqueId(), petMaxFood, uuid, type);
    }

    /**
     * 获得指定玩家的宠物最低攻击力
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物最低攻击力
     */
    double getPetMinDamage(@NotNull UUID player, @NotNull UUID uuid, String type);

    default double getPetMinDamage(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetMinDamage(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物最低攻击力
     *
     * @param player       玩家
     * @param petMinDamage 最低攻击力
     * @param uuid         宠物UUID
     */
    void setPetMinDamage(@NotNull UUID player, double petMinDamage, @NotNull UUID uuid, String type);

    default void setPetMinDamage(@NotNull Player player, double petMinDamage, @NotNull UUID uuid, String type) {
        setPetMinDamage(player.getUniqueId(), petMinDamage, uuid, type);
    }

    /**
     * 获得指定玩家的宠物最高攻击力
     *
     * @param player 玩家
     * @param uuid   宠物UUID
     * @return 获得指定玩家的宠物最高攻击力
     */
    double getPetMaxDamage(@NotNull UUID player, @NotNull UUID uuid, String type);

    default double getPetMaxDamage(@NotNull Player player, @NotNull UUID uuid, String type) {
        return getPetMaxDamage(player.getUniqueId(), uuid, type);
    }

    /**
     * 设置玩家的宠物最高攻击力
     *
     * @param player       玩家
     * @param petMaxDamage 宠物最高攻击力
     * @param uuid         宠物UUID
     */
    void setPetMaxDamage(@NotNull UUID player, double petMaxDamage, @NotNull UUID uuid, String type);

    default void setPetMaxDamage(@NotNull Player player, double petMaxDamage, @NotNull UUID uuid, String type) {
        setPetMaxDamage(player.getUniqueId(), petMaxDamage, uuid, type);
    }

    /**
     * 设置玩家的经验存储盒的经验值
     *
     * @param playerUUID 玩家UUID
     * @param exp        经验值
     */
    void setExpBox(@NotNull UUID playerUUID, int exp);

    /**
     * 获得玩家的经验存储盒的经验值值
     *
     * @param playerUUID 玩家UUID
     * @return 获得玩家的经验存储盒的经验值值
     */
    int getExpBox(@NotNull UUID playerUUID);

    /**
     * 添加玩家的经验存储盒的经验值值
     *
     * @param playerUUID 玩家UUID
     * @param addExp     添加经验值
     */
    void addExpBox(@NotNull UUID playerUUID, int addExp);

    /**
     * 减少玩家的经验存储盒的经验值值
     *
     * @param playerUUID 玩家UUID
     * @param delExp     减少经验值
     */
    void delExpBox(@NotNull UUID playerUUID, int delExp);

    /**
     * 获得跟随玩家的宠物的UUID
     *
     * @param player 玩家
     * @return 获得跟随玩家的宠物的UUID
     */
    @NotNull
    default Set<UUID> getFollowingPetUUID(@NotNull Player player) {
        return getFollowingPetUUID(player.getUniqueId());
    }

    /**
     * 获得跟随玩家的宠物的UUID
     *
     * @param player 玩家
     * @return 获得跟随玩家的宠物的UUID
     */
    Set<UUID> getFollowingPetUUID(@NotNull UUID player);
    /*
     *
     * @param player 玩家
     * @param petName 宠物名称
     * @return 获得指定宠物名称的宠物的UUID
     */
//    UUID getFollowPetUUID(Player player, String petName);

    /**
     * 获得玩家的宠物背包拥有宠物量 上限6个
     *
     * @param playerUUID 玩家UUID
     * @return 获得玩家的宠物背包拥有宠物量 上限6个
     */
    int getPetsPackAmount(@NotNull UUID playerUUID);

    /**
     * 集合玩家背包宠物为列表
     *
     * @param playerUUID 玩家UUID
     * @return 集合玩家背包宠物为列表, 其中UUID为宠物UUID
     */
    @NotNull
    List<UUID> getPetsPackList(@NotNull UUID playerUUID);

    /**
     * 获得玩家的宠物仓库拥有宠物量 上限6个
     *
     * @param playerUUID 玩家UUID
     * @return 获得玩家的宠物仓库拥有宠物量
     */
    int getPetsWarehouseAmount(@NotNull UUID playerUUID);

    /**
     * 集合玩家背包宠物为列表
     *
     * @param playerUUID 玩家UUID
     * @return 集合玩家仓库宠物为列表, 其中UUID为宠物UUID
     */
    @NotNull
    List<UUID> getPetsWarehouseList(@NotNull UUID playerUUID);

    /**
     * 删除指定玩家的目标宠物数据，如果用于放生就不推荐直接使用这个
     *
     * @param playerUUID 玩家UUID
     * @param petUUID    宠物UUID
     */
    void removePet(@NotNull UUID playerUUID, @NotNull UUID petUUID, String type);

    /**
     * 获取宠物的 AttributePlus 属性表
     *
     * @param playerUUID
     * @param petUUID
     * @return
     */
    List<String> getPetApAttribute(@NotNull UUID playerUUID, @NotNull UUID petUUID, String type);

    /**
     * 设置指定玩家的宠物 AttributePlus 属性表
     * @param playerUUID
     * @param attributeList
     * @param petUUID
     * @param type
     */
    void setPetApAttribute(@NotNull UUID playerUUID, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 设置指定玩家的宠物 AttributePlus 属性表
     * @param player
     * @param attributeList
     * @param petUUID
     * @param type
     */
    void setPetApAttribute(@NotNull Player player, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 获取宠物的 BUFF AttributePlus 属性表
     *
     * @param playerUUID
     * @param petUUID
     * @return
     */
    List<String> getPetBuffAP(@NotNull UUID playerUUID, @NotNull UUID petUUID,  @NotNull String type);

    /**
     * 设置指定玩家的宠物 BUFF AttributePlus 属性表
     * @param playerUUID
     * @param attributeList
     * @param petUUID
     * @param type
     */
    void setPetBuffAP(@NotNull UUID playerUUID, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 设置指定玩家的宠物 技能表
     * @param player
     * @param attributeList
     * @param petUUID
     * @param type
     */
    void setPetBuffAP(@NotNull Player player, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 返回指定玩家的宠物 技能表
     * @param playerUUID
     * @param petUUID
     * @param type
     * @return
     */
    List<String> getPetSkills(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 设置指定玩家的宠物 技能表
     * @param playerUUID
     * @param skillList
     * @param petUUID
     */
    void setPetSkills(@NotNull UUID playerUUID, List<String> skillList, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 设置指定玩家的宠物 技能表
     * @param player
     * @param skillList
     * @param petUUID
     */
    void setPetSkills(@NotNull Player player, List<String> skillList, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 返回指定玩家的宠物 未装载的技能表
     * @param playerUUID
     * @param petUUID
     * @param type
     * @return
     */
    List<String> getPetSkillsUn(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 设置指定玩家的宠物 未装载的技能表
     * @param playerUUID
     * @param skillList
     * @param petUUID
     */
    void setPetSkillsUn(@NotNull UUID playerUUID, List<String> skillList, @NotNull UUID petUUID, @NotNull String type);

    /**
     * 设置指定玩家的宠物 未装载的技能表
     * @param player
     * @param skillList
     * @param petUUID
     */
    void setPetSkillsUn(@NotNull Player player, List<String> skillList, @NotNull UUID petUUID, @NotNull String type);
}
