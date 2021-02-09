package cn.mcres.imiPet.gui.vanilla.warehouse;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.data.SaveNewPet;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.gui.vanilla.GuiSelectPet;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.gui.vanilla.home.GuiHome;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import static cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml.*;
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class GuiWarehouse implements Listener {
    private static HashMap<Player, Integer> guiValue = new LinkedHashMap<>();
    private static HashMap<Player, Integer> guiPage = new LinkedHashMap<>();

    // 将仓库的各个数位写入宠物，方便调用
    private static HashMap<Player, List<UUID>> getWarehouseGuiPet = new LinkedHashMap<>();

    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    private static String title = "§l§a§c§c§3§3§3§l§r§8§5§d§5";

    public static void open(Player player, int value, int page) {
        getWarehouseGuiPet.remove(player);
        guiValue.put(player, value);
        guiPage.put(player, page);

        Inventory gui = Bukkit.createInventory(player, 6*9, title+warehouseTitle);

        setItemLoc(player, gui, value, 11);

        gui.setItem(49, GuiUtil.itemBuildGui(closeMaterial, closeName));
        gui.setItem(48, GuiUtil.itemBuildGui(previousPageMaterial, previousPageName));
        gui.setItem(50, GuiUtil.itemBuildGui(nextPageMaterial, nextPageName));

        player.openInventory(gui);
    }

    private static void setItemLoc(Player player, Inventory gui, int value, int startLoc) {
        int addLoc = 0;
        UUID playerUUID = player.getUniqueId();
        List<UUID> list = info().getPetsWarehouseList(playerUUID);
        List<UUID> warehouseList = new ArrayList<>();
        for (int i = value; i < value+5; i++) {
            if (!list.isEmpty() && list.size() > i) {
                UUID petUUID = list.get(i);
                String modelId = info().getPetModelId(player, petUUID, "warehouse");
                int itemLoc = startLoc + addLoc;
                if (ModelInfoManager.petModelList.get(modelId) != null) {
                    ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
                    String name = infoManager.getAnimationItemNameIdle();
                    int customModelData = infoManager.getAnimationCustomModelDataIdle();
                    gui.setItem(
                            itemLoc,
                            GuiSelectPet.petShowItem(name, ReplaceAll.petWarehouseReplaceAll(warehousePetInfo, player, petUUID), customModelData));
                }else {
                    gui.setItem(itemLoc, GuiUtil.itemBuildGui(emptySelectPetMaterial, emptySelectPetName));
                }
                gui.setItem(itemLoc + 9, GuiUtil.itemBuildGui(intoPackWarehouseMaterial, intoPackWarehouseName));
                guiValue.put(player, guiValue.get(player)+1);
                warehouseList.add(petUUID);
                addLoc++;
            }
        }
        getWarehouseGuiPet.put(player, warehouseList);
    }

    @EventHandler
    void onClick(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(title)) {
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            UUID playerUUID = player.getUniqueId();
            List<UUID> list = info().getPetsWarehouseList(playerUUID);
            int i = guiValue.get(player);
            int page = guiPage.get(player);
            if (!list.isEmpty()) {
                if (slot == 48) {
                    if (i > 5) {
                        open(player, i - getItemInt(ev.getInventory()) - 5, page - 1);
                    }else {
                        GuiHome.open(player);
                    }
                }else if (slot == 50) {
                    if (list.size() > (5 * guiPage.get(player))) {
                        open(player, i, guiPage.get(player) + 1);
                    }
                }
                if (getWarehouseGuiPet.get(player) !=null && !getWarehouseGuiPet.get(player).isEmpty()) {
                    List<UUID> warehouseList = getWarehouseGuiPet.get(player);
                    boolean apLibEnable = APLib.APLibEnable;
                    for (int guiInt = 0; guiInt < warehouseList.size(); guiInt++) {
                        if (slot == guiInt + 20) {
                            UUID petUUID = warehouseList.get(guiInt);
                            if (info().getPetsPackAmount(playerUUID) < 6) {
                                SaveNewPet.TransferPackWarehouse(player, petUUID, "warehouse", apLibEnable, apLibEnable);
                                open(player, 0, 1);
                            }else {
                                GuiSelectPet.guiSelectPetOpenCause.put(player, "intoPack");
                                MapAll.vgPackOrWarehouseTarget.put(player, petUUID);
                                GuiSelectPet.previousPageGui.put(player, "warehouse");
                                GuiSelectPet.open(player);
                            }
                        }
                    }
                }
            }else {
                GuiHome.open(player);
            }
        }
    }

    private static int getItemInt(Inventory gui) {
        int s = 0;
        for ( ItemStack item : gui.getContents() ) {
            if ( item != null && item.getType().equals(Material.DIAMOND_HOE) ){
                s++;
            }
        }
        return s;
    }
}
