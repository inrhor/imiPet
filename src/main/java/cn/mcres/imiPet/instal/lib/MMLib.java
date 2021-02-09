package cn.mcres.imiPet.instal.lib;

import cn.mcres.imiPet.build.utils.LogUtil;
import org.bukkit.Bukkit;

public class MMLib {
    public static boolean MMLibEnable = false;

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
//            LogUtil.send("&r┝ §a已安装插件：§rMythicMobs");
            MMLibEnable = true;
        /*}else {
            LogUtil.send("&r┝ §c未安装插件：§rMythicMobs");*/
        }
    }
}
