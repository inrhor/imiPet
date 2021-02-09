package cn.mcres.imiPet.gui.vexview.home.expansion;

import cn.mcres.imiPet.api.data.SaveNewPet;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vexview.home.VgHome;
import cn.mcres.imiPet.instal.lib.APLib;
import io.izzel.taboolib.module.locale.TLocale;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexHoverText;
import lk.vexview.gui.components.VexTextField;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;


import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class AddTransferComponent {

    public static VexGui open(Player player, UUID petUUID) {
        VexGui vg = VgHome.gui(player);

        vg.addComponent(new VexTextField(vgVTFX1, vgVTFY1, vgVTFW1, vgVTFH1, Integer.MAX_VALUE, vgVTFID1));

        vg.addComponent(new VexButton(vgButtonID25, vgButtonText25, vgButtonUrl125, vgButtonUrl225, vgButtonX25, vgButtonY25, vgButtonW25, vgButtonH25, pp -> {
            String getTextFieldText = vg.getTextField().getTypedText();
            if (!getTextFieldText.isEmpty()) {
                Player newOwner = Bukkit.getPlayer(getTextFieldText);
                if (newOwner!=null) {
                    boolean apLibEnable = APLib.APLibEnable;
                    SaveNewPet.TransferPet(pp, newOwner, petUUID, apLibEnable, apLibEnable);
                }else {
//                    Msg.send(pp, PLAYER_IS_NOT_ONLINE);
                    TLocale.sendTo(player, "PLAYER_IS_NOT_ONLINE");
                }
            }else {
//                Msg.send(pp, TEXT_IS_EMPTY);
                TLocale.sendTo(player, "TEXT_IS_EMPTY");
            }
        }, new VexHoverText(vgButtonHoverText25)));

        return vg;
    }
}
