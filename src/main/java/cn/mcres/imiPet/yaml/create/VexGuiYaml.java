package cn.mcres.imiPet.yaml.create;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.yaml.CreateYaml;
import cn.mcres.imiPet.yaml.get.GetVexGuiYaml;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.logging.Level;

public class VexGuiYaml {
    public static final FileConfiguration vg = new YamlConfiguration();
    private static final Plugin plugin = ImiPet.loader.getPlugin();

    public static void reload() {
        try {
            CreateYaml.RunResponse<Void> run = CreateYaml.copyFile(plugin, "VexGui.yml", false);
            vg.load(run.file);
        } catch (IOException | InvalidConfigurationException error) {
            plugin.getLogger().log(Level.WARNING, "无法加载VexGui.yml", error);
        }
        // 实现获取YAML内容
        GetVexGuiYaml.get();
    }

    static{
        reload();
    }
}
