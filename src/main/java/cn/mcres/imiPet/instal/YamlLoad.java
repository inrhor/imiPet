package cn.mcres.imiPet.instal;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.build.utils.LogUtil;
import cn.mcres.imiPet.yaml.YamlUpdate;
import cn.mcres.imiPet.yaml.create.*;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class YamlLoad {
    private static JavaPlugin main() {
        return ImiPet.loader.getPlugin();
    }

    public static ItemStack skillCastGuiItem;
    public static List<String> skillCastGuiItemLore = new ArrayList<>();
    public static ItemStack skillUnGuiItem;
    public static List<String> skillUnGuiItemLore = new ArrayList<>();
    public static ItemStack skillCastGuiUnItem;

    public void reload() {
        main().saveDefaultConfig();
        File configFile = new File(main().getDataFolder(), "config.yml");
        File vexGuiFile = new File(main().getDataFolder(), "VexGui.yml");
        File vanillaGuiFile = new File(main().getDataFolder(), "VanillaGui.yml");
        try {
            YamlUpdate.update(main(), "config.yml", configFile, Collections.emptyList());
            YamlUpdate.update(main(), "VexGui.yml", vexGuiFile, Collections.emptyList());
            YamlUpdate.update(main(), "VanillaGui.yml", vanillaGuiFile, Collections.emptyList());
        } catch (IOException e) {
            LogUtil.send("update yaml error");
        }
        main().reloadConfig();
        VexGuiYaml.reload();
        VanillaGuiYaml.reload();
        ImiPet.loader.updateYaml();

        FileConfiguration config = main().getConfig();
        String path = "skill.gui.";
        skillCastGuiItem = new ItemStack(Material.valueOf(config.getString(path+"material")));
        ItemMeta itemMeta = skillCastGuiItem.getItemMeta();
        itemMeta.setDisplayName(config.getString(path+"itemName").replaceAll("&", "§"));
        if (config.contains(path+"customModelData")) {
            if (!ImiPet.loader.getServerInfo().isOldVersion()) {
                itemMeta.setCustomModelData(config.getInt(path+"customModelData"));
            }
        }
        skillCastGuiItem.setItemMeta(itemMeta);
        skillCastGuiItemLore = config.getStringList(path+"lore");

        String path2 = "skill.unGui.";
        skillUnGuiItem = new ItemStack(Material.valueOf(config.getString(path2+"material")));
        ItemMeta itemMeta2 = skillUnGuiItem.getItemMeta();
        itemMeta2.setDisplayName(config.getString(path2+"itemName").replaceAll("&", "§"));
        if (config.contains(path2+"customModelData")) {
            if (!ImiPet.loader.getServerInfo().isOldVersion()) {
                itemMeta2.setCustomModelData(config.getInt(path2+"customModelData"));
            }
        }
        skillUnGuiItem.setItemMeta(itemMeta2);
        skillUnGuiItemLore = config.getStringList(path2+"lore");

        String path3 = "skill.unGuiOpen.";
        skillCastGuiUnItem = new ItemStack(Material.valueOf(config.getString(path3+"material")));
        ItemMeta itemMeta3 = skillCastGuiUnItem.getItemMeta();
        itemMeta3.setDisplayName(config.getString(path3+"itemName").replaceAll("&", "§"));
        if (config.contains(path3+"customModelData")) {
            if (!ImiPet.loader.getServerInfo().isOldVersion()) {
                itemMeta3.setCustomModelData(config.getInt(path3+"customModelData"));
            }
        }
        itemMeta3.setLore(ReplaceAll.commonReplace(config.getStringList(path3+"lore")));
        skillCastGuiUnItem.setItemMeta(itemMeta3);
    }
}
