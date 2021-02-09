package cn.mcres.imiPet.gui.vexview;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.data.SaveNewPet;
import cn.mcres.imiPet.build.utils.entity.EntityCreator;
import cn.mcres.imiPet.gui.vexview.evolution.VgEvolution;
import cn.mcres.imiPet.gui.vexview.exp.VgExp;
import cn.mcres.imiPet.gui.vexview.updateInfo.VgUpdateInfo;
import cn.mcres.imiPet.gui.vexview.warehouse.VgWarehouse;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexHoverText;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class GlobalClickPet {
    private VexGui newVg;
    private Player player;
    private String vgString;
    private int id;
    private int x;
    private int y;
    private int w;
    private int h;
    private int addX;

    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public GlobalClickPet(VexGui newVg, Player player, String vgString, int id, int x, int y, int w, int h, int addX) {
        this.newVg = newVg;
        this.player = player;
        this.vgString = vgString;
        this.id = id;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.addX = addX;
    }

    // 实体绘制
    public void addButton() {
        UUID playerUUID = this.player.getUniqueId();
            List<UUID> list = info().getPetsPackList(playerUUID);
        if (!list.isEmpty()) {
            ArmorStand armorStand = (ArmorStand) EntityCreator.create(EntityType.ARMOR_STAND, player.getLocation());
            // 按钮-宠物选择-6个
            for (int i = 0; i<6; i++) {
                int i2 = i;
                if (list.size()> i) {
                    String modelId = info().getPetModelId(player, list.get(i2), "pets");
                    this.newVg.addComponent(new VexButton(id+ i, "", vgButton1Url1, vgButton1Url2, x+w* i +addX* i, y, w, h, pp -> {
                        MapAll.guiSelectPet.put(pp, i2);
                        if (MapAll.guiVgPet.get(pp)!=null) {
                            switch (MapAll.guiVgPet.get(pp)) {
                                case "vgExp":
                                    VexViewAPI.openGui(pp, VgExp.gui(pp));
                                    break;
                                case "vgEvolution":
                                    VexViewAPI.openGui(pp, VgEvolution.gui(pp));
                                    break;
                                case "vgUpdateInfo":
                                    VexViewAPI.openGui(pp, VgUpdateInfo.gui(pp));
                                    break;
                                case "vgTransferPackWarehouse":
                                    boolean apLibEnable = APLib.APLibEnable;
                                    SaveNewPet.TransferPackWarehouse(pp, MapAll.vgPackOrWarehouseTarget.get(pp), list.get(i2), "warehouse", apLibEnable, apLibEnable);
                                    VexViewAPI.openGui(pp, VgWarehouse.gui(pp));
                                    break;
                            }
                        }
                    }, new VexHoverText(Collections.singletonList("&f宠物名称： " + info().getPetName(player, list.get(i2), "pets")))));
                    ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
                    /*int addX = GetPetsYaml.getInt(modelId, "entityDraw.vgHome.small.addX");
                    int x = GetPetsYaml.getInt(modelId, "entityDraw.vgHome.small.x");
                    int y = GetPetsYaml.getInt(modelId, "entityDraw.vgHome.small.y");*/
                    int addX = infoManager.getVgHomeDrawSmallAddX();
                    int x = infoManager.getVgHomeDrawSmallX();
                    int y = infoManager.getVgHomeDrawSmallY();
                    int newX = x + this.x + this.w * i + addX * i;
                    int newY = this.y + y;
                    GlobalGet.vgButtonEntity(armorStand, player, list, i2, this.newVg, newX, newY, this.vgString);
                }
            }
        }
        VexViewAPI.openGui(this.player, this.newVg);
    }
}
