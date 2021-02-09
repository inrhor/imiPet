package cn.mcres.imiPet.instal.lib;

import cn.mcres.imiPet.build.utils.LogUtil;
import org.bukkit.Bukkit;

public class HDLib {
    public static boolean HDLibEnable = false;

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
//            LogUtil.send("&r┝ §a已安装插件：§rHolographicDisplays");
            HDLibEnable = true;
        /*}else {
            LogUtil.send("&r┝ §c未安装插件：§rHolographicDisplays");*/
        }
    }
}
