package cn.mcres.imiPet.api.fastHandle;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vexview.updateInfo.VgUpdateInfo;
import cn.mcres.imiPet.instal.lib.VaultLib;
import io.izzel.taboolib.module.locale.TLocale;
import lk.vexview.api.VexViewAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;



public class UpdatePetName {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 更改宠物名称
     *
     * @param playerUUID 玩家
     * @param petUUID 宠物
     * @param name 新名词
     * @param openVg 是否打开VexView界面
     */
    public static void run(UUID playerUUID, UUID petUUID, String name, boolean openVg, String type) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (!info().havePet(player)) {
//            Msg.send(player, BACK_WITHOUT_PETS);
            TLocale.sendTo(player, "BACK_WITHOUT_PETS");
            return;
        }
        FileConfiguration config = ImiPet.loader.getPlugin().getConfig();
        if (name.isEmpty() || name.length() < 2 || name.length() > config.getInt("petName.maximum")) {
//            Msg.send(player, NAME_IS_TOO_LONG);
            TLocale.sendTo(player, "NAME_IS_TOO_LONG");
            return;
        }
        if (haveDisableWord(name)) {
//            Msg.send(player, EXIST_DISABLE_WORD);
            TLocale.sendTo(player, "EXIST_DISABLE_WORD");
            return;
        }
        if (config.getBoolean("petName.money.enable")) {
            if (VaultLib.VaultLibEnable) {
                Economy economy = VaultLib.economy;
                double needMoney = config.getDouble("petName.money.need");
                if (!economy.has(player, needMoney)) {
//                    Msg.send(player, NOT_ENOUGH_MONEY);
                    TLocale.sendTo(player, "NOT_ENOUGH_MONEY");
                    return;
                }
                economy.withdrawPlayer(player, needMoney);
            }
        }
        info().setPetName(player, name, petUUID, type);
        /*for (String msg : SUCCESSFULLY_RENAMED) {
            Msg.send(player, msg.replaceAll("%imipet_name%", name));
        }*/
        TLocale.sendTo(player, "SUCCESSFULLY_RENAMED", name);
        if (openVg) {
            VexViewAPI.openGui(player, VgUpdateInfo.gui(player));
        }
    }

    private static boolean haveDisableWord(String string) {
        List<String> disableWords = ImiPet.loader.getPlugin().getConfig().getStringList("petName.disableWords");
        if (!disableWords.isEmpty()) {
            for (String s : disableWords) {
                if (string.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }
}
