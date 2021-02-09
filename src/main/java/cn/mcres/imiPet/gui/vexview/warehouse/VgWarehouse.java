package cn.mcres.imiPet.gui.vexview.warehouse;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.data.SaveNewPet;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.gui.vexview.home.VgHome;
import cn.mcres.imiPet.gui.vexview.warehouse.otherVg.VgTransferPackWarehouse;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.other.MapAll;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class VgWarehouse {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static VexGui gui(Player player) {
        VexGui vg = new VexGui(vgGuiUrl2, vgGuiX2, vgGuiY2, vgGuiW2, vgGuiH2, vgGuiW2, vgGuiH2);

        UUID playerUUID = player.getUniqueId();
        int warehouseAmount = info().getPetsWarehouseAmount(playerUUID);

        vg.addComponent(new VexButton(vgButtonID26, vgButtonText26, vgButtonUrl126, vgButtonUrl226, vgButtonX26, vgButtonY26, vgButtonW26, vgButtonH26, pp -> {
            VexViewAPI.openGui(pp, VgHome.gui(pp));
        }));

        VexScrollingList vsl = new VexScrollingList(vgVslX2, vgVslY2, vgVslW2, vgVslH2, vgVslFull2+(warehouseAmount*vgVslAddFull2));
        boolean apLibEnable = APLib.APLibEnable;
        int i=0;
        for (UUID uuid : info().getPetsWarehouseList(playerUUID)) {
            vsl.addComponent(new VexImage(vslImageUrl11, vslImageX11, vslImageY11+(i*vslImageAddY11), vslImageW11, vslImageH11));

            // 显示更多信息
//            String imageShowSmall = GetPetsYaml.getString(info().getPetModelId(player, uuid, "warehouse"), "imageShowSmall");
            List<String> newVslButtonHoverText6 = ReplaceAll.petWarehouseReplaceAll(vslButtonHoverText6, player, uuid);
            int newHeadY = vslButtonY6+(i*vslButtonAddY6);
            vsl.addComponent(new VexButton(vslButtonID6+i+"wa", vslButtonText6, vslButtonUrl16, vslButtonUrl26, vslButtonX6, newHeadY, vslButtonW6, vslButtonH6, pp -> {
            }, new VexHoverText(newVslButtonHoverText6)));

            // 转移到背包
//            vsl.addComponent(new VexTextField(vslVTFX1, vslVTFY1, vslVTFH1, vslVTFW1, Integer.MAX_VALUE, vslVTFID1));
            int newClickY = vslButtonY7+(i*vslButtonAddY7);
            vsl.addComponent(new VexButton(vslButtonID7+i+"bu", vslButtonText7, vslButtonUrl17, vslButtonUrl27, vslButtonX7, newClickY, vslButtonW7, vslButtonH7, pp -> {
                if (info().getPetsPackAmount(playerUUID) < 6) {
                    SaveNewPet.TransferPackWarehouse(pp, uuid, "warehouse", apLibEnable, apLibEnable);
                    VexViewAPI.openGui(pp, gui(pp));
                }else {
                    MapAll.vgPackOrWarehouseTarget.put(pp, uuid);
                    MapAll.guiVgPet.put(pp, "vgTransferPackWarehouse");
                    VexViewAPI.openGui(pp, VgTransferPackWarehouse.gui(pp));
                }
            }));

//            String modelId = info().getPetModelId(player, uuid, "warehouse");

            /*ArmorStand armorStand = (ArmorStand) EntityCreator.create(EntityType.ARMOR_STAND, player.getLocation());

            String itemName = GetPetsYaml.getString(modelId, "entityDraw.itemName");

            int customModelData = GetPetsYaml.getInt(modelId, "entityDraw.customModelData");
            int size = GetPetsYaml.getInt(modelId, "entityDraw.vgWarehouse.small.size");
            int addY = GetPetsYaml.getInt(modelId, "entityDraw.vgWarehouse.small.addY");
            int x = GetPetsYaml.getInt(modelId, "entityDraw.vgWarehouse.small.x");
            int y = GetPetsYaml.getInt(modelId, "entityDraw.vgWarehouse.small.y");

            int newY = y+(i*addY);

            ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(itemName);
            if (!ImiPet.getServerInfo().isOldVersion()) {
                itemMeta.setCustomModelData(customModelData);
            }
            itemStack.setItemMeta(itemMeta);

            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setHelmet(itemStack);

            VexEntityDraw vexEntityDraw = new VexEntityDraw(x, newY, size, armorStand);
            vsl.addComponent(vexEntityDraw);*/



            vsl.addComponent(new VexText(vslTextX1, vslTextY1+i*vslTextAddY1, ReplaceAll.petWarehouseReplaceAll(vslTextString1, player, uuid)));

            i++;
        }

        vg.addComponent(vsl);
        return vg;
    }
}
