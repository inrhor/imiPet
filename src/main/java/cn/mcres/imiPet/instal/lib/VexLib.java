package cn.mcres.imiPet.instal.lib;

import cn.mcres.imiPet.build.utils.LogUtil;
import org.bukkit.Bukkit;

public class VexLib {
    public static boolean VexLibEnable = false;

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("VexView")) {
//            LogUtil.send("&r┝ §a已安装插件：§rVexView");
            VexLibEnable = true;
        /*}else {
            LogUtil.send("&r┝ §c未安装插件：§rVexView");*/
        }
    }
}
