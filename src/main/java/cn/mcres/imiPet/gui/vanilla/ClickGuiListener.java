package cn.mcres.imiPet.gui.vanilla;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

public class ClickGuiListener implements Listener {
    @EventHandler
    void onClick(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (GuiUtil.isPetGui(gui.getTitle())) {
            ev.setCancelled(true);
            if (ev.getRawSlot() == 49) {
                ev.getWhoClicked().closeInventory();
            }
        }
    }
}
