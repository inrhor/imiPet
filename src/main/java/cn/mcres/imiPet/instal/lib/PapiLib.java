package cn.mcres.imiPet.instal.lib;

import cn.mcres.imiPet.build.utils.LogUtil;
import cn.mcres.imiPet.other.Papi;
import org.bukkit.Bukkit;

public class PapiLib {
    public static boolean papiLibEnable = false;

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Papi().register();
            papiLibEnable = true;
            /*LogUtil.send("&r┝ §a已安装插件：§rPlaceholderAPI");
        }else {
            LogUtil.send("&r┝ §c未安装插件：§rPlaceholderAPI");*/
        }
    }
}
