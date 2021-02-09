package cn.mcres.imiPet.gui.vexview.home.expansion;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.fastHandle.CureHandle;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vexview.home.VgHome;
import io.izzel.taboolib.module.locale.TLocale;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexHoverText;
import lk.vexview.gui.components.VexTextField;
import org.bukkit.entity.Player;

import java.util.UUID;


import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class AddCureMoneyComponent {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static VexGui open(Player player, UUID petUUID) {
        VexGui vg = VgHome.gui(player);

        vg.addComponent(new VexTextField(vgVTFX1, vgVTFY1, vgVTFW1, vgVTFH1, Integer.MAX_VALUE, vgVTFID1));

        vg.addComponent(new VexButton(vgButtonID7, vgButtonText7, vgButtonUrl17, vgButtonUrl27, vgButtonX7, vgButtonY7, vgButtonW7, vgButtonH7, pp -> {
            String getTextFieldText = vg.getTextField().getTypedText();
            if (!getTextFieldText.isEmpty()) {
                if (MathTool.isIntNumber(getTextFieldText)) {
                    CureHandle.cureMoney(pp, petUUID, info().getPetModelId(pp, petUUID, "pets"), Double.parseDouble(getTextFieldText), "pets");
                    VexViewAPI.openGui(pp, vg);
                }else {
//                    Msg.send(pp, NOT_INT);
                    TLocale.sendTo(player, "NOT_INT");
                }
            }else {
//                Msg.send(pp, TEXT_IS_EMPTY);
                TLocale.sendTo(player, "TEXT_IS_EMPTY");
            }
        }, new VexHoverText(vgButtonHoverText7)));

        return vg;
    }
}
