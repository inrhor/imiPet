package cn.mcres.imiPet.gui.vanilla.castSkill;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.other.PetUtils;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.api.skill.SkillCastGui;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.instal.YamlLoad;
import cn.mcres.imiPet.pet.BuildPet;
import static cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

public class GuiUnSkill implements Listener {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 物品列表开始的位置
     */
    private static HashMap<Player, Integer> guiValue = new LinkedHashMap<>();
    /**
     * 页数
     */
    private static HashMap<Player, Integer> guiPage = new LinkedHashMap<>();
    /**
     * 已显示的物品数
     */
    private static HashMap<Player, Integer> guiShowAmount = new LinkedHashMap<>();

    private static String title = "§l§a§c§c§3§3§3§l§r§8§5§8§8§d§d§3";

    public static void open(Player player, int value, int page) {
        if (PetUtils.getFollowPet(player) == null) {
            TLocale.sendTo(player, "YOU_NOT_HAVE_FOLLOWING_PET");
            return;
        }

        Inventory gui = Bukkit.createInventory(player, 6*9, title+unSkillTitle);

        createItemListGui(player, gui, value, page, guiValue, guiPage, guiShowAmount);

        BuildPet buildPet = PetUtils.getFollowPet(player);
        List<String> unSkillList = info().getPetSkillsUn(player.getUniqueId(), buildPet.getPetUUID(), "pets");

        if (!unSkillList.isEmpty()) {
            setItemLoc(player, gui, unSkillList, guiValue, guiShowAmount, value, 11, buildPet);
            if (unSkillList.size() > 5) {
                setItemLoc(player, gui, unSkillList, guiValue, guiShowAmount, guiValue.get(player), 20, buildPet);
            }
            if (unSkillList.size() > 10) {
                setItemLoc(player, gui, unSkillList, guiValue, guiShowAmount, guiValue.get(player), 29, buildPet);
            }
        }

        player.openInventory(gui);
    }

    private static void setItemLoc(Player player, Inventory gui,
                                  List<String> unSkillList,
                                  HashMap<Player, Integer> guiValue,
                                  HashMap<Player, Integer> guiShowAmount,
                                  int value,
                                  int startLoc,
                                   BuildPet buildPet) {
        int addLoc = 0;
        ItemStack itemStack = YamlLoad.skillUnGuiItem;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = YamlLoad.skillUnGuiItemLore;
        for (int i = value; i < value+5; i++) {
            if (!unSkillList.isEmpty() && unSkillList.size() > i) {
                itemMeta.setLore(ReplaceAll.replaceSkillCastGui(buildPet, lore, unSkillList, i));
                itemStack.setItemMeta(itemMeta);
                gui.setItem(startLoc+addLoc, itemStack);
                guiValue.put(player, guiValue.get(player)+1);
                guiShowAmount.put(player, guiShowAmount.get(player)+1);
                addLoc++;
            }
        }
    }

    private static void createItemListGui(
            Player player,
            Inventory gui,
            int value,
            int page,
            HashMap<Player, Integer> guiValue,
            HashMap<Player, Integer> guiPage,
            HashMap<Player, Integer> guiShowAmount) {
        guiValue.put(player, value);
        guiPage.put(player, page);
        guiShowAmount.put(player, 0);

        gui.setItem(48, GuiUtil.itemBuildGui(previousPageMaterial, previousPageName));
        gui.setItem(50, GuiUtil.itemBuildGui(nextPageMaterial, nextPageName));
        gui.setItem(49, GuiUtil.itemBuildGui(closeMaterial, closeName));
    }

    public static boolean isClickSlot(int slot) {
        if (10 < slot && slot < 16) {
            return true;
        }
        if (19 < slot && slot < 25) {
            return true;
        }
        return 28 < slot && slot < 34;
    }

    public static int getClickItemInt(int slot, int page) {
        int newPage = page-1;
        if (10 < slot && slot < 16) {
            return (slot-11)+15*newPage;
        }
        if (19 < slot && slot < 25) {
            return (slot-15)+15*newPage;
        }
        if (28 < slot && slot < 34) {
            return (slot-19)+15*newPage;
        }
        return 0;
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
            /*if (slot == 48 || slot == 50) {
                GuiCastSkill.open(player);
                return;
            }*/
            int startValue = guiValue.get(player);
            int page = guiPage.get(player);
            int showAmount = guiShowAmount.get(player);
            if (slot == 48) {
                if (page == 1) {
                    GuiCastSkill.open(player);
                }else {
                    open(player, startValue - showAmount - 15, page - 1);
                }
                return;
            }
            BuildPet buildPet = PetUtils.getFollowPet(player);
            UUID playerUUID = player.getUniqueId();
            UUID petUUID = buildPet.getPetUUID();
            String type = "pets";
            List<String> unSkillList = info().getPetSkillsUn(playerUUID, petUUID, type);
            if (slot == 50) {
                if (unSkillList.size()>(15*page)) {
                    open(player, startValue, page+1);
                }
                return;
            }
            if (ev.getCurrentItem() != null && isClickSlot(slot)) {
                int getList = getClickItemInt(slot, page);
                if (ev.isLeftClick()) {
                    if (info().getPetSkills(playerUUID, petUUID, type).size() <5) {
                        SkillCastGui.loadSkill(playerUUID, petUUID, unSkillList, getList, type);
                        open(player, 0, 1);
                    }else {
                        TLocale.sendTo(player, "LOAD_SKILL_GUI_SLOT_FULL");
                    }
                }else if (ev.isRightClick()) {
                    SkillCastGui.removeSkill(playerUUID, petUUID, unSkillList, getList, type, false);
                    open(player, 0, 1);
                }
            }
        }
    }
}
