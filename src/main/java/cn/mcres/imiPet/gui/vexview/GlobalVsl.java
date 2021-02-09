package cn.mcres.imiPet.gui.vexview;

import cn.mcres.imiPet.api.vexgui.VexEvolution;
import cn.mcres.imiPet.api.vexgui.VexExpCore;
import cn.mcres.imiPet.api.vexgui.VexUpdateInfo;
import cn.mcres.imiPet.api.vexgui.VexWarehouse;
import cn.mcres.imiPet.gui.vexview.home.VgHome;
import cn.mcres.imiPet.other.MapAll;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexImage;
import lk.vexview.gui.components.VexScrollingList;

import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class GlobalVsl {
    public static void addVsl(VexGui vg, VexScrollingList vsl) {
        // 列表
        vg.addComponent(vsl);

        // 图片-主界面
        vsl.addComponent(new VexImage(vslImageUrl5, vslImageX5, vslImageY5, vslImageW5, vslImageH5));
        // 图片-主界面
        vsl.addComponent(new VexImage(vslImageUrl6, vslImageX6, vslImageY6, vslImageW6, vslImageH6));
        // 按钮-主界面
        vsl.addComponent(new VexButton(vslButtonID3, vslButtonText3, vslButtonUrl13, vslButtonUrl23, vslButtonX3, vslButtonY3, vslButtonW3, vslButtonH3, pp -> {
            VexViewAPI.openGui(pp, VgHome.gui(pp));
        }));

        // 图片-经验库
        vsl.addComponent(new VexImage(vslImage1Url, vslImage1X, vslImage1Y, vslImage1W, vslImage1H));
        // 图片-经验库
        vsl.addComponent(new VexImage(vslImage2Url, vslImage2X, vslImage2Y, vslImage2W, vslImage2H));
        // 按钮-经验库
        vsl.addComponent(new VexButton(vslButton1ID, vslButton1Text, vslButton1Url1, vslButton1Url2, vslButton1X, vslButton1Y, vslButton1W, vslButton1H, pp -> {
            MapAll.guiVgPet.put(pp, "vgExp");
            new VexExpCore(pp).addComponentToVg();
        }));

        // 图片-进化仓
        vsl.addComponent(new VexImage(vslImageUrl3, vslImageX3, vslImageY3, vslImageW3, vslImageH3));
        // 图片-进化仓
        vsl.addComponent(new VexImage(vslImageUrl4, vslImageX4, vslImageY4, vslImageW4, vslImageH4));
        // 按钮-进化仓
        vsl.addComponent(new VexButton(vslButtonID2, vslButtonText2, vslButtonUrl12, vslButtonUrl22, vslButtonX2, vslButtonY2, vslButtonW2, vslButtonH2, pp -> {
            MapAll.guiVgPet.put(pp, "vgEvolution");
            new VexEvolution(pp).addComponentToVg();
        }));

        // 图片-更新信息
        vsl.addComponent(new VexImage(vslImageUrl7, vslImageX7, vslImageY7, vslImageW7, vslImageH7));
        // 图片-更新信息
        vsl.addComponent(new VexImage(vslImageUrl8, vslImageX8, vslImageY8, vslImageW8, vslImageH8));
        // 按钮-更新信息
        vsl.addComponent(new VexButton(vslButtonID4, vslButtonText4, vslButtonUrl14, vslButtonUrl24, vslButtonX4, vslButtonY4, vslButtonW4, vslButtonH4, pp -> {
            MapAll.guiVgPet.put(pp, "vgUpdateInfo");
            new VexUpdateInfo(pp).addComponentToVg();
        }));

        // 图片-仓库系统
        vsl.addComponent(new VexImage(vslImageUrl9, vslImageX9, vslImageY9, vslImageW9, vslImageH9));
        // 图片-仓库系统
        vsl.addComponent(new VexImage(vslImageUrl10, vslImageX10, vslImageY10, vslImageW10, vslImageH10));
        // 按钮-仓库系统
        vsl.addComponent(new VexButton(vslButtonID5, vslButtonText5, vslButtonUrl15, vslButtonUrl25, vslButtonX5, vslButtonY5, vslButtonW5, vslButtonH5, pp -> {
            new VexWarehouse(pp).addComponentToVg();
        }));
    }
}
