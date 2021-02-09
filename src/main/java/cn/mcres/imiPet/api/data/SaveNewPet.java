package cn.mcres.imiPet.api.data;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.build.utils.Msg;

import java.util.List;

import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.entity.Player;

import java.util.UUID;


public class SaveNewPet {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 收获新宠物而设置
     *
     * @param modelId 模型ID
     * @param petName 宠物名称
     * @param petMaxHP 最大血量
     * @param petNowHP 当前血量
     * @param petMinDamage 当前伤害
     * @param petMaxDamage 最大伤害
     * @param petLevel 等级
     * @param petMaxExp 最大经验值
     * @param petNowExp 当前经验值
     * @param petMaxFood 最大活力
     * @param petNowFood 当前活力
     */
    public static void createNewPet(Player player, String modelId, String petName,
                                    double petMaxHP, double petNowHP,
                                    double petMinDamage, double petMaxDamage,
                                    int petLevel,
                                    int petMaxExp, int petNowExp,
                                    int petMaxFood, int petNowFood,
                                    boolean apEnable, List<String> apAttribute,
                                    boolean buffAPEnable, List<String> buffAPList) {
        UUID playerUUID = player.getUniqueId();
        UUID uuid = UUID.randomUUID();
        PetDefiner pd = new PetDefiner();
        basisAttributePet(pd, uuid, player, modelId, petName,
                petMaxHP, petNowHP,
                petMinDamage, petMaxDamage,
                petLevel,
                petMaxExp, petNowExp,
                petMaxFood, petNowFood,
                apEnable, apAttribute,
                buffAPEnable, buffAPList);
        if (info().getPetsPackAmount(playerUUID) < 6) {
            info().definePet(pd);
        }else {
            info().warehouseDefinePet(pd);
        }
    }

    /**
     * 收获新宠物而设置
     *
     * @param modelId 模型ID
     * @param petName 宠物名称
     * @param petMaxHP 最大血量
     * @param petNowHP 当前血量
     * @param petMinDamage 当前伤害
     * @param petMaxDamage 最大伤害
     * @param petLevel 等级
     * @param petMaxExp 最大经验值
     * @param petNowExp 当前经验值
     * @param petMaxFood 最大活力
     * @param petNowFood 当前活力
     * @param to 放入背包还是仓库 pack/warehouse
     */
    public static void createNewPet(Player player, String modelId, String petName,
                                    double petMaxHP, double petNowHP,
                                    double petMinDamage, double petMaxDamage,
                                    int petLevel,
                                    int petMaxExp, int petNowExp,
                                    int petMaxFood, int petNowFood,
                                    boolean apEnable, List<String> apAttribute,
                                    boolean buffAPEnable, List<String> buffAPList,
                                    String to) {
        UUID uuid = UUID.randomUUID();
        PetDefiner pd = new PetDefiner();
        basisAttributePet(pd, uuid, player, modelId, petName,
                petMaxHP, petNowHP,
                petMinDamage, petMaxDamage,
                petLevel,
                petMaxExp, petNowExp,
                petMaxFood, petNowFood,
                apEnable, apAttribute,
                buffAPEnable, buffAPList);
        if (to.equals("pets")) {
            info().definePet(pd);
        }else {
            info().warehouseDefinePet(pd);
        }
    }

    private static void basisAttributePet(PetDefiner pd, UUID petUUID, Player player, String modelId, String petName,
                                          double petMaxHP, double petNowHP,
                                          double petMinDamage, double petMaxDamage,
                                          int petLevel,
                                          int petMaxExp, int petNowExp,
                                          int petMaxFood, int petNowFood,
                                          boolean apEnable, List<String> apAttribute,
                                          boolean buffAPEnable, List<String> buffAPList) {
        pd.petUUID = petUUID;
        pd.player = player.getUniqueId();
        pd.modelId = modelId;
        pd.petName = petName;
        pd.petMaxHP = petMaxHP;
        pd.petNowExp = petNowExp;
        pd.petNowHP = petNowHP;
        pd.petMinDamage = petMinDamage;
        pd.petMaxDamage = petMaxDamage;
        pd.petLevel = petLevel;
        pd.petMaxExp = petMaxExp;
        pd.petMaxFood = petMaxFood;
        pd.petNowFood = petNowFood;
        pd.apEnable = apEnable;
        pd.buffAPEnable = buffAPEnable;
        if (apEnable) {
            pd.apAttribute = apAttribute;
        }
        if (buffAPEnable) {
            pd.buffAPList = buffAPList;
        }
    }

    /**
     * 转交宠物
     *
     * @param oldOwner 旧主人
     * @param newOwner 新主人
     * @param petUUID 宠物
     */
    public static void TransferPet(Player oldOwner, Player newOwner, UUID petUUID, boolean apEnable, boolean buffAPEnable) {
        if (info().getPetsPackAmount(newOwner.getUniqueId())<6) {
            if (!oldOwner.equals(newOwner)) {
                String type = "pets";
                String petName = info().getPetName(oldOwner, petUUID, type);
                /*for (String msg : SUCCESSFULLY_TRANSFER_PET_TO_NEWOWNER) {
                    Msg.send(oldOwner, msg.replaceAll("%imipet_name%", petName).replaceAll("%newowner_name%", newOwner.getName()));
                }*/
                TLocale.sendTo(oldOwner, "SUCCESSFULLY_TRANSFER_PET_TO_NEWOWNER", petName, newOwner.getName());
                /*for (String msg : SUCCESSFULLY_TRANSFER_PET_FROM_OLDOWNER) {
                    Msg.send(oldOwner, msg.replaceAll("%oldowner_name%", oldOwner.getName()).replaceAll("%imipet_name%", petName));
                }*/
                TLocale.sendTo(oldOwner, "SUCCESSFULLY_TRANSFER_PET_FROM_OLDOWNER", oldOwner.getName(), petName);
                UUID oldOwnerUUID = oldOwner.getUniqueId();
                SaveNewPet.createNewPet(newOwner,
                        info().getPetModelId(oldOwner, petUUID, type),
                        info().getPetName(oldOwner, petUUID, type),
                        info().getPetMaxHP(oldOwner, petUUID, type),
                        info().getPetNowHP(oldOwner, petUUID, type),
                        info().getPetMinDamage(oldOwner, petUUID, type),
                        info().getPetMaxDamage(oldOwner, petUUID, type),
                        info().getPetLevel(oldOwner, petUUID, type),
                        info().getPetMaxExp(oldOwner, petUUID, type),
                        info().getPetNowExp(oldOwner, petUUID, type),
                        info().getPetMaxFood(oldOwner, petUUID, type),
                        info().getPetNowFood(oldOwner, petUUID, type),
                        apEnable, info().getPetApAttribute(oldOwner.getUniqueId(), petUUID, type),
                        buffAPEnable, info().getPetBuffAP(oldOwnerUUID, petUUID, type));
                SaveDelPet.removePet(oldOwner.getUniqueId(), petUUID, type);
            }else {
//                Msg.send(oldOwner, CAN_NOT_SELF);
                TLocale.sendTo(oldOwner, "CAN_NOT_SELF");
            }
        }else {
//            Msg.send(oldOwner, PET_ALREADY_FULL_PACK_ADMIN);
            TLocale.sendTo(oldOwner, "PET_ALREADY_FULL_PACK_ADMIN");
        }
    }

    public static void TransferPackWarehouse(Player player, UUID targetPetUUID, UUID selectUUID,
                                             String origin,
                                             boolean apEnable,
                                             boolean buffAPEnable) {
        String petsType = "pets";
        String warehouseType = "warehouse";
        UUID playerUUID = player.getUniqueId();
        if (origin.equals(petsType)) {
            CreateNewPet(player, targetPetUUID, selectUUID, apEnable, buffAPEnable, petsType, warehouseType, playerUUID);
        }else {
            CreateNewPet(player, selectUUID, targetPetUUID, apEnable, buffAPEnable, warehouseType, petsType, playerUUID);
        }
    }

    private static void CreateNewPet(Player player, UUID targetPetUUID, UUID selectUUID, boolean apEnable, boolean buffAPEnable, String petsType, String warehouseType, UUID playerUUID) {
        CreateNewPet(player, selectUUID, apEnable, buffAPEnable, petsType, warehouseType, playerUUID);
        CreateNewPet(player, targetPetUUID, apEnable, buffAPEnable, warehouseType, petsType, playerUUID);
    }

    public static void TransferPackWarehouse(Player player, UUID targetPetUUID,
                                             String origin,
                                             boolean apEnable,
                                             boolean buffAPEnable) {
        String petsType = "pets";
        String warehouseType = "warehouse";
        UUID playerUUID = player.getUniqueId();
        if (origin.equals(petsType)) {
            CreateNewPet(player, targetPetUUID, apEnable, buffAPEnable, petsType, warehouseType, playerUUID);
        }else {
            CreateNewPet(player, targetPetUUID, apEnable, buffAPEnable, warehouseType, petsType, playerUUID);
        }
    }

    private static void CreateNewPet(Player player, UUID targetPetUUID, boolean apEnable, boolean buffAPEnable, String petsType, String warehouseType, UUID playerUUID) {
        SaveNewPet.createNewPet(player,
                info().getPetModelId(player, targetPetUUID, petsType),
                info().getPetName(player, targetPetUUID, petsType),
                info().getPetMaxHP(player, targetPetUUID, petsType),
                info().getPetNowHP(player, targetPetUUID, petsType),
                info().getPetMinDamage(player, targetPetUUID, petsType),
                info().getPetMaxDamage(player, targetPetUUID, petsType),
                info().getPetLevel(player, targetPetUUID, petsType),
                info().getPetMaxExp(player, targetPetUUID, petsType),
                info().getPetNowExp(player, targetPetUUID, petsType),
                info().getPetMaxFood(player, targetPetUUID, petsType),
                info().getPetNowFood(player, targetPetUUID, petsType),
                apEnable, info().getPetApAttribute(playerUUID, targetPetUUID, petsType),
                buffAPEnable, info().getPetBuffAP(playerUUID, targetPetUUID, petsType),
                warehouseType);
        SaveDelPet.removePet(playerUUID, targetPetUUID, petsType);
    }
}
