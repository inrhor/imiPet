package cn.mcres.imiPet.gui.vexview.home.expansion;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.data.SaveDelPet;
import cn.mcres.imiPet.gui.vexview.home.VgHome;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.other.MapAll;
import io.izzel.taboolib.module.locale.TLocale;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexHoverText;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class ReleasePetButton {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static VexGui open(Player player, UUID petUUID) {
        VexGui vg = VgHome.gui(player);

        UUID playerUUID = player.getUniqueId();

        // 选择放生宠物
        vg.addComponent(new VexButton(vgButtonID8, vgButtonText8, vgButtonUrl18, vgButtonUrl28, vgButtonX8, vgButtonY8, vgButtonW8, vgButtonH8, pp -> {
            // 再次确认一下是否拥有该宠物
            List<UUID> list = info().getPetsPackList(playerUUID);
            if (!list.isEmpty()) {
                if (list.get(MapAll.guiHomeSelectPet.get(pp)).equals(petUUID)) {
                    SaveDelPet.removePet(playerUUID, petUUID, "pets");
                    TLocale.sendTo(pp, "SUCCESSFUL_RELEASE_PET");
                }
            }
            pp.closeInventory();
        }, new VexHoverText(vgButtonHoverText8)));

        vg.addComponent(new VexButton(vgButtonID9, vgButtonText9, vgButtonUrl19, vgButtonUrl29, vgButtonX9, vgButtonY9, vgButtonW9, vgButtonH9, pp -> {
            VexViewAPI.openGui(player, VgHome.gui(pp));
        }, new VexHoverText(vgButtonHoverText9)));

        return vg;
    }
}
