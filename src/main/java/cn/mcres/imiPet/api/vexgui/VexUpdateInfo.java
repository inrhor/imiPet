package cn.mcres.imiPet.api.vexgui;

import cn.mcres.imiPet.gui.vexview.updateInfo.VgUpdateInfo;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexComponents;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VexUpdateInfo {
    private Player player;
    private VexGui vexGui;

    /**
     * 更新信息界面的组件列表，允许添加组件
     */
    public static List<VexComponents> publicVexUpdateInfoComponentsList = new ArrayList<>();

    public VexUpdateInfo(Player player) {
        this.player = player;
        this.vexGui = VgUpdateInfo.gui(this.player);
    }

    public void addComponentToVg() {
        if (!publicVexUpdateInfoComponentsList.isEmpty()) {
            for (VexComponents vexComponents : publicVexUpdateInfoComponentsList) {
                this.vexGui.addComponent(vexComponents);
            }
        }
        VexViewAPI.openGui(this.player, this.vexGui);
    }
}
