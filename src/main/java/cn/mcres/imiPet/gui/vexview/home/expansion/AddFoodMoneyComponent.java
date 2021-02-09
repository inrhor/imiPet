package cn.mcres.imiPet.gui.vexview.home.expansion;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.fastHandle.FoodHandle;
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

public class AddFoodMoneyComponent {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static VexGui open(Player player, UUID petUUID) {
        VexGui vg = VgHome.gui(player);

        vg.addComponent(new VexTextField(vgVTFX2, vgVTFY2, vgVTFW2, vgVTFH2, Integer.MAX_VALUE, vgVTFID2));

        vg.addComponent(new VexButton(vgButtonID11, vgButtonText11, vgButtonUrl111, vgButtonUrl211, vgButtonX11, vgButtonY11, vgButtonW11, vgButtonH11, pp -> {
            String getTextFieldText = vg.getTextField().getTypedText();
            if (!getTextFieldText.isEmpty()) {
                if (MathTool.isIntNumber(getTextFieldText)) {
                    FoodHandle.foodMoney(pp, petUUID, info().getPetModelId(pp, petUUID, "pets"), Integer.parseInt(getTextFieldText), "pets");
                    VexViewAPI.openGui(pp, vg);
                }else {
                    TLocale.sendTo(pp, "NOT_INT");
                }
            }else {
                TLocale.sendTo(pp, "TEXT_IS_EMPTY");
            }
        }, new VexHoverText(vgButtonHoverText11)));

        return vg;
    }
}
