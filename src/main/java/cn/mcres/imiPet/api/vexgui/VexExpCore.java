package cn.mcres.imiPet.api.vexgui;

import cn.mcres.imiPet.gui.vexview.exp.VgExp;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexComponents;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VexExpCore {
    private Player player;
    private VexGui vexGui;

    /**
     * 经验盒界面的组件列表，允许添加组件
     */
    public static List<VexComponents> publicVexExpComponentsList = new ArrayList<>();

    public VexExpCore(Player player) {
        this.player = player;
        this.vexGui = VgExp.gui(this.player);
    }

    public void addComponentToVg() {
        if (!publicVexExpComponentsList.isEmpty()) {
            for (VexComponents vexComponents : publicVexExpComponentsList) {
                this.vexGui.addComponent(vexComponents);
            }
        }
        VexViewAPI.openGui(this.player, this.vexGui);
    }
}
