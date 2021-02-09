package cn.mcres.imiPet.api.vexgui;

import cn.mcres.imiPet.gui.vexview.evolution.VgEvolution;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexComponents;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VexEvolution {
    private Player player;
    private VexGui vexGui;

    /**
     * 进化仓界面的组件列表，允许添加组件
     */
    public static List<VexComponents> publicVexEvolutionComponentsList = new ArrayList<>();

    public VexEvolution(Player player) {
        this.player = player;
        this.vexGui = VgEvolution.gui(this.player);
    }

    public void addComponentToVg() {
        if (!publicVexEvolutionComponentsList.isEmpty()) {
            for (VexComponents vexComponents : publicVexEvolutionComponentsList) {
                this.vexGui.addComponent(vexComponents);
            }
        }
        VexViewAPI.openGui(this.player, this.vexGui);
    }
}
