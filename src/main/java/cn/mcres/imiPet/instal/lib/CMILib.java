package cn.mcres.imiPet.instal.lib;

import cn.mcres.imiPet.build.utils.LogUtil;
import org.bukkit.Bukkit;

public class CMILib {
    public static boolean CMILibEnable = false;

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("CMI")) {
//            LogUtil.send("&r┝ §a已安装插件：§rCMI");
            CMILibEnable = true;
        /*}else {
            LogUtil.send("&r┝ §c未安装插件：§rCMI");*/
        }
    }
}
