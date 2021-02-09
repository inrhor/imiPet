package cn.mcres.imiPet.gui.vanilla.exp;

import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vanilla.GuiSelectPet;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.gui.vanilla.home.GuiHome;
import static cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml.*;

import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuiExpBox implements Listener {
    private static String title = "§l§a§c§c§3§3§3§l§r§8§5§d§2";

    public static void open(Player e) {
        Inventory gui = Bukkit.createInventory(e, 6*9, title+expBoxTitle);

        gui.setItem(11, GuiUtil.petShow(e));

        gui.setItem(12, GuiUtil.itemBuildGui(giveExpExpBoxMaterial, giveExpExpBoxName, ReplaceAll.expBoxReplace(giveExpExpBoxLore, e)));

        gui.setItem(49, GuiUtil.itemBuildGui(closeMaterial, closeName));
        gui.setItem(48, GuiUtil.itemBuildGui(previousPageMaterial, previousPageName));
        gui.setItem(50, GuiUtil.itemBuildGui(previousPageMaterial, previousPageName));

        e.openInventory(gui);
    }

    @EventHandler
    void onClick(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(title)) {
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            if (slot == 48 || slot == 50) {
                GuiHome.open(player);
            }else if (slot == 11) {
                GuiSelectPet.previousPageGui.put(player, "expBox");
                GuiSelectPet.open(player);
            }else if (slot == 12) {
                GuiUtil.coolDownExp.put(player, 30);
                GuiUtil.enterType.put(player, "exp");
                TLocale.sendTo(player, "PLEASE_ENTER_TEXT");
                player.closeInventory();
            }
        }
    }
}
