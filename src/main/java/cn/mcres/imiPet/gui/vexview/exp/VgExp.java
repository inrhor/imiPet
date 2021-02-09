package cn.mcres.imiPet.gui.vexview.exp;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.fastHandle.ExpBoxHandle;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vexview.GlobalClickPet;
import cn.mcres.imiPet.gui.vexview.GlobalGet;
import cn.mcres.imiPet.gui.vexview.GlobalVsl;
import cn.mcres.imiPet.other.MapAll;
import io.izzel.taboolib.module.locale.TLocale;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class VgExp {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static VexGui gui(Player player) {
        VexGui vg = new VexGui(vgGuiUrl2, vgGuiX2, vgGuiY2, vgGuiW2, vgGuiH2, vgGuiW2, vgGuiH2);
        VexScrollingList vsl = new VexScrollingList(vgVsl1X, vgVsl1Y, vgVsl1W, vgVsl1H, vgVsl1Full);
        UUID playerUUID = player.getUniqueId();

        // 经验盒主体
        vg.addComponent(new VexImage(vgImageUrl5, vgImageX5, vgImageY5, vgImageW5, vgImageH5));

        // 经验存储量
        vg.addComponent(new VexText(vgTextX6, vgTextY6, vgTextString6));

        // 确定按钮
        vg.addComponent(new VexButton(vgButtonID12, vgButtonText12, vgButtonUrl112, vgButtonUrl212, vgButtonX12, vgButtonY12, vgButtonW12, vgButtonH12, pp -> {
            String getTextFieldText = vg.getTextField().getTypedText();
            if (MapAll.guiSelectPet.get(pp) != null) {
                List<UUID> list = info().getPetsPackList(playerUUID);
                if (!list.isEmpty())
                    if (!getTextFieldText.isEmpty()) {
                        if (MathTool.isIntNumber(getTextFieldText)) {
                            ExpBoxHandle.givePet(pp, list.get(MapAll.guiSelectPet.get(pp)), Integer.parseInt(getTextFieldText));
                            VexViewAPI.openGui(pp, gui(player));
                        } else {
//                            Msg.send(pp, NOT_INT);
                            TLocale.sendTo(player, "NOT_INT");
                        }
                    } else {
//                        Msg.send(pp, TEXT_IS_EMPTY);
                        TLocale.sendTo(player, "TEXT_IS_EMPTY");
                    }
            } else {
//                Msg.send(pp, PET_IS_EMPTY);
                TLocale.sendTo(player, "PET_IS_EMPTY");
            }
        }, new VexHoverText(vgButtonHoverText12)));

        // 宠物选择框
        vg.addComponent(
                new VexButton(vgButtonID13, vgButtonText13, vgButtonUrl113, vgButtonUrl213, vgButtonX13, vgButtonY13, vgButtonW13, vgButtonH13, pp ->
                new GlobalClickPet(vg, pp, "vgExp", vgButtonID15, vgButtonX15, vgButtonY15, vgButtonW15, vgButtonH15, vgButtonAddX15).addButton()));
        GlobalGet.showHeadEntityDraw(vg, player, "vgExp");

        // 给定经验输入框
        vg.addComponent(new VexTextField(vgVTFX3, vgVTFY3, vgVTFW3, vgVTFH3, Integer.MAX_VALUE, vgVTFID3));

        // 列表
        GlobalVsl.addVsl(vg, vsl);

        return vg;
    }
}
