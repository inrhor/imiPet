package cn.mcres.imiPet.gui.vanilla;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.data.SaveNewPet;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.gui.vanilla.evolution.GuiEvolution;
import cn.mcres.imiPet.gui.vanilla.exp.GuiExpBox;
import cn.mcres.imiPet.gui.vanilla.home.GuiHome;
import cn.mcres.imiPet.gui.vanilla.updateInfo.GuiUpdateInfo;
import cn.mcres.imiPet.gui.vanilla.warehouse.GuiWarehouse;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import static cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiSelectPet implements Listener {
    private static String title = "§l§a§c§c§3§3§3§l§r§8§5§d§6";

    public static ItemStack petShowItem(String name, List<String> lore, int customModelData) {
        ItemStack petShowItem = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta itemMeta = petShowItem.getItemMeta();
        if (!ImiPet.loader.getServerInfo().isOldVersion()) {
            itemMeta.setCustomModelData(customModelData);
        }
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        petShowItem.setItemMeta(itemMeta);
        return petShowItem;
    }

    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    // 选择的宠物数位
    public static HashMap<Player, Integer> guiSelectPet = new LinkedHashMap<>();
    // 打开选择数位界面原因，为了分辨于仓库替换宠物还是单纯选择宠物
    public static HashMap<Player, String> guiSelectPetOpenCause = new LinkedHashMap<>();
    // 为了点击后返回某页面
    public static HashMap<Player, String> previousPageGui = new LinkedHashMap<>();

    public static void open(Player player) {
        Inventory gui = Bukkit.createInventory(player, 5*9, title+selectPetTitle);

        UUID playerUUID = player.getUniqueId();
        List<UUID> list = info().getPetsPackList(playerUUID);

        for (int i = 0; i < 6; i++) {
            gui.setItem(i+19, GuiUtil.itemBuildGui(emptySelectPetMaterial, emptySelectPetName));
        }

        if (!list.isEmpty()) {
            // 按钮-宠物选择-6个
            for (int i = 0; i < 6; i++) {
                if (list.size() > i) {
                    UUID petUUID = list.get(i);
                    String modelId = info().getPetModelId(player, petUUID, "pets");
                    if (ModelInfoManager.petModelList.get(modelId) != null) {
                        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
                        String name = infoManager.getAnimationItemNameIdle();
                        int customModelData = infoManager.getAnimationCustomModelDataIdle();
                        gui.setItem(i + 19, petShowItem(name, ReplaceAll.petReplaceAll(petInfoLore, player, petUUID), customModelData));
                    }
                }
            }
        }

        gui.setItem(25, GuiUtil.itemBuildGui(closeMaterial, closeName));

        player.openInventory(gui);
    }

    @EventHandler
    void onClick(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(title)) {
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            if (slot == 25) {
                player.closeInventory();
            }
            if (ev.getCurrentItem()!=null && ev.getCurrentItem().getType().equals(emptySelectPetMaterial)) return;
            UUID playerUUID = player.getUniqueId();
            List<UUID> petsPackList = info().getPetsPackList(playerUUID);

            boolean apLibEnable = APLib.APLibEnable;

            for (int i = 0; i < 6; i++) {
                int petSlot = i+19;
                if (slot == petSlot) {
                    if (guiSelectPetOpenCause.get(player) != null) {
                        SaveNewPet.TransferPackWarehouse(player, MapAll.vgPackOrWarehouseTarget.get(player) ,petsPackList.get(i), "warehouse", apLibEnable, apLibEnable);
                    }else {
                        guiSelectPet.put(player, i);
                    }
                    openPreviousPageGui(player);
                }
            }
        }
    }

    private void openPreviousPageGui(Player player) {
        if (previousPageGui.get(player) == null) return;
        String previousPageGui = GuiSelectPet.previousPageGui.get(player);
        switch (previousPageGui) {
            case "home":
                GuiHome.open(player);
                break;
            case "expBox":
                GuiExpBox.open(player);
                break;
            case "updateInfo":
                GuiUpdateInfo.open(player);
                break;
            case "evolution":
                GuiEvolution.open(player);
                break;
            case "warehouse":
                guiSelectPetOpenCause.remove(player);
                GuiWarehouse.open(player, 0, 1);
                break;
        }
    }
}
