package cn.mcres.imiPet.gui.vanilla.castSkill;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.other.PetUtils;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.api.skill.SkillCastGui;
import cn.mcres.imiPet.api.skill.SkillCastManager;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.instal.YamlLoad;
import cn.mcres.imiPet.pet.BuildPet;

import static cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml.*;
import java.util.List;
import java.util.UUID;

import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiCastSkill implements Listener {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    private static String title = "§l§a§c§c§3§3§3§l§r§8§5§8§8§d";

    public static void open(Player player) {
        if (PetUtils.getFollowPet(player) == null) {
//            Msg.send(player, YOU_NOT_HAVE_FOLLOWING_PET);
            TLocale.sendTo(player, "YOU_NOT_HAVE_FOLLOWING_PET");
            return;
        }

        Inventory gui = Bukkit.createInventory(player, 6*9, title+castSkillTitle);

        gui.setItem(49, GuiUtil.itemBuildGui(closeMaterial, closeName));
        gui.setItem(40, YamlLoad.skillCastGuiUnItem);

        BuildPet buildPet = PetUtils.getFollowPet(player);
        List<String> skillList = info().getPetSkills(player.getUniqueId(), buildPet.getPetUUID(), "pets");

        int v = 0;
        ItemStack itemStack = YamlLoad.skillCastGuiItem;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = YamlLoad.skillCastGuiItemLore;
        for (int i = 20; i < 25; i++) {
            if (skillList.size() > v) {
                itemMeta.setLore(ReplaceAll.replaceSkillCastGui(buildPet, lore, skillList, v));
                itemStack.setItemMeta(itemMeta);
                gui.setItem(i, itemStack);
                v++;
            }else {
                break;
            }
        }

        player.openInventory(gui);
    }

    @EventHandler
    void onClick(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(title)) {
            ev.setCancelled(true);
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            if (PetUtils.getFollowPet(player) == null || slot == 49) {
                player.closeInventory();
                return;
            }
            if (slot == 40) {
                GuiUnSkill.open(player, 0, 1);
                return;
            }
            BuildPet buildPet = PetUtils.getFollowPet(player);
            UUID petUUID = buildPet.getPetUUID();
            UUID playerUUID = player.getUniqueId();
            List<String> skillList = info().getPetSkills(player.getUniqueId(), petUUID, "pets");
            int v = 0;
            for (int i = 20; i < 25; i++) {
                if (skillList.size() > v) {
                    if (slot == i) {
                        if (ev.isLeftClick()) {
                            String skillID = skillList.get(v);
                            SkillCastManager.petCastSkill(buildPet, skillID);
                            player.closeInventory();
                        }else if (ev.isRightClick()) {
                            SkillCastGui.uninstallSkill(playerUUID, petUUID, skillList, v, "pets");
                            open(player);
                        }
                        return;
                    }
                    v++;
                }else {
                    break;
                }
            }
        }
    }
}
