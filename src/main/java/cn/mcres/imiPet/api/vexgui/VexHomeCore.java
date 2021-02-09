package cn.mcres.imiPet.api.vexgui;

import cn.mcres.imiPet.gui.vexview.home.VgHome;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexComponents;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VexHomeCore {
    private Player player;
    private VexGui vexGui;

    /**
     * 主界面的组件列表，允许添加组件
     */
    public static List<VexComponents> publicVexHomeComponentsList = new ArrayList<>();

    public VexHomeCore(Player player) {
        this.player = player;
        this.vexGui = VgHome.gui(this.player);
    }

    public void addCompToVg() {
        if (!publicVexHomeComponentsList.isEmpty()) {
            for (VexComponents vexComponents : publicVexHomeComponentsList) {
                this.vexGui.addComponent(vexComponents);
            }
        }
        VexViewAPI.openGui(this.player, this.vexGui);
    }
}
