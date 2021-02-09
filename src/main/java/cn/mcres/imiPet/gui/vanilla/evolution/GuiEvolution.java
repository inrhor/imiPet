package cn.mcres.imiPet.gui.vanilla.evolution;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.fastHandle.EvolutionHandle;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.gui.vanilla.GuiSelectPet;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.gui.vanilla.home.GuiHome;
import cn.mcres.imiPet.model.ModelInfoManager;
import static cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml.*;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuiEvolution implements Listener {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    private static String title = "§l§a§c§c§3§3§3§l§r§8§5§d§1";

    public static void open(Player player) {
        Inventory gui = Bukkit.createInventory(player, 6*9, title+evolutionTitle);

        gui.setItem(11, GuiUtil.petShow(player));

        gui.setItem(13, GuiUtil.itemBuildGui(startEvolutionMaterial, cannotEvolutionName));

        gui.setItem(15, GuiUtil.itemBuildGui(selectEvolutionMaterial, selectEvolutionName));


        if (canEvolution(player)) {
            UUID playerUUID = player.getUniqueId();
            List<UUID> list = info().getPetsPackList(playerUUID);
            int guiSelectPet = GuiSelectPet.guiSelectPet.get(player);
            UUID petUUID = list.get(guiSelectPet);
            String modelId = info().getPetModelId(player, petUUID, "pets");
            ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
            String newModelId = infoManager.getEvolutionNewModelId();
            ModelInfoManager infoManager2 = ModelInfoManager.petModelList.get(newModelId);
            String name = /*GetPetsYaml.getString(newModelId, "entityDraw.itemName")*/infoManager2.getAnimationItemNameIdle();
            gui.setItem(13, GuiUtil.itemBuildGui(startEvolutionMaterial,
                    "§f", ReplaceAll.needEvolutionLevel(getHoverText(player, petUUID), player, petUUID, "pets")));
            gui.setItem(15, GuiUtil.itemBuildGui(Material.DIAMOND_HOE, name));
        }

        gui.setItem(49, GuiUtil.itemBuildGui(closeMaterial, closeName));
        gui.setItem(48, GuiUtil.itemBuildGui(previousPageMaterial, previousPageName));
        gui.setItem(50, GuiUtil.itemBuildGui(previousPageMaterial, previousPageName));

        player.openInventory(gui);
    }

    private static List<String> getHoverText(Player player, UUID petUUID) {
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, "pets"));
        if (infoManager.isEvolutionEnable()) {
            return infoManager.getEvolutionText();
        }
        return null;
    }

    @EventHandler
    void onClick(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(title)) {
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            if (slot == 48 || slot == 50) {
                GuiHome.open(player);
            }else if (slot == 11) {
                GuiSelectPet.previousPageGui.put(player, "evolution");
                GuiSelectPet.open(player);
            }else if (slot == 13) {
                if (canEvolution(player)) {
                    int guiSelectPet = GuiSelectPet.guiSelectPet.get(player);
                    UUID playerUUID = player.getUniqueId();
                    List<UUID> list = info().getPetsPackList(playerUUID);
                    UUID petUUID = list.get(guiSelectPet);
                    EvolutionHandle.run(player, petUUID, "pets");
                }
            }
        }
    }

    private static boolean canEvolution(Player player) {
        if (info().havePet(player)) {
            UUID playerUUID = player.getUniqueId();
            List<UUID> list = info().getPetsPackList(playerUUID);
            if (GuiSelectPet.guiSelectPet.get(player) != null) {
                int guiSelectPet = GuiSelectPet.guiSelectPet.get(player);
                UUID petUUID = list.get(guiSelectPet);
                String modelId = info().getPetModelId(player, petUUID, "pets");
                ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
                return infoManager.isEvolutionEnable();
            }
        }
        return false;
    }
}
