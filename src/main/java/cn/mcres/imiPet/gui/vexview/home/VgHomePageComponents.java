package cn.mcres.imiPet.gui.vexview.home;

import cn.mcres.imiPet.api.vexgui.component.VexHomePage;
import cn.mcres.imiPet.gui.vexview.home.expansion.AddCureMoneyComponent;
import cn.mcres.imiPet.gui.vexview.home.expansion.AddFoodMoneyComponent;
import cn.mcres.imiPet.gui.vexview.home.expansion.AddTransferComponent;
import cn.mcres.imiPet.other.MapAll;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexComponents;
import lk.vexview.gui.components.VexHoverText;
import lk.vexview.gui.components.VexText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class VgHomePageComponents {
    public void run() {
        VexHomePage.vgHomePageInt = 1;

        // page 1
        List<VexComponents> pageOneList = new ArrayList<>();
        pageOneList.add(new VexText(vgTextX1, vgTextY1, vgTextString1));
        pageOneList.add(new VexText(vgTextX2, vgTextY2, vgTextString2));
        pageOneList.add(new VexText(vgTextX3, vgTextY3, vgTextString3));
        pageOneList.add(new VexText(vgTextX4, vgTextY4, vgTextString4));
        pageOneList.add(new VexText(vgTextX5, vgTextY5, vgTextString5));
        // 按钮-治疗
        pageOneList.add(new VexButton(vgButton3ID, vgButton3Text, vgButton3Url1, vgButton3Url2, vgButton3X, vgButton3Y, vgButton3W, vgButton3H, pp -> {
            UUID petUUID = MapAll.vgHomePagePetUUID.get(pp);
            VexViewAPI.openGui(pp, AddCureMoneyComponent.open(pp, petUUID));
        }, new VexHoverText(vgButtonHoverText3)));
        // 按钮-补充活力
        pageOneList.add(new VexButton(vgButton4ID, vgButton4Text, vgButton4Url1, vgButton4Url2, vgButton4X, vgButton4Y, vgButton4W, vgButton4H, pp -> {
            UUID petUUID = MapAll.vgHomePagePetUUID.get(pp);
            VexViewAPI.openGui(pp, AddFoodMoneyComponent.open(pp, petUUID));
        }, new VexHoverText(vgButtonHoverText4)));
        VexHomePage.vgHomePageComponents.put(1, pageOneList);
        // 按钮-转交宠物
        pageOneList.add(new VexButton(vgButtonID24, vgButtonText24, vgButtonUrl124, vgButtonUrl224, vgButtonX24, vgButtonY24, vgButtonW24, vgButtonH24, pp -> {
            UUID petUUID = MapAll.vgHomePagePetUUID.get(pp);
            VexViewAPI.openGui(pp, AddTransferComponent.open(pp, petUUID));
        }));
    }
}
