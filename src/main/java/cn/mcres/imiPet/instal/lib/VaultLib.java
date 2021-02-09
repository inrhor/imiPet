package cn.mcres.imiPet.instal.lib;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.build.utils.LogUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultLib {
    public static boolean VaultLibEnable = false;
    public static Economy economy = null;

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = ImiPet.loader.getPlugin().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            if (setupEconomy()) {
                setupEconomy();
                VaultLibEnable = true;
//                LogUtil.send("&r┝ §a已安装插件：§rVault");
                return;
            }
        }
//        LogUtil.send("&r┝ §c未安装插件：§rVault");
    }
}
