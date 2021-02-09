package cn.mcres.imiPet.build.utils;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.build.utils.entity.EntityCreator;
import cn.mcres.imiPet.model.ModelInfoManager;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexEntityDraw;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FastEntityDraw {
    public static void add(Player player, String modelId, String sel, VexGui vg) {
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
        String itemName = infoManager.getAnimationItemNameIdle();
        int customModelData = infoManager.getAnimationCustomModelDataIdle();
        int size = size(infoManager, sel);
        int x = x(infoManager, sel);
        int y = y(infoManager, sel);

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
        VexEntityDraw vexEntityDraw = new VexEntityDraw(x, y, size, armorStand);
        vg.addComponent(vexEntityDraw);
    }

    private static int size(ModelInfoManager infoManager, String sel) {
        if (sel.equals("vgEvolution.big")) {
            return infoManager.getVgEvolutionBigSize();
        }else if (sel.equals("vgEvolution.show")) {
            return infoManager.getVgEvolutionShowSize();
        }
        return 0;
    }

    private static int x(ModelInfoManager infoManager, String sel) {
        if (sel.equals("vgEvolution.big")) {
            return infoManager.getVgEvolutionBigX();
        }else if (sel.equals("vgEvolution.show")) {
            return infoManager.getVgEvolutionShowX();
        }
        return 0;
    }

    private static int y(ModelInfoManager infoManager, String sel) {
        if (sel.equals("vgEvolution.big")) {
            return infoManager.getVgEvolutionBigY();
        }else if (sel.equals("vgEvolution.show")) {
            return infoManager.getVgEvolutionShowY();
        }
        return 0;
    }
}
