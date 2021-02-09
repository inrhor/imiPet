package cn.mcres.imiPet.gui.vexview.evolution;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.fastHandle.EvolutionHandle;
import cn.mcres.imiPet.build.utils.FastEntityDraw;
import cn.mcres.imiPet.gui.vexview.GlobalClickPet;
import cn.mcres.imiPet.gui.vexview.GlobalGet;
import cn.mcres.imiPet.gui.vexview.GlobalVsl;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import io.izzel.taboolib.module.locale.TLocale;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class VgEvolution {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static VexGui gui(Player player) {
        VexGui vg = new VexGui(vgGuiUrl3, vgGuiX3, vgGuiY3, vgGuiW3, vgGuiH3, vgGuiW3, vgGuiH3);
        VexScrollingList vsl = new VexScrollingList(vgVsl1X, vgVsl1Y, vgVsl1W, vgVsl1H, vgVsl1Full);
        UUID playerUUID = player.getUniqueId();

        // 选择宠物的背景
        vg.addComponent(new VexImage(vgImageUrl6, vgImageX6, vgImageY6, vgImageW6, vgImageH6));

        // 进化宠物的背景
        vg.addComponent(new VexImage(vgImageUrl7, vgImageX7, vgImageY7, vgImageW7, vgImageH7));

        if (info().havePet(player)) {
            List<UUID> list = info().getPetsPackList(playerUUID);
            if (!list.isEmpty() && MapAll.guiSelectPet.get(player)!=null) {
                UUID petUUID = list.get(MapAll.guiSelectPet.get(player));
//                String imageShowBigUrl = GetPetsYaml.getString(info().getPetModelId(player, petUUID, "pets"), "imageShowBig");
//                vg.addComponent(new VexImage(imageShowBigUrl, vgImageX8, vgImageY8, vgImageW8, vgImageH8));

                String modelId = info().getPetModelId(player, petUUID, "pets");

                FastEntityDraw.add(player, modelId, "vgEvolution.big", vg);

                ModelInfoManager infoManager = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, "pets"));

                if (infoManager.isEvolutionEnable()) {
                    String modelIdSel = info().getPetModelId(player, list.get(MapAll.guiSelectPet.get(player)), "pets");
                    ModelInfoManager infoManager2 = ModelInfoManager.petModelList.get(modelIdSel);
//                    String newModelId = GetPetsYaml.getString(info().getPetModelId(player, list.get(MapAll.guiSelectPet.get(player)), "pets"), "evolution.newModelId");
//                    vg.addComponent(new VexImage(GetPetsYaml.getString(newModelId, "imageShowBig"), vgImageX9, vgImageY9, vgImageW9, vgImageH9));
                    String newModelId = infoManager2.getEvolutionNewModelId();
                    FastEntityDraw.add(player, newModelId, "vgEvolution.show", vg);
                }

                // 赋值hoverText
                List<String> hoverText = getHoverText(player, petUUID);

                // 确定按钮
                vg.addComponent(new VexButton(vgButtonID17, vgButtonText17, vgButtonUrl117, vgButtonUrl217, vgButtonX17, vgButtonY17, vgButtonW17, vgButtonH17, pp -> {
                    EvolutionHandle.run(pp, petUUID, "pets");
                }, new VexHoverText(hoverText)));
            }
        }

        // 宠物选择框
        vg.addComponent(new VexButton(vgButtonID14, vgButtonText14, vgButtonUrl114, vgButtonUrl214, vgButtonX14, vgButtonY14, vgButtonW14, vgButtonH14, pp -> new GlobalClickPet(vg, pp, "vgEvolution", vgButtonID16, vgButtonX16, vgButtonY16, vgButtonW16, vgButtonH16, vgButtonAddX16).addButton()));
        GlobalGet.showHeadEntityDraw(vg, player, "vgEvolution");

        // 列表
        GlobalVsl.addVsl(vg, vsl);

        return vg;
    }

    private static List<String> getHoverText(Player player, UUID petUUID) {
        ModelInfoManager infoManager = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, "pets"));
        if (infoManager.isEvolutionEnable()) {
            if (infoManager.getEvolutionText()!=null) {
                return infoManager.getEvolutionText();
            }else {
                return vgButtonHoverText17;
            }
        }
        return TLocale.asStringList("CAN_NOT_EVOLVED_HOVER_TEXT");
    }
}
