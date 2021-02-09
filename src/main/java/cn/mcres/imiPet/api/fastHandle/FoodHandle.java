package cn.mcres.imiPet.api.fastHandle;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.instal.lib.VaultLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import io.izzel.taboolib.module.locale.TLocale;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;



public class FoodHandle {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 喂养宠物检查物品以及触发脚本
     * @param player
     * @param petUUID
     * @param modelId
     * @param itemStack
     * @param type
     */
    public static void foodItemCheck(Player player, UUID petUUID, String modelId, ItemStack itemStack, String type) {
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);

        for (String etaList : infoManager.getEatList()) {
            String[] eatString = etaList.split(":");
            if (eatString.length == 5) {
                String eatType = eatString[0];
                String eatValue = eatString[1];
                if (!MathTool.isIntNumber(eatString[2])) return;
                int eatAmount = Integer.parseInt(eatString[2]);
                int itemAmount = itemStack.getAmount();
                if (itemAmount < eatAmount) return;
                String scriptType = eatString[3];
                String script = eatString[4];
                switch (eatType) {
                    case "material":
                        Material itemMaterial = itemStack.getType();
                        if (GuiUtil.getMaterial(eatValue) != null) {
                            Material material = GuiUtil.getMaterial(eatValue);
                            if (material.equals(itemMaterial)) {
                                foodItem(player, petUUID, type, itemStack, eatAmount, scriptType, script);
                                break;
                            }
                        }
                    case "name":
                        if (itemStack.hasItemMeta()) {
                            String newEatValue = eatValue.replaceAll("&", "§");
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            if (itemMeta.hasDisplayName()) {
                                if (newEatValue.equals(itemMeta.getDisplayName())) {
                                    foodItem(player, petUUID, type, itemStack, eatAmount, scriptType, script);
                                    break;
                                }
                            }
                        }
                    case "lore":
                        if (itemStack.hasItemMeta()) {
                            String newEatValue = eatValue.replaceAll("&", "§");
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            if (itemMeta.hasLore()) {
                                for (String text : itemMeta.getLore()) {
                                    if (text.equals(newEatValue)) {
                                        foodItem(player, petUUID, type, itemStack, eatAmount, scriptType, script);
                                        break;
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    /**
     * 喂养宠物所触发脚本
     * @param player
     * @param petUUID
     * @param type
     * @param itemStack
     * @param needItemAmount
     * @param scriptType
     * @param script
     */
    public static void foodItem(Player player, UUID petUUID, String type, ItemStack itemStack, int needItemAmount, String scriptType, String script) {
        int itemAmount = itemStack.getAmount();
        itemStack.setAmount(itemAmount-needItemAmount);
        if (scriptType.startsWith("addHP")) {
            double scriptHP = Double.parseDouble(script);
            if (!(scriptHP == 0)) {
                double nowHP = info().getPetNowHP(player, petUUID, type);
                double maxHP = info().getPetMaxHP(player, petUUID, type);
                if (!(nowHP == maxHP)) {
                    double newHP = nowHP + scriptHP;
                    info().setPetNowHP(player, Math.min(newHP, maxHP), petUUID, type);
                    return;
                }
            }
        }
        if (scriptType.startsWith("addFood")) {
            int scriptFood = Integer.parseInt(script);
            if (!(scriptFood == 0)) {
                int nowFood = info().getPetNowFood(player, petUUID, type);
                int maxFood = info().getPetMaxFood(player, petUUID, type);
                if (!(nowFood == maxFood)) {
                    int newFood = nowFood + scriptFood;
                    info().setPetNowFood(player, Math.min(newFood, maxFood), petUUID, type);
                    return;
                }
            }
        }
        if (scriptType.startsWith("command_op")) {
            String command = script.replaceAll("@player", player.getName());
            if (!player.isOp()) {
                try {
                    player.setOp(true);
                    player.performCommand(command);
                } finally {
                    player.setOp(false);
                }
            }else {
                player.performCommand(command);
            }
//            return;
        }
    }

    private static boolean equalsLore(ItemMeta itemMeta, String getLore) {
        for (String lore : itemMeta.getLore()) {
            if (lore.equals(getLore)) return true;
        }
        return false;
    }

    /**
     * 通过经济补充宠物活力
     *
     * @param player 玩家
     * @param petUUID 宠物UUID
     * @param modelId 宠物模型ID
     * @param addFood 补充活力值
     */
    public static void foodMoney(Player player, UUID petUUID, String modelId, int addFood, String type) {
        if (VaultLib.VaultLibEnable) {
            if (info().havePet(player)) {
                Economy economy = VaultLib.economy;
                int petNowFood = info().getPetNowFood(player, petUUID, type);
                int petMaxFood = info().getPetMaxFood(player, petUUID, type);
                ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
                double moneyYaml = /*GetPetsYaml.getDouble(modelId, "cureHP.requirement.money")*/infoManager.getCureRequirementHPMoney();
                if (!(petNowFood == petMaxFood)) {
                    // 如果补充活力少于或等于宠物失去的活力
                    if (addFood <= petMaxFood-petNowFood) {
                        // 补充活力所需经济
                        double needMoney = moneyYaml*addFood;
                        int newFood = info().getPetNowFood(player, petUUID, type)+addFood;
                        haveMoneyToFood(economy, player, needMoney, newFood, petUUID, addFood, type);
                    }else { // 如果补充活力大于宠物失去的活力
                        int addFood2 = petMaxFood-petNowFood;
                        double needMoney = moneyYaml*addFood2;
                        int newFood = info().getPetNowFood(player, petUUID, type)+addFood2;
                        haveMoneyToFood(economy, player, needMoney, newFood, petUUID, addFood2, type);
                    }
                }else {
//                    Msg.send(player, PET_ALREADY_FULL_FOOD);
                    TLocale.sendTo(player, "PET_ALREADY_FULL_FOOD");
                }
            }
        }
    }

    private static void haveMoneyToFood(Economy economy, Player player, double needMoney, int newFood, UUID petUUID, double addHP, String type) {
        if (economy.has(player, needMoney)) {
            economy.withdrawPlayer(player, needMoney);
            info().setPetNowFood(player, newFood, petUUID, type);
            /*for (String string : MONEY_ADD_FOOD) {
                Msg.send(player, string.replaceAll("%imipet_addFood%", String.valueOf(addHP)).replaceAll("%imipet_needMoney%", String.valueOf(needMoney)));
            }*/
            TLocale.sendTo(player, "MONEY_ADD_FOOD", String.valueOf(addHP), String.valueOf(needMoney));
        }else {
//            Msg.send(player, NOT_ENOUGH_MONEY);
            TLocale.sendTo(player, "NOT_ENOUGH_MONEY");
        }
    }
}
