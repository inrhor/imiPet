package cn.mcres.imiPet.gui.vexview.updateInfo;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.entity.EntityCreator;
import cn.mcres.imiPet.gui.vexview.GlobalClickPet;
import cn.mcres.imiPet.gui.vexview.GlobalGet;
import cn.mcres.imiPet.gui.vexview.GlobalVsl;
import cn.mcres.imiPet.gui.vexview.updateInfo.expansion.AddUpdateNameComponent;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class VgUpdateInfo {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static VexGui gui(Player player) {
        VexGui vg = new VexGui(vgGuiUrl4, vgGuiX4, vgGuiY4, vgGuiW4, vgGuiH4, vgGuiW4, vgGuiH4);
        VexScrollingList vsl = new VexScrollingList(vgVsl1X, vgVsl1Y, vgVsl1W, vgVsl1H, vgVsl1Full);
        UUID playerUUID = player.getUniqueId();

        // 图片-宠物模型背景
        vg.addComponent(new VexImage(vgImageUrl10, vgImageX10, vgImageY10, vgImageW10, vgImageH10));
        // 图片-宠物信息背景
        vg.addComponent(new VexImage(vgImageUrl11, vgImageX11, vgImageY11, vgImageW11, vgImageH11));

        if (info().havePet(player)) {
            List<UUID> list = info().getPetsPackList(playerUUID);
            if (!list.isEmpty() && MapAll.guiSelectPet.get(player) != null) {
                UUID petUUID = list.get(MapAll.guiSelectPet.get(player));
//                String imageShowBigUrl = GetPetsYaml.getString(info().getPetModelId(player, petUUID, "pets"), "imageShowBig");
//                vg.addComponent(new VexImage(imageShowBigUrl, vgImageX12, vgImageY12, vgImageW12, vgImageH12));

                String modelId = info().getPetModelId(player, petUUID, "pets");

                ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);

                /*String itemName = GetPetsYaml.getString(modelId, "entityDraw.itemName");
                int customModelData = GetPetsYaml.getInt(modelId, "entityDraw.customModelData");
                int bigSize = GetPetsYaml.getInt(modelId, "entityDraw.vgUpdateInfo.big.size");
                int bigX = GetPetsYaml.getInt(modelId, "entityDraw.vgUpdateInfo.big.x");
                int bigY = GetPetsYaml.getInt(modelId, "entityDraw.vgUpdateInfo.big.y");*/
                String itemName = infoManager.getAnimationItemNameIdle();
                int customModelData = infoManager.getAnimationCustomModelDataIdle();
                int bigSize = infoManager.getVgUpdateInfoBigSize();
                int bigX = infoManager.getVgUpdateInfoBigX();
                int bigY = infoManager.getVgUpdateInfoBigY();

                ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(itemName);
                if (!ImiPet.loader.getServerInfo().isOldVersion()) {
                    itemMeta.setCustomModelData(customModelData);
                }
                itemStack.setItemMeta(itemMeta);
                ArmorStand armorStand = (ArmorStand) EntityCreator.create(EntityType.ARMOR_STAND, player.getLocation());
                armorStand.setSmall(true);
                armorStand.setVisible(false);
                armorStand.setHelmet(itemStack);
                VexEntityDraw vexEntityDraw = new VexEntityDraw(bigX, bigY, bigSize, armorStand);
                vg.addComponent(vexEntityDraw);

                List<String> newText = new ArrayList<>();
                String selectPetName = info().getPetName(player, petUUID, "pets");
                for (String text : vgTextString7) {
                    newText.add(text.replaceAll("%updateinfo_name%", selectPetName));
                }
                // 文字-宠物信息
                vg.addComponent(new VexText(vgTextX7, vgTextY7, newText));

                // 按钮-更改名称
                vg.addComponent(new VexButton(vgButtonID20, vgButtonText20, vgButtonUrl120, vgButtonUrl220, vgButtonX20, vgButtonY20, vgButtonW20, vgButtonH20, pp -> {
                    VexViewAPI.openGui(pp, AddUpdateNameComponent.open(pp, petUUID));
                }, new VexHoverText(vgButtonHoverText20)));
            }
        }

        // 宠物选择框
        vg.addComponent(new VexButton(vgButtonID18, vgButtonText18, vgButtonUrl118, vgButtonUrl218, vgButtonX18, vgButtonY18, vgButtonW18, vgButtonH18, pp -> {
                new GlobalClickPet(vg, pp, "vgUpdateInfo", vgButtonID19, vgButtonX19, vgButtonY19, vgButtonW19, vgButtonH19, vgButtonAddX19).addButton();
        }));
        GlobalGet.showHeadEntityDraw(vg, player, "vgUpdateInfo");

        // 列表
        GlobalVsl.addVsl(vg, vsl);

        return vg;
    }
}
