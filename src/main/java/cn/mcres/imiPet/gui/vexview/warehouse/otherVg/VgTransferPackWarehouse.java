package cn.mcres.imiPet.gui.vexview.warehouse.otherVg;

import cn.mcres.imiPet.gui.vexview.GlobalClickPet;
import cn.mcres.imiPet.gui.vexview.GlobalGet;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexText;
import org.bukkit.entity.Player;

import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class VgTransferPackWarehouse {
    public static VexGui gui(Player player) {
        VexGui vg = new VexGui(vgGuiUrl6, vgGuiX6, vgGuiY6, vgGuiW6, vgGuiH6, vgGuiW6, vgGuiH6);

        vg.addComponent(new VexText(vgTextX8, vgTextY8, vgTextString8));

        new GlobalClickPet(vg, player, "vgTransferPackWarehouse", vgButtonID27, vgButtonX27, vgButtonY27, vgButtonW27, vgButtonH27, vgButtonAddX27).addButton();

        return vg;
    }
}
