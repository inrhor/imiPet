package cn.mcres.imiPet.instal.lib;

import cn.mcres.imiPet.build.utils.LogUtil;
import cn.mcres.imiPet.task.BuffTask;
import org.bukkit.Bukkit;

public class APLib {
    public static boolean APLibEnable = false;

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("AttributePlus")) {
//            LogUtil.send("&r┝ §a已安装插件：§rAttributePlus");
            BuffTask.run();
            APLibEnable = true;
        /*}else {
            LogUtil.send("&r┝ §c未安装插件：§rAttributePlus");*/
        }
    }
}
