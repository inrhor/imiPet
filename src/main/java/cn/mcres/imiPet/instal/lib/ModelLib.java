package cn.mcres.imiPet.instal.lib;

import cn.mcres.imiPet.build.utils.LogUtil;
import org.bukkit.Bukkit;

public class ModelLib {
    public static boolean ModelLibEnable = false;

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("ModelEngine")) {
//            LogUtil.send("&r┝ §a已安装插件：§rModelEngine");
            ModelLibEnable = true;
        /*}else {
            LogUtil.send("&r┝ §c未安装插件：§rModelEngine");*/
        }
    }
}
