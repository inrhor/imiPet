package cn.mcres.imiPet.gui.vexview;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.entity.EntityCreator;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexEntityDraw;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GlobalGet {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    // 选择框-实体绘制
    public static void vgButtonEntity(ArmorStand armorStand, Player player, List<UUID> list, int index, VexGui vg, int smallX, int smallY, String vgString) {
        /*new BukkitRunnable() {
            @Override
            public void run() {
                // 异步处理，防止阻塞
                Bukkit.getScheduler().runTaskAsynchronously(ImiPet.loader.getPlugin(), () -> {

                });
            }
        }.runTaskLater(ImiPet.loader.getPlugin(), 1);*/
            String smallModelId = info().getPetModelId(player, list.get(index), "pets");

            ModelInfoManager infoManager = ModelInfoManager.petModelList.get(smallModelId);

            String smallItemName = infoManager.getAnimationItemNameIdle();
            int smallCustomModelData = infoManager.getAnimationCustomModelDataIdle();
            int smallSize = smallSize(infoManager, vgString);
            /*int smallCustomModelData = GetPetsYaml.getInt(smallModelId, "entityDraw.customModelData");
            int smallSize = GetPetsYaml.getInt(smallModelId, "entityDraw." + vgString + ".small.size");*/

            ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(smallItemName);
            if (!ImiPet.loader.getServerInfo().isOldVersion()) {
                itemMeta.setCustomModelData(smallCustomModelData);
            }
            itemStack.setItemMeta(itemMeta);

            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setHelmet(itemStack);

            VexEntityDraw vexEntityDraw = new VexEntityDraw(smallX, smallY, smallSize, armorStand);
            vg.addComponent(vexEntityDraw);

    }

    // 已选择框-实体绘制
    public static void showHeadEntityDraw(VexGui vg, Player player, String vgString) {
        UUID playerUUID = player.getUniqueId();
        if (info().havePet(player)) {
            List<UUID> list = info().getPetsPackList(playerUUID);
            if (!list.isEmpty()) {
                if (MapAll.guiSelectPet.get(player) != null) {
                    String modelId = info().getPetModelId(player, list.get(MapAll.guiSelectPet.get(player)), "pets");

                    ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);

                    String itemName = infoManager.getAnimationItemNameIdle();
                    int customModelData = infoManager.getAnimationCustomModelDataIdle();
                    /*int bigSize = GetPetsYaml.getInt(modelId, "entityDraw."+vgString+".small.size");
                    int bigX = GetPetsYaml.getInt(modelId, "entityDraw."+vgString+".small.firstX");
                    int bigY = GetPetsYaml.getInt(modelId, "entityDraw."+vgString+".small.firstY");*/
                    int bigSize = smallSize(infoManager, vgString);
                    int bigX = smallFirstX(infoManager, vgString);
                    int bigY = smallFirstY(infoManager, vgString);

                    ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(itemName);
                    if (!ImiPet.loader.getServerInfo().isOldVersion()) {
                        itemMeta.setCustomModelData(customModelData);
                    }
                    itemStack.setItemMeta(itemMeta);

//                    Bukkit.getScheduler().runTaskAsynchronously(ImiPet.loader.getPlugin(), () -> {
                        ArmorStand armorStand = (ArmorStand) EntityCreator.create(EntityType.ARMOR_STAND, player.getLocation());
                        armorStand.setSmall(true);
                        armorStand.setVisible(false);
                        armorStand.setHelmet(itemStack);

                        VexEntityDraw vexEntityDraw = new VexEntityDraw(bigX, bigY, bigSize, armorStand);

                        vg.addComponent(vexEntityDraw);
//                    });
                }
            }
        }
    }

    private static int smallSize(ModelInfoManager infoManager, String vgString) {
        switch (vgString) {
            case "vgHome":
                return infoManager.getVgHomeDrawSmallSize();
            case "vgUpdateInfo":
                return infoManager.getVgUpdateInfoSmallSize();
            case "vgTransferPackWarehouse":
                return infoManager.getVgTPWDrawSmallSize();
            case "vgExp":
                return infoManager.getVgExpDrawSmallSize();
            case "vgEvolution":
                return infoManager.getVgEvolutionSmallSize();
        }
        return 0;
    }

    private static int smallFirstX(ModelInfoManager infoManager, String vgString) {
        switch (vgString) {
            case "vgExp":
                return infoManager.getVgExpDrawSmallFirstX();
            case "vgUpdateInfo":
                return infoManager.getVgUpdateInfoSmallFirstX();
            case "vgEvolution":
                return infoManager.getVgEvolutionSmallFirstX();
        }
        return 0;
    }

    private static int smallFirstY(ModelInfoManager infoManager, String vgString) {
        switch (vgString) {
            case "vgExp":
                return infoManager.getVgExpDrawSmallFirstY();
            case "vgUpdateInfo":
                return infoManager.getVgUpdateInfoSmallFirstY();
            case "vgEvolution":
                return infoManager.getVgEvolutionSmallFirstY();
        }
        return 0;
    }
}
