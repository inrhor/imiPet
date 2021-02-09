package cn.mcres.imiPet.yaml.create;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.yaml.CreateYaml;
import cn.mcres.imiPet.yaml.get.GetVanillaGuiYaml;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class VanillaGuiYaml {
    public static final FileConfiguration gui = new YamlConfiguration();
    private static final Plugin plugin = ImiPet.loader.getPlugin();

    public static void reload() {
        String vanillaGui = "VanillaGui";
        if (ImiPet.loader.getServerInfo().isOldVersionMaterial()) {
            vanillaGui = "VanillaGuiOldVer";
        }
        try {
            CreateYaml.RunResponse<Void> run = CreateYaml.copyFile(plugin, vanillaGui+".yml", false);
            gui.load(run.file);
        } catch (IOException | InvalidConfigurationException error) {
            plugin.getLogger().log(Level.WARNING, "无法加载"+vanillaGui+".yml", error);
        }
        // 实现获取YAML内容
        GetVanillaGuiYaml.get();
    }

    static{
        reload();
    }
}
