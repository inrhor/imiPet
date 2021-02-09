package cn.mcres.imiPet.gui.vanilla.home;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.data.SaveDelPet;
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle;
import cn.mcres.imiPet.api.other.GetBooleanValue;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vanilla.GuiSelectPet;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.gui.vanilla.evolution.GuiEvolution;
import cn.mcres.imiPet.gui.vanilla.exp.GuiExpBox;
import cn.mcres.imiPet.gui.vanilla.updateInfo.GuiUpdateInfo;
import cn.mcres.imiPet.gui.vanilla.warehouse.GuiWarehouse;

import static cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml.*;
import java.util.List;
import java.util.UUID;

import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuiHome implements Listener {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    private static String title = "§l§a§c§c§3§3§3§l§r§8§5§d§3";

    public static void open(Player e) {
        Inventory gui = Bukkit.createInventory(e, 6*9, title+homeTitle);

        gui.setItem(11, GuiUtil.petShow(e));

        gui.setItem(12, GuiUtil.itemBuildGui(warehouseMaterial, warehouseName));

        gui.setItem(13, GuiUtil.itemBuildGui(expBoxMaterial, expBoxName));

        gui.setItem(14, GuiUtil.itemBuildGui(updatePetInfoMaterial, updatePetInfoName));

        gui.setItem(15, GuiUtil.itemBuildGui(evolutionMaterial, evolutionName));

        gui.setItem(20, GuiUtil.itemBuildGui(followPetHomeMaterial, followPetHomeName));

        gui.setItem(21, GuiUtil.itemBuildGui(curePetHomeMaterial, curePetHomeName));

        gui.setItem(22, GuiUtil.itemBuildGui(foodPetHomeMaterial, foodPetHomeName));

        gui.setItem(23, GuiUtil.itemBuildGui(transferHomeMaterial, transferHomeName));

        gui.setItem(24, GuiUtil.itemBuildGui(releaseHomeMaterial, releaseHomeName));

        gui.setItem(49, GuiUtil.itemBuildGui(closeMaterial, closeName));

        e.openInventory(gui);
    }

    @EventHandler
    void onClick(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(title)) {
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            if (slot == 13) {
                GuiExpBox.open(player);
            }else if (slot == 12) {
                GuiWarehouse.open(player, 0, 1);
            }else if (slot == 14) {
                GuiUpdateInfo.open(player);
            }else if (slot == 15) {
                GuiEvolution.open(player);
            }else if (slot == 11) {
                GuiSelectPet.previousPageGui.put(player, "home");
                GuiSelectPet.open(player);
            }
            UUID playerUUID = player.getUniqueId();
            List<UUID> list = info().getPetsPackList(playerUUID);
            if (list.isEmpty()) return;
            if (GuiSelectPet.guiSelectPet.get(player) == null) return;
            UUID petUUID = list.get(GuiSelectPet.guiSelectPet.get(player));
            if (slot == 20) {
                if (!GetBooleanValue.inDisablePetWorld(player)) {
                    FollowSetHandle.runInfo(player, petUUID);
                    player.closeInventory();
                }else {
//                    Msg.send(player, DISABLE_PET_FOLLOW_WORLD);
                    TLocale.sendTo(player, "DISABLE_PET_FOLLOW_WORLD");
                }
            }else if (slot == 21) {
                GuiUtil.coolDownCure.put(player, 30);
                GuiUtil.enterType.put(player, "cure");
//                Msg.send(player, PLEASE_ENTER_TEXT);
                TLocale.sendTo(player, "PLEASE_ENTER_TEXT");
                player.closeInventory();
            }else if (slot == 22) {
                GuiUtil.coolDownFood.put(player, 30);
                GuiUtil.enterType.put(player, "food");
//                Msg.send(player, PLEASE_ENTER_TEXT);
                TLocale.sendTo(player, "PLEASE_ENTER_TEXT");
                player.closeInventory();
            }else if (slot == 23) {
                GuiUtil.coolDownTransfer.put(player, 30);
                GuiUtil.enterType.put(player, "transfer");
//                Msg.send(player, PLEASE_ENTER_TEXT);
                TLocale.sendTo(player, "PLEASE_ENTER_TEXT");
                player.closeInventory();
            }else if (slot == 24) {
                GuiSelectPet.guiSelectPet.remove(player);
                SaveDelPet.removePet(playerUUID, petUUID, "pets");
//                Msg.send(player, SUCCESSFUL_RELEASE_PET);
                TLocale.sendTo(player, "SUCCESSFUL_RELEASE_PET");
                GuiHome.open(player);
            }
        }
    }
}
