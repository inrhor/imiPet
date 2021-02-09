package cn.mcres.imiPet.gui.vanilla;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.data.SaveNewPet;
import cn.mcres.imiPet.api.fastHandle.CureHandle;
import cn.mcres.imiPet.api.fastHandle.ExpBoxHandle;
import cn.mcres.imiPet.api.fastHandle.FoodHandle;
import cn.mcres.imiPet.api.fastHandle.UpdatePetName;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.model.ModelInfoManager;

import static cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml.*;
import java.util.*;

import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiUtil implements Listener {
    // 点击后三十秒内在聊天输入以治疗宠物
    public static HashMap<Player, Integer> coolDownCure = new LinkedHashMap<>();
    // 点击后三十秒内在聊天输入以喂养宠物
    public static HashMap<Player, Integer> coolDownFood = new LinkedHashMap<>();
    // 点击后三十秒内在聊天输入将经验给予宠物
    public static HashMap<Player, Integer> coolDownExp = new LinkedHashMap<>();
    // 点击后三十秒内在聊天输入以转让宠物
    public static HashMap<Player, Integer> coolDownTransfer = new LinkedHashMap<>();
    // 点击后三十秒内在聊天输入修改宠物名称
    public static HashMap<Player, Integer> coolDownRename = new LinkedHashMap<>();
    // 点击后三十秒内输入的内容触发何种方法
    public static HashMap<Player, String> enterType = new LinkedHashMap<>();
    // 清除
    public static void removeMap(Player player) {
        coolDownCure.remove(player);
        coolDownFood.remove(player);
        coolDownTransfer.remove(player);
        coolDownExp.remove(player);
        coolDownRename.remove(player);
        enterType.remove(player);
    }

    // 定时器计时器
    public static void runCoolDownTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (enterType.get(player) != null) {
                        String enterTypeString = enterType.get(player);
                        switch (enterTypeString) {
                            case "cure":
                                int cureCD = coolDownCure.get(player);
                                if (cureCD <= 0) {
                                    coolDownCure.remove(player);
                                    enterType.remove(player);
                                    break;
                                }
                                coolDownCure.put(player, cureCD-1);
                                break;
                            case "food":
                                int foodCD = coolDownFood.get(player);
                                if (foodCD <= 0) {
                                    coolDownFood.remove(player);
                                    enterType.remove(player);
                                    break;
                                }
                                coolDownFood.put(player, foodCD-1);
                                break;
                            case "transfer":
                                int transferCD = coolDownTransfer.get(player);
                                if (transferCD <= 0) {
                                    coolDownTransfer.remove(player);
                                    enterType.remove(player);
                                    break;
                                }
                                coolDownTransfer.put(player, transferCD-1);
                                break;
                            case "exp":
                                int expCD = coolDownExp.get(player);
                                if (expCD <= 0) {
                                    coolDownExp.remove(player);
                                    enterType.remove(player);
                                    break;
                                }
                                coolDownExp.put(player, expCD-1);
                                break;
                            case "rename":
                                int renameCD = coolDownRename.get(player);
                                if (renameCD <= 0) {
                                    coolDownRename.remove(player);
                                    enterType.remove(player);
                                    break;
                                }
                                coolDownRename.put(player, renameCD-1);
                                break;
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(ImiPet.loader.getPlugin(), 1L, 20L);
    }

    @EventHandler
    void playerEnterChat(AsyncPlayerChatEvent ev) {
        Player player = ev.getPlayer();
        UUID playerUUID = player.getUniqueId();
        List<UUID> list = info().getPetsPackList(playerUUID);
        if (list.isEmpty()) return;
        if (GuiSelectPet.guiSelectPet.get(player) == null) return;
        if (enterType.get(player) == null) return;
        UUID petUUID = list.get(GuiSelectPet.guiSelectPet.get(player));
        String modelId = info().getPetModelId(player, petUUID, "pets");
        String enterTypeString = enterType.get(player);
        ev.setCancelled(true);
        String msg = ev.getMessage();
        switch (enterTypeString) {
            case "cure":
                if (MathTool.isIntNumber(msg)) {
                    Bukkit.getScheduler().runTask(ImiPet.loader.getPlugin(), () -> {
                        CureHandle.cureMoney(player, petUUID, modelId, Double.parseDouble(msg), "pets");
                    });
                    removeMap(player);
                }else {
//                    Msg.send(player, NOT_INT);
                    TLocale.sendTo(player, "NOT_INT");
                }
                break;
            case "food":
                if (MathTool.isIntNumber(msg)) {
                    Bukkit.getScheduler().runTask(ImiPet.loader.getPlugin(), () -> {
                        FoodHandle.foodMoney(player, petUUID, modelId, Integer.parseInt(msg), "pets");
                    });
                    removeMap(player);
                }else {
//                    Msg.send(player, NOT_INT);
                    TLocale.sendTo(player, "NOT_INT");
                }
                break;
            case "transfer":
                Player newOwner = Bukkit.getPlayer(msg);
                if (newOwner!=null) {
                    boolean apLibEnable = APLib.APLibEnable;
                    SaveNewPet.TransferPet(player, newOwner, petUUID, apLibEnable, apLibEnable);
                }else {
//                    Msg.send(player, PLAYER_IS_NOT_ONLINE);
                    TLocale.sendTo(player, "PLAYER_IS_NOT_ONLINE");
                }
                removeMap(player);
                break;
            case "exp":
                if (MathTool.isIntNumber(msg)) {
                    ExpBoxHandle.givePet(player, petUUID, Integer.parseInt(msg));
                    removeMap(player);
                }else {
//                    Msg.send(player, NOT_INT);
                    TLocale.sendTo(player, "NOT_INT");
                }
                break;
            case "rename":
                UpdatePetName.run(playerUUID, petUUID, msg, false, "pets");
                break;
        }
    }

    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static ItemStack petShow(Player player) {
        List<UUID> list = info().getPetsPackList(player.getUniqueId());
        if (GuiSelectPet.guiSelectPet.get(player) == null || list.isEmpty()) {
            return GuiUtil.itemBuildGui(noSelectPetMaterial, noSelectPetName);
        }else {
            UUID petUUID = list.get(GuiSelectPet.guiSelectPet.get(player));
            String modelId = info().getPetModelId(player, petUUID, "pets");
            ModelInfoManager modelInfoManager = ModelInfoManager.petModelList.get(modelId);
            String name = /*GetPetsYaml.getString(modelId, "entityDraw.itemName")*/modelInfoManager.getAnimationItemNameIdle();
            int customModelData = modelInfoManager.getAnimationCustomModelDataIdle();
            return GuiSelectPet.petShowItem(name, ReplaceAll.petReplaceAll(petInfoLore, player, petUUID), customModelData);
        }
    }

    public static ItemStack itemBuildGui(Material material, String name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack itemBuildGui(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static List<String> titleList = Arrays.asList(
            "§l§a§c§c§3§3§3§l§r§8§5§d§1",
            "§l§a§c§c§3§3§3§l§r§8§5§d§2",
            "§l§a§c§c§3§3§3§l§r§8§5§d§3",
            "§l§a§c§c§3§3§3§l§r§8§5§d§4",
            "§l§a§c§c§3§3§3§l§r§8§5§d§5",
            "§l§a§c§c§3§3§3§l§r§8§5§d§6"
    );

    public static boolean isPetGui(String clickGuiTitle) {
        for (String title : titleList) {
            if (clickGuiTitle.startsWith(title)) return true;
        }
        return false;
    }

    public static Material getMaterial(String material) {
        return Material.valueOf(material.substring( material.indexOf( ":" ) + 1 ).toUpperCase());
    }

    public static String getName(String itemName) {
        return itemName.replaceAll("&", "§");
    }
}
