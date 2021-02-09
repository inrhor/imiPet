package cn.mcres.imiPet.api.vexgui;

import cn.mcres.imiPet.gui.vexview.exp.VgExp;
import cn.mcres.imiPet.gui.vexview.warehouse.VgWarehouse;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexComponents;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VexWarehouse {
    private Player player;
    private VexGui vexGui;

    /**
     * 仓库界面的组件列表，允许添加组件
     */
    public static List<VexComponents> publicVexWarehouseComponentsList = new ArrayList<>();

    public VexWarehouse(Player player) {
        this.player = player;
        this.vexGui = VgWarehouse.gui(this.player);
    }

    public void addComponentToVg() {
        if (!publicVexWarehouseComponentsList.isEmpty()) {
            for (VexComponents vexComponents : publicVexWarehouseComponentsList) {
                this.vexGui.addComponent(vexComponents);
            }
        }
        VexViewAPI.openGui(this.player, this.vexGui);
    }
}
