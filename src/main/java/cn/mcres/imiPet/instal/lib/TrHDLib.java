package cn.mcres.imiPet.instal.lib;

import cn.mcres.imiPet.build.utils.LogUtil;
import org.bukkit.Bukkit;

public class TrHDLib {
    public static boolean TrHDLibEnable = false;

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("TrHologram")) {
//            LogUtil.send("&r┝ §a已安装插件：§rTrHologram");
            TrHDLibEnable = true;
        /*}else {
            LogUtil.send("&r┝ §c未安装插件：§rTrHologram");*/
        }
    }
}
