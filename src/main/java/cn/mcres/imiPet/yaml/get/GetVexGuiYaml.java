package cn.mcres.imiPet.yaml.get;

import cn.mcres.imiPet.yaml.create.VexGuiYaml;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class GetVexGuiYaml {
    public static String vgGui1Url,
            vgGuiUrl2,
            vgGuiUrl3,
            vgGuiUrl4,
            vgGuiUrl5,
            vgGuiUrl6;
    public static int vgGui1X, vgGui1Y, vgGui1W, vgGui1H,
            vgGuiX2, vgGuiY2, vgGuiW2, vgGuiH2,
            vgGuiX3, vgGuiY3, vgGuiW3, vgGuiH3,
            vgGuiX4, vgGuiY4, vgGuiW4, vgGuiH4,
            vgGuiX5, vgGuiY5, vgGuiW5, vgGuiH5,
            vgGuiX6, vgGuiY6, vgGuiW6, vgGuiH6;
    public static int vgVsl1X, vgVsl1Y, vgVsl1W, vgVsl1H, vgVsl1Full,
            vgVslX2, vgVslY2, vgVslW2, vgVslH2, vgVslFull2, vgVslAddFull2;
    public static int vslButton1ID, vslButton1X, vslButton1Y, vslButton1W, vslButton1H,
            vslButtonID2, vslButtonX2, vslButtonY2, vslButtonW2, vslButtonH2,
            vslButtonID3, vslButtonX3, vslButtonY3, vslButtonW3, vslButtonH3,
            vslButtonID4, vslButtonX4, vslButtonY4, vslButtonW4, vslButtonH4,
            vslButtonID5, vslButtonX5, vslButtonY5, vslButtonW5, vslButtonH5,
            vslButtonID6, vslButtonX6, vslButtonY6, vslButtonW6, vslButtonH6, vslButtonAddY6,
            vslButtonID7, vslButtonX7, vslButtonY7, vslButtonW7, vslButtonH7, vslButtonAddY7;
    public static String vslButton1Text, vslButton1Url1, vslButton1Url2,
            vslButtonText2, vslButtonUrl12, vslButtonUrl22,
            vslButtonText3, vslButtonUrl13, vslButtonUrl23,
            vslButtonText4, vslButtonUrl14, vslButtonUrl24,
            vslButtonText5, vslButtonUrl15, vslButtonUrl25,
            vslButtonText6, vslButtonUrl16, vslButtonUrl26,
            vslButtonText7, vslButtonUrl17, vslButtonUrl27;
    public static List<String> vslButtonHoverText6;
    public static int vgVTFX1, vgVTFY1, vgVTFW1, vgVTFH1, vgVTFID1,
            vgVTFX2, vgVTFY2, vgVTFW2, vgVTFH2, vgVTFID2,
            vgVTFX3, vgVTFY3, vgVTFW3, vgVTFH3, vgVTFID3,
            vgVTFX4, vgVTFY4, vgVTFW4, vgVTFH4, vgVTFID4,
            vgVTFX5, vgVTFY5, vgVTFW5, vgVTFH5, vgVTFID5;

    public static int vslVTFID1, vslVTFX1, vslVTFY1, vslVTFW1, vslVTFH1, vslVTFAddY1;

    public static String vslImage1Url,
            vslImage2Url,
            vslImageUrl3,
            vslImageUrl4,
            vslImageUrl5,
            vslImageUrl6,
            vslImageUrl7,
            vslImageUrl8,
            vslImageUrl9,
            vslImageUrl10,
            vslImageUrl11;
    public static int vslImage1X, vslImage1Y, vslImage1W, vslImage1H,
            vslImage2X, vslImage2Y, vslImage2W, vslImage2H,
            vslImageX3, vslImageY3, vslImageW3, vslImageH3,
            vslImageX4, vslImageY4, vslImageW4, vslImageH4,
            vslImageX5, vslImageY5, vslImageW5, vslImageH5,
            vslImageX6, vslImageY6, vslImageW6, vslImageH6,
            vslImageX7, vslImageY7, vslImageW7, vslImageH7,
            vslImageX8, vslImageY8, vslImageW8, vslImageH8,
            vslImageX9, vslImageY9, vslImageW9, vslImageH9,
            vslImageX10, vslImageY10, vslImageW10, vslImageH10,
            vslImageX11, vslImageY11, vslImageW11, vslImageH11, vslImageAddY11;
    public static String vgImage1Url,
            vgImage2Url,
            vgImage3Url,
            vgImageUrl5,
            vgImageUrl6,
            vgImageUrl7,
            vgImageUrl10,
            vgImageUrl11;
    public static int vgImage1X, vgImage1Y, vgImage1W, vgImage1H,
            vgImage2X, vgImage2Y, vgImage2W, vgImage2H,
            vgImage3X, vgImage3Y, vgImage3W, vgImage3H, vgImage3AddX,
            vgImageX5, vgImageY5, vgImageW5, vgImageH5,
            vgImageX6, vgImageY6, vgImageW6, vgImageH6,
            vgImageX7, vgImageY7, vgImageW7, vgImageH7,
            vgImageX8, vgImageY8, vgImageW8, vgImageH8,
            vgImageX9, vgImageY9, vgImageW9, vgImageH9,
            vgImageX10, vgImageY10, vgImageW10, vgImageH10,
            vgImageX11, vgImageY11, vgImageW11, vgImageH11,
            vgImageX12, vgImageY12, vgImageW12, vgImageH12;
    public static int vgButton1ID, vgButton1X, vgButton1Y, vgButton1W, vgButton1H, vgButton1AddX,
            vgButton2ID, vgButton2X, vgButton2Y, vgButton2W, vgButton2H,
            vgButton3ID, vgButton3X, vgButton3Y, vgButton3W, vgButton3H,
            vgButton4ID, vgButton4X, vgButton4Y, vgButton4W, vgButton4H,
            vgButton5ID, vgButton5X, vgButton5Y, vgButton5W, vgButton5H,
            vgButtonID7, vgButtonX7, vgButtonY7, vgButtonW7, vgButtonH7,
            vgButtonID8, vgButtonX8, vgButtonY8, vgButtonW8, vgButtonH8,
            vgButtonID9, vgButtonX9, vgButtonY9, vgButtonW9, vgButtonH9,
            vgButtonID11, vgButtonX11, vgButtonY11, vgButtonW11, vgButtonH11,
            vgButtonID12, vgButtonX12, vgButtonY12, vgButtonW12, vgButtonH12,
            vgButtonID13, vgButtonX13, vgButtonY13, vgButtonW13, vgButtonH13,
            vgButtonID14, vgButtonX14, vgButtonY14, vgButtonW14, vgButtonH14,
            vgButtonID15, vgButtonX15, vgButtonY15, vgButtonW15, vgButtonH15, vgButtonAddX15,
            vgButtonID16, vgButtonX16, vgButtonY16, vgButtonW16, vgButtonH16, vgButtonAddX16,
            vgButtonID17, vgButtonX17, vgButtonY17, vgButtonW17, vgButtonH17,
            vgButtonID18, vgButtonX18, vgButtonY18, vgButtonW18, vgButtonH18,
            vgButtonID19, vgButtonX19, vgButtonY19, vgButtonW19, vgButtonH19, vgButtonAddX19,
            vgButtonID20, vgButtonX20, vgButtonY20, vgButtonW20, vgButtonH20,
            vgButtonID21, vgButtonX21, vgButtonY21, vgButtonW21, vgButtonH21,
            vgButtonID22, vgButtonX22, vgButtonY22, vgButtonW22, vgButtonH22,
            vgButtonID23, vgButtonX23, vgButtonY23, vgButtonW23, vgButtonH23,
            vgButtonID24, vgButtonX24, vgButtonY24, vgButtonW24, vgButtonH24,
            vgButtonID25, vgButtonX25, vgButtonY25, vgButtonW25, vgButtonH25,
            vgButtonID26, vgButtonX26, vgButtonY26, vgButtonW26, vgButtonH26,
            vgButtonID27, vgButtonX27, vgButtonY27, vgButtonW27, vgButtonH27, vgButtonAddX27;
    public static String vgButton1Text, vgButton1Url1, vgButton1Url2,
            vgButton2Text, vgButton2Url1, vgButton2Url2,
            vgButton3Text, vgButton3Url1, vgButton3Url2,
            vgButton4Text, vgButton4Url1, vgButton4Url2,
            vgButton5Text, vgButton5Url1, vgButton5Url2,
            vgButtonText7, vgButtonUrl17, vgButtonUrl27,
            vgButtonText8, vgButtonUrl18, vgButtonUrl28,
            vgButtonText9, vgButtonUrl19, vgButtonUrl29,
            vgButtonText11, vgButtonUrl111, vgButtonUrl211,
            vgButtonText12, vgButtonUrl112, vgButtonUrl212,
            vgButtonText13, vgButtonUrl113, vgButtonUrl213,
            vgButtonText14, vgButtonUrl114, vgButtonUrl214,
            vgButtonText15, vgButtonUrl115, vgButtonUrl215,
            vgButtonText16, vgButtonUrl116, vgButtonUrl216,
            vgButtonText17, vgButtonUrl117, vgButtonUrl217,
            vgButtonText18, vgButtonUrl118, vgButtonUrl218,
            vgButtonText19, vgButtonUrl119, vgButtonUrl219,
            vgButtonText20, vgButtonUrl120, vgButtonUrl220,
            vgButtonText21, vgButtonUrl121, vgButtonUrl221,
            vgButtonText22, vgButtonUrl122, vgButtonUrl222,
            vgButtonText23, vgButtonUrl123, vgButtonUrl223,
            vgButtonText24, vgButtonUrl124, vgButtonUrl224,
            vgButtonText25, vgButtonUrl125, vgButtonUrl225,
            vgButtonText26, vgButtonUrl126, vgButtonUrl226,
            vgButtonText27, vgButtonUrl127, vgButtonUrl227;
    public static List<String> vgButtonHoverText2,
            vgButtonHoverText3,
            vgButtonHoverText4,
            vgButtonHoverText5,
            vgButtonHoverText7,
            vgButtonHoverText8,
            vgButtonHoverText9,
            vgButtonHoverText11,
            vgButtonHoverText12,
            vgButtonHoverText17,
            vgButtonHoverText20,
            vgButtonHoverText21,
            vgButtonHoverText25;
    public static int vgTextX1, vgTextY1,
            vgTextX2, vgTextY2,
            vgTextX3, vgTextY3,
            vgTextX4, vgTextY4,
            vgTextX5, vgTextY5,
            vgTextX6, vgTextY6,
            vgTextX7, vgTextY7,
            vgTextX8, vgTextY8;
    public static List<String> vgTextString1,
            vgTextString2,
            vgTextString3,
            vgTextString4,
            vgTextString5,
            vgTextString6,
            vgTextString7,
            vgTextString8;

    public static int vslTextX1, vslTextY1, vslTextAddY1;
    public static List<String> vslTextString1;

    public static void get() {
        FileConfiguration vgYaml = VexGuiYaml.vg;

        vgGui1Url = vgYaml.getString("vg.gui.1.url");
        vgGui1X = vgYaml.getInt("vg.gui.1.x");
        vgGui1Y = vgYaml.getInt("vg.gui.1.y");
        vgGui1W = vgYaml.getInt("vg.gui.1.w");
        vgGui1H = vgYaml.getInt("vg.gui.1.h");

        vgGuiUrl2 = vgYaml.getString("vg.gui.2.url");
        vgGuiX2 = vgYaml.getInt("vg.gui.2.x");
        vgGuiY2 = vgYaml.getInt("vg.gui.2.y");
        vgGuiW2 = vgYaml.getInt("vg.gui.2.w");
        vgGuiH2 = vgYaml.getInt("vg.gui.2.h");

        vgGuiUrl3 = vgYaml.getString("vg.gui.3.url");
        vgGuiX3 = vgYaml.getInt("vg.gui.3.x");
        vgGuiY3 = vgYaml.getInt("vg.gui.3.y");
        vgGuiW3 = vgYaml.getInt("vg.gui.3.w");
        vgGuiH3 = vgYaml.getInt("vg.gui.3.h");

        vgGuiUrl4 = vgYaml.getString("vg.gui.4.url");
        vgGuiX4 = vgYaml.getInt("vg.gui.4.x");
        vgGuiY4 = vgYaml.getInt("vg.gui.4.y");
        vgGuiW4 = vgYaml.getInt("vg.gui.4.w");
        vgGuiH4 = vgYaml.getInt("vg.gui.4.h");

        vgGuiUrl5 = vgYaml.getString("vg.gui.5.url");
        vgGuiX5 = vgYaml.getInt("vg.gui.5.x");
        vgGuiY5 = vgYaml.getInt("vg.gui.5.y");
        vgGuiW5 = vgYaml.getInt("vg.gui.5.w");
        vgGuiH5 = vgYaml.getInt("vg.gui.5.h");

        vgGuiUrl6 = vgYaml.getString("vg.gui.6.url");
        vgGuiX6 = vgYaml.getInt("vg.gui.6.x");
        vgGuiY6 = vgYaml.getInt("vg.gui.6.y");
        vgGuiW6 = vgYaml.getInt("vg.gui.6.w");
        vgGuiH6 = vgYaml.getInt("vg.gui.6.h");

        vgVsl1X = vgYaml.getInt("vg.vsl.1.x");
        vgVsl1Y = vgYaml.getInt("vg.vsl.1.y");
        vgVsl1W = vgYaml.getInt("vg.vsl.1.w");
        vgVsl1H = vgYaml.getInt("vg.vsl.1.h");
        vgVsl1Full = vgYaml.getInt("vg.vsl.1.full");

        vgVslX2 = vgYaml.getInt("vg.vsl.2.x");
        vgVslY2 = vgYaml.getInt("vg.vsl.2.y");
        vgVslW2 = vgYaml.getInt("vg.vsl.2.w");
        vgVslH2 = vgYaml.getInt("vg.vsl.2.h");
        vgVslFull2 = vgYaml.getInt("vg.vsl.2.full");
        vgVslAddFull2 = vgYaml.getInt("vg.vsl.2.addFull");

        vslButton1Text = vgYaml.getString("vsl.button.1.text");
        vslButton1Url1 = vgYaml.getString("vsl.button.1.url1");
        vslButton1Url2 = vgYaml.getString("vsl.button.1.url2");
        vslButton1ID = vgYaml.getInt("vsl.button.1.id");
        vslButton1X = vgYaml.getInt("vsl.button.1.x");
        vslButton1Y = vgYaml.getInt("vsl.button.1.y");
        vslButton1W = vgYaml.getInt("vsl.button.1.w");
        vslButton1H = vgYaml.getInt("vsl.button.1.h");

        vslButtonText2 = vgYaml.getString("vsl.button.2.text");
        vslButtonUrl12 = vgYaml.getString("vsl.button.2.url1");
        vslButtonUrl22 = vgYaml.getString("vsl.button.2.url2");
        vslButtonID2 = vgYaml.getInt("vsl.button.2.id");
        vslButtonX2 = vgYaml.getInt("vsl.button.2.x");
        vslButtonY2 = vgYaml.getInt("vsl.button.2.y");
        vslButtonW2 = vgYaml.getInt("vsl.button.2.w");
        vslButtonH2 = vgYaml.getInt("vsl.button.2.h");

        vslButtonText3 = vgYaml.getString("vsl.button.3.text");
        vslButtonUrl13 = vgYaml.getString("vsl.button.3.url1");
        vslButtonUrl23 = vgYaml.getString("vsl.button.3.url2");
        vslButtonID3 = vgYaml.getInt("vsl.button.3.id");
        vslButtonX3 = vgYaml.getInt("vsl.button.3.x");
        vslButtonY3 = vgYaml.getInt("vsl.button.3.y");
        vslButtonW3 = vgYaml.getInt("vsl.button.3.w");
        vslButtonH3 = vgYaml.getInt("vsl.button.3.h");

        vslButtonText4 = vgYaml.getString("vsl.button.4.text");
        vslButtonUrl14 = vgYaml.getString("vsl.button.4.url1");
        vslButtonUrl24 = vgYaml.getString("vsl.button.4.url2");
        vslButtonID4 = vgYaml.getInt("vsl.button.4.id");
        vslButtonX4 = vgYaml.getInt("vsl.button.4.x");
        vslButtonY4 = vgYaml.getInt("vsl.button.4.y");
        vslButtonW4 = vgYaml.getInt("vsl.button.4.w");
        vslButtonH4 = vgYaml.getInt("vsl.button.4.h");

        vslButtonText5 = vgYaml.getString("vsl.button.5.text");
        vslButtonUrl15 = vgYaml.getString("vsl.button.5.url1");
        vslButtonUrl25 = vgYaml.getString("vsl.button.5.url2");
        vslButtonID5 = vgYaml.getInt("vsl.button.5.id");
        vslButtonX5 = vgYaml.getInt("vsl.button.5.x");
        vslButtonY5 = vgYaml.getInt("vsl.button.5.y");
        vslButtonW5 = vgYaml.getInt("vsl.button.5.w");
        vslButtonH5 = vgYaml.getInt("vsl.button.5.h");

        vslButtonText6 = vgYaml.getString("vsl.button.6.text");
        vslButtonUrl16 = vgYaml.getString("vsl.button.6.url1");
        vslButtonUrl26 = vgYaml.getString("vsl.button.6.url2");
        vslButtonID6 = vgYaml.getInt("vsl.button.6.id");
        vslButtonX6 = vgYaml.getInt("vsl.button.6.x");
        vslButtonY6 = vgYaml.getInt("vsl.button.6.y");
        vslButtonAddY6 = vgYaml.getInt("vsl.button.6.addY");
        vslButtonW6 = vgYaml.getInt("vsl.button.6.w");
        vslButtonH6 = vgYaml.getInt("vsl.button.6.h");
        vslButtonHoverText6 = vgYaml.getStringList("vsl.button.6.hoverText");

        vslButtonText7 = vgYaml.getString("vsl.button.7.text");
        vslButtonUrl17 = vgYaml.getString("vsl.button.7.url1");
        vslButtonUrl27 = vgYaml.getString("vsl.button.7.url2");
        vslButtonID7 = vgYaml.getInt("vsl.button.7.id");
        vslButtonX7 = vgYaml.getInt("vsl.button.7.x");
        vslButtonY7 = vgYaml.getInt("vsl.button.7.y");
        vslButtonW7 = vgYaml.getInt("vsl.button.7.w");
        vslButtonH7 = vgYaml.getInt("vsl.button.7.h");
        vslButtonAddY7 = vgYaml.getInt("vsl.button.7.addY");

        vslVTFID1 = vgYaml.getInt("vsl.vexTextField.1.id");
        vslVTFX1 = vgYaml.getInt("vsl.vexTextField.1.x");
        vslVTFY1 = vgYaml.getInt("vsl.vexTextField.1.y");
        vslVTFW1 = vgYaml.getInt("vsl.vexTextField.1.w");
        vslVTFH1 = vgYaml.getInt("vsl.vexTextField.1.h");
        vslVTFAddY1 = vgYaml.getInt("vsl.vexTextField.1.addY");

        vgVTFID1 = vgYaml.getInt("vg.vexTextField.1.id");
        vgVTFX1 = vgYaml.getInt("vg.vexTextField.1.x");
        vgVTFY1 = vgYaml.getInt("vg.vexTextField.1.y");
        vgVTFW1 = vgYaml.getInt("vg.vexTextField.1.w");
        vgVTFH1 = vgYaml.getInt("vg.vexTextField.1.h");

        vgVTFID2 = vgYaml.getInt("vg.vexTextField.2.id");
        vgVTFX2 = vgYaml.getInt("vg.vexTextField.2.x");
        vgVTFY2 = vgYaml.getInt("vg.vexTextField.2.y");
        vgVTFW2 = vgYaml.getInt("vg.vexTextField.2.w");
        vgVTFH2 = vgYaml.getInt("vg.vexTextField.2.h");

        vgVTFID3 = vgYaml.getInt("vg.vexTextField.3.id");
        vgVTFX3 = vgYaml.getInt("vg.vexTextField.3.x");
        vgVTFY3 = vgYaml.getInt("vg.vexTextField.3.y");
        vgVTFW3 = vgYaml.getInt("vg.vexTextField.3.w");
        vgVTFH3 = vgYaml.getInt("vg.vexTextField.3.h");

        vgVTFID4 = vgYaml.getInt("vg.vexTextField.4.id");
        vgVTFX4 = vgYaml.getInt("vg.vexTextField.4.x");
        vgVTFY4 = vgYaml.getInt("vg.vexTextField.4.y");
        vgVTFW4 = vgYaml.getInt("vg.vexTextField.4.w");
        vgVTFH4 = vgYaml.getInt("vg.vexTextField.4.h");

        vgVTFID5 = vgYaml.getInt("vg.vexTextField.5.id");
        vgVTFX5 = vgYaml.getInt("vg.vexTextField.5.x");
        vgVTFY5 = vgYaml.getInt("vg.vexTextField.5.y");
        vgVTFW5 = vgYaml.getInt("vg.vexTextField.5.w");
        vgVTFH5 = vgYaml.getInt("vg.vexTextField.5.h");

        vslImage1Url = vgYaml.getString("vsl.image.1.url");
        vslImage1X = vgYaml.getInt("vsl.image.1.x");
        vslImage1Y = vgYaml.getInt("vsl.image.1.y");
        vslImage1W = vgYaml.getInt("vsl.image.1.w");
        vslImage1H = vgYaml.getInt("vsl.image.1.h");

        vslImage2Url = vgYaml.getString("vsl.image.2.url");
        vslImage2X = vgYaml.getInt("vsl.image.2.x");
        vslImage2Y = vgYaml.getInt("vsl.image.2.y");
        vslImage2W = vgYaml.getInt("vsl.image.2.w");
        vslImage2H = vgYaml.getInt("vsl.image.2.h");

        vslImageUrl3 = vgYaml.getString("vsl.image.3.url");
        vslImageX3 = vgYaml.getInt("vsl.image.3.x");
        vslImageY3 = vgYaml.getInt("vsl.image.3.y");
        vslImageW3 = vgYaml.getInt("vsl.image.3.w");
        vslImageH3 = vgYaml.getInt("vsl.image.3.h");

        vslImageUrl4 = vgYaml.getString("vsl.image.4.url");
        vslImageX4 = vgYaml.getInt("vsl.image.4.x");
        vslImageY4 = vgYaml.getInt("vsl.image.4.y");
        vslImageW4 = vgYaml.getInt("vsl.image.4.w");
        vslImageH4 = vgYaml.getInt("vsl.image.4.h");

        vslImageUrl5 = vgYaml.getString("vsl.image.5.url");
        vslImageX5 = vgYaml.getInt("vsl.image.5.x");
        vslImageY5 = vgYaml.getInt("vsl.image.5.y");
        vslImageW5 = vgYaml.getInt("vsl.image.5.w");
        vslImageH5 = vgYaml.getInt("vsl.image.5.h");

        vslImageUrl6 = vgYaml.getString("vsl.image.6.url");
        vslImageX6 = vgYaml.getInt("vsl.image.6.x");
        vslImageY6 = vgYaml.getInt("vsl.image.6.y");
        vslImageW6 = vgYaml.getInt("vsl.image.6.w");
        vslImageH6 = vgYaml.getInt("vsl.image.6.h");

        vslImageUrl7 = vgYaml.getString("vsl.image.7.url");
        vslImageX7 = vgYaml.getInt("vsl.image.7.x");
        vslImageY7 = vgYaml.getInt("vsl.image.7.y");
        vslImageW7 = vgYaml.getInt("vsl.image.7.w");
        vslImageH7 = vgYaml.getInt("vsl.image.7.h");

        vslImageUrl8 = vgYaml.getString("vsl.image.8.url");
        vslImageX8 = vgYaml.getInt("vsl.image.8.x");
        vslImageY8 = vgYaml.getInt("vsl.image.8.y");
        vslImageW8 = vgYaml.getInt("vsl.image.8.w");
        vslImageH8 = vgYaml.getInt("vsl.image.8.h");

        vslImageUrl9 = vgYaml.getString("vsl.image.9.url");
        vslImageX9 = vgYaml.getInt("vsl.image.9.x");
        vslImageY9 = vgYaml.getInt("vsl.image.9.y");
        vslImageW9 = vgYaml.getInt("vsl.image.9.w");
        vslImageH9 = vgYaml.getInt("vsl.image.9.h");

        vslImageUrl10 = vgYaml.getString("vsl.image.10.url");
        vslImageX10 = vgYaml.getInt("vsl.image.10.x");
        vslImageY10 = vgYaml.getInt("vsl.image.10.y");
        vslImageW10 = vgYaml.getInt("vsl.image.10.w");
        vslImageH10 = vgYaml.getInt("vsl.image.10.h");

        vgImage1Url = vgYaml.getString("vg.image.1.url");
        vgImage1X = vgYaml.getInt("vg.image.1.x");
        vgImage1Y = vgYaml.getInt("vg.image.1.y");
        vgImage1W = vgYaml.getInt("vg.image.1.w");
        vgImage1H = vgYaml.getInt("vg.image.1.h");

        vgImage2Url = vgYaml.getString("vg.image.2.url");
        vgImage2X = vgYaml.getInt("vg.image.2.x");
        vgImage2Y = vgYaml.getInt("vg.image.2.y");
        vgImage2W = vgYaml.getInt("vg.image.2.w");
        vgImage2H = vgYaml.getInt("vg.image.2.h");

        vgImageUrl5 = vgYaml.getString("vg.image.5.url");
        vgImageX5 = vgYaml.getInt("vg.image.5.x");
        vgImageY5 = vgYaml.getInt("vg.image.5.y");
        vgImageW5 = vgYaml.getInt("vg.image.5.w");
        vgImageH5 = vgYaml.getInt("vg.image.5.h");

        vgButton1Text = vgYaml.getString("vg.button.1.text");
        vgButton1Url1 = vgYaml.getString("vg.button.1.url1");
        vgButton1Url2 = vgYaml.getString("vg.button.1.url2");
        vgButton1ID = vgYaml.getInt("vg.button.1.id");
        vgButton1X = vgYaml.getInt("vg.button.1.x");
        vgButton1Y = vgYaml.getInt("vg.button.1.y");
        vgButton1W = vgYaml.getInt("vg.button.1.w");
        vgButton1H = vgYaml.getInt("vg.button.1.h");
        vgButton1AddX = vgYaml.getInt("vg.button.1.addX");

        vgButton2Text = vgYaml.getString("vg.button.2.text");
        vgButton2Url1 = vgYaml.getString("vg.button.2.url1");
        vgButton2Url2 = vgYaml.getString("vg.button.2.url2");
        vgButton2ID = vgYaml.getInt("vg.button.2.id");
        vgButton2X = vgYaml.getInt("vg.button.2.x");
        vgButton2Y = vgYaml.getInt("vg.button.2.y");
        vgButton2W = vgYaml.getInt("vg.button.2.w");
        vgButton2H = vgYaml.getInt("vg.button.2.h");
        vgButtonHoverText2 = vgYaml.getStringList("vg.button.2.hoverText");

        vgButton3Text = vgYaml.getString("vg.button.3.text");
        vgButton3Url1 = vgYaml.getString("vg.button.3.url1");
        vgButton3Url2 = vgYaml.getString("vg.button.3.url2");
        vgButton3ID = vgYaml.getInt("vg.button.3.id");
        vgButton3X = vgYaml.getInt("vg.button.3.x");
        vgButton3Y = vgYaml.getInt("vg.button.3.y");
        vgButton3W = vgYaml.getInt("vg.button.3.w");
        vgButton3H = vgYaml.getInt("vg.button.3.h");
        vgButtonHoverText3 = vgYaml.getStringList("vg.button.3.hoverText");

        vgButton4Text = vgYaml.getString("vg.button.4.text");
        vgButton4Url1 = vgYaml.getString("vg.button.4.url1");
        vgButton4Url2 = vgYaml.getString("vg.button.4.url2");
        vgButton4ID = vgYaml.getInt("vg.button.4.id");
        vgButton4X = vgYaml.getInt("vg.button.4.x");
        vgButton4Y = vgYaml.getInt("vg.button.4.y");
        vgButton4W = vgYaml.getInt("vg.button.4.w");
        vgButton4H = vgYaml.getInt("vg.button.4.h");
        vgButtonHoverText4 = vgYaml.getStringList("vg.button.4.hoverText");

        vgButton5Text = vgYaml.getString("vg.button.5.text");
        vgButton5Url1 = vgYaml.getString("vg.button.5.url1");
        vgButton5Url2 = vgYaml.getString("vg.button.5.url2");
        vgButton5ID = vgYaml.getInt("vg.button.5.id");
        vgButton5X = vgYaml.getInt("vg.button.5.x");
        vgButton5Y = vgYaml.getInt("vg.button.5.y");
        vgButton5W = vgYaml.getInt("vg.button.5.w");
        vgButton5H = vgYaml.getInt("vg.button.5.h");
        vgButtonHoverText5 = vgYaml.getStringList("vg.button.5.hoverText");

        vgImage3Url = vgYaml.getString("vg.image.3.url");
        vgImage3X = vgYaml.getInt("vg.image.3.x");
        vgImage3Y = vgYaml.getInt("vg.image.3.y");
        vgImage3W = vgYaml.getInt("vg.image.3.w");
        vgImage3H = vgYaml.getInt("vg.image.3.h");
        vgImage3AddX = vgYaml.getInt("vg.image.3.addX");

        vgTextX1 = vgYaml.getInt("vg.text.1.x");
        vgTextY1 = vgYaml.getInt("vg.text.1.y");
        vgTextString1 = vgYaml.getStringList("vg.text.1.string");

        vgTextX2 = vgYaml.getInt("vg.text.2.x");
        vgTextY2 = vgYaml.getInt("vg.text.2.y");
        vgTextString2 = vgYaml.getStringList("vg.text.2.string");

        vgTextX3 = vgYaml.getInt("vg.text.3.x");
        vgTextY3 = vgYaml.getInt("vg.text.3.y");
        vgTextString3 = vgYaml.getStringList("vg.text.3.string");

        vgTextX4 = vgYaml.getInt("vg.text.4.x");
        vgTextY4 = vgYaml.getInt("vg.text.4.y");
        vgTextString4 = vgYaml.getStringList("vg.text.4.string");

        vgTextX5 = vgYaml.getInt("vg.text.5.x");
        vgTextY5 = vgYaml.getInt("vg.text.5.y");
        vgTextString5 = vgYaml.getStringList("vg.text.5.string");

        vgTextX6 = vgYaml.getInt("vg.text.6.x");
        vgTextY6 = vgYaml.getInt("vg.text.6.y");
        vgTextString6 = vgYaml.getStringList("vg.text.6.string");

        vgTextX7 = vgYaml.getInt("vg.text.7.x");
        vgTextY7 = vgYaml.getInt("vg.text.7.y");
        vgTextString7 = vgYaml.getStringList("vg.text.7.string");

        vgButtonText7 = vgYaml.getString("vg.button.7.text");
        vgButtonUrl17 = vgYaml.getString("vg.button.7.url1");
        vgButtonUrl27 = vgYaml.getString("vg.button.7.url2");
        vgButtonID7 = vgYaml.getInt("vg.button.7.id");
        vgButtonX7 = vgYaml.getInt("vg.button.7.x");
        vgButtonY7 = vgYaml.getInt("vg.button.7.y");
        vgButtonW7 = vgYaml.getInt("vg.button.7.w");
        vgButtonH7 = vgYaml.getInt("vg.button.7.h");
        vgButtonHoverText7 = vgYaml.getStringList("vg.button.7.hoverText");

        vgButtonText8 = vgYaml.getString("vg.button.8.text");
        vgButtonUrl18 = vgYaml.getString("vg.button.8.url1");
        vgButtonUrl28 = vgYaml.getString("vg.button.8.url2");
        vgButtonID8 = vgYaml.getInt("vg.button.8.id");
        vgButtonX8 = vgYaml.getInt("vg.button.8.x");
        vgButtonY8 = vgYaml.getInt("vg.button.8.y");
        vgButtonW8 = vgYaml.getInt("vg.button.8.w");
        vgButtonH8 = vgYaml.getInt("vg.button.8.h");
        vgButtonHoverText8 = vgYaml.getStringList("vg.button.8.hoverText");

        vgButtonText9 = vgYaml.getString("vg.button.9.text");
        vgButtonUrl19 = vgYaml.getString("vg.button.9.url1");
        vgButtonUrl29 = vgYaml.getString("vg.button.9.url2");
        vgButtonID9 = vgYaml.getInt("vg.button.9.id");
        vgButtonX9 = vgYaml.getInt("vg.button.9.x");
        vgButtonY9 = vgYaml.getInt("vg.button.9.y");
        vgButtonW9 = vgYaml.getInt("vg.button.9.w");
        vgButtonH9 = vgYaml.getInt("vg.button.9.h");
        vgButtonHoverText9 = vgYaml.getStringList("vg.button.9.hoverText");

        vgButtonText11 = vgYaml.getString("vg.button.11.text");
        vgButtonUrl111 = vgYaml.getString("vg.button.11.url1");
        vgButtonUrl211 = vgYaml.getString("vg.button.11.url2");
        vgButtonID11 = vgYaml.getInt("vg.button.11.id");
        vgButtonX11 = vgYaml.getInt("vg.button.11.x");
        vgButtonY11 = vgYaml.getInt("vg.button.11.y");
        vgButtonW11 = vgYaml.getInt("vg.button.11.w");
        vgButtonH11 = vgYaml.getInt("vg.button.11.h");
        vgButtonHoverText11 = vgYaml.getStringList("vg.button.11.hoverText");

        vgButtonText12 = vgYaml.getString("vg.button.12.text");
        vgButtonUrl112 = vgYaml.getString("vg.button.12.url1");
        vgButtonUrl212 = vgYaml.getString("vg.button.12.url2");
        vgButtonID12 = vgYaml.getInt("vg.button.12.id");
        vgButtonX12 = vgYaml.getInt("vg.button.12.x");
        vgButtonY12 = vgYaml.getInt("vg.button.12.y");
        vgButtonW12 = vgYaml.getInt("vg.button.12.w");
        vgButtonH12 = vgYaml.getInt("vg.button.12.h");
        vgButtonHoverText12 = vgYaml.getStringList("vg.button.12.hoverText");

        vgButtonText13 = vgYaml.getString("vg.button.13.text");
        vgButtonUrl113 = vgYaml.getString("vg.button.13.url1");
        vgButtonUrl213 = vgYaml.getString("vg.button.13.url2");
        vgButtonID13 = vgYaml.getInt("vg.button.13.id");
        vgButtonX13 = vgYaml.getInt("vg.button.13.x");
        vgButtonY13 = vgYaml.getInt("vg.button.13.y");
        vgButtonW13 = vgYaml.getInt("vg.button.13.w");
        vgButtonH13 = vgYaml.getInt("vg.button.13.h");

        vgButtonText14 = vgYaml.getString("vg.button.14.text");
        vgButtonUrl114 = vgYaml.getString("vg.button.14.url1");
        vgButtonUrl214 = vgYaml.getString("vg.button.14.url2");
        vgButtonID14 = vgYaml.getInt("vg.button.14.id");
        vgButtonX14 = vgYaml.getInt("vg.button.14.x");
        vgButtonY14 = vgYaml.getInt("vg.button.14.y");
        vgButtonW14 = vgYaml.getInt("vg.button.14.w");
        vgButtonH14 = vgYaml.getInt("vg.button.14.h");

        vgButtonText15 = vgYaml.getString("vg.button.15.text");
        vgButtonUrl115 = vgYaml.getString("vg.button.15.url1");
        vgButtonUrl215 = vgYaml.getString("vg.button.15.url2");
        vgButtonID15 = vgYaml.getInt("vg.button.15.id");
        vgButtonX15 = vgYaml.getInt("vg.button.15.x");
        vgButtonY15 = vgYaml.getInt("vg.button.15.y");
        vgButtonW15 = vgYaml.getInt("vg.button.15.w");
        vgButtonH15 = vgYaml.getInt("vg.button.15.h");
        vgButtonAddX15 = vgYaml.getInt("vg.button.15.addX");

        vgButtonText16 = vgYaml.getString("vg.button.16.text");
        vgButtonUrl116 = vgYaml.getString("vg.button.16.url1");
        vgButtonUrl216 = vgYaml.getString("vg.button.16.url2");
        vgButtonID16 = vgYaml.getInt("vg.button.16.id");
        vgButtonX16 = vgYaml.getInt("vg.button.16.x");
        vgButtonY16 = vgYaml.getInt("vg.button.16.y");
        vgButtonW16 = vgYaml.getInt("vg.button.16.w");
        vgButtonH16 = vgYaml.getInt("vg.button.16.h");
        vgButtonAddX16 = vgYaml.getInt("vg.button.16.addX");

        vgImageUrl6 = vgYaml.getString("vg.image.6.url");
        vgImageX6 = vgYaml.getInt("vg.image.6.x");
        vgImageY6 = vgYaml.getInt("vg.image.6.y");
        vgImageW6 = vgYaml.getInt("vg.image.6.w");
        vgImageH6 = vgYaml.getInt("vg.image.6.h");

        vgImageUrl7 = vgYaml.getString("vg.image.7.url");
        vgImageX7 = vgYaml.getInt("vg.image.7.x");
        vgImageY7 = vgYaml.getInt("vg.image.7.y");
        vgImageW7 = vgYaml.getInt("vg.image.7.w");
        vgImageH7 = vgYaml.getInt("vg.image.7.h");

        vgImageX8 = vgYaml.getInt("vg.image.8.x");
        vgImageY8 = vgYaml.getInt("vg.image.8.y");
        vgImageW8 = vgYaml.getInt("vg.image.8.w");
        vgImageH8 = vgYaml.getInt("vg.image.8.h");

        vgImageX9 = vgYaml.getInt("vg.image.9.x");
        vgImageY9 = vgYaml.getInt("vg.image.9.y");
        vgImageW9 = vgYaml.getInt("vg.image.9.w");
        vgImageH9 = vgYaml.getInt("vg.image.9.h");

        vgButtonText17 = vgYaml.getString("vg.button.17.text");
        vgButtonUrl117 = vgYaml.getString("vg.button.17.url1");
        vgButtonUrl217 = vgYaml.getString("vg.button.17.url2");
        vgButtonID17 = vgYaml.getInt("vg.button.17.id");
        vgButtonX17 = vgYaml.getInt("vg.button.17.x");
        vgButtonY17 = vgYaml.getInt("vg.button.17.y");
        vgButtonW17 = vgYaml.getInt("vg.button.17.w");
        vgButtonH17 = vgYaml.getInt("vg.button.17.h");
        vgButtonHoverText17 = vgYaml.getStringList("vg.button.17.hoverText");

        vgButtonText18 = vgYaml.getString("vg.button.18.text");
        vgButtonUrl118 = vgYaml.getString("vg.button.18.url1");
        vgButtonUrl218 = vgYaml.getString("vg.button.18.url2");
        vgButtonID18 = vgYaml.getInt("vg.button.18.id");
        vgButtonX18 = vgYaml.getInt("vg.button.18.x");
        vgButtonY18 = vgYaml.getInt("vg.button.18.y");
        vgButtonW18 = vgYaml.getInt("vg.button.18.w");
        vgButtonH18 = vgYaml.getInt("vg.button.18.h");

        vgButtonText19 = vgYaml.getString("vg.button.19.text");
        vgButtonUrl119 = vgYaml.getString("vg.button.19.url1");
        vgButtonUrl219 = vgYaml.getString("vg.button.19.url2");
        vgButtonID19 = vgYaml.getInt("vg.button.19.id");
        vgButtonX19 = vgYaml.getInt("vg.button.19.x");
        vgButtonY19 = vgYaml.getInt("vg.button.19.y");
        vgButtonW19 = vgYaml.getInt("vg.button.19.w");
        vgButtonH19 = vgYaml.getInt("vg.button.19.h");
        vgButtonAddX19 = vgYaml.getInt("vg.button.19.addX");

        vgImageUrl10 = vgYaml.getString("vg.image.10.url");
        vgImageX10 = vgYaml.getInt("vg.image.10.x");
        vgImageY10 = vgYaml.getInt("vg.image.10.y");
        vgImageW10 = vgYaml.getInt("vg.image.10.w");
        vgImageH10 = vgYaml.getInt("vg.image.10.h");

        vgImageUrl11 = vgYaml.getString("vg.image.11.url");
        vgImageX11 = vgYaml.getInt("vg.image.11.x");
        vgImageY11 = vgYaml.getInt("vg.image.11.y");
        vgImageW11 = vgYaml.getInt("vg.image.11.w");
        vgImageH11 = vgYaml.getInt("vg.image.11.h");

        vgImageX12 = vgYaml.getInt("vg.image.12.x");
        vgImageY12 = vgYaml.getInt("vg.image.12.y");
        vgImageW12 = vgYaml.getInt("vg.image.12.w");
        vgImageH12 = vgYaml.getInt("vg.image.12.h");

        vgButtonText20 = vgYaml.getString("vg.button.20.text");
        vgButtonUrl120 = vgYaml.getString("vg.button.20.url1");
        vgButtonUrl220 = vgYaml.getString("vg.button.20.url2");
        vgButtonID20 = vgYaml.getInt("vg.button.20.id");
        vgButtonX20 = vgYaml.getInt("vg.button.20.x");
        vgButtonY20 = vgYaml.getInt("vg.button.20.y");
        vgButtonW20 = vgYaml.getInt("vg.button.20.w");
        vgButtonH20 = vgYaml.getInt("vg.button.20.h");
        vgButtonHoverText20 = vgYaml.getStringList("vg.button.20.hoverText");

        vgButtonText21 = vgYaml.getString("vg.button.21.text");
        vgButtonUrl121 = vgYaml.getString("vg.button.21.url1");
        vgButtonUrl221 = vgYaml.getString("vg.button.21.url2");
        vgButtonID21 = vgYaml.getInt("vg.button.21.id");
        vgButtonX21 = vgYaml.getInt("vg.button.21.x");
        vgButtonY21 = vgYaml.getInt("vg.button.21.y");
        vgButtonW21 = vgYaml.getInt("vg.button.21.w");
        vgButtonH21 = vgYaml.getInt("vg.button.21.h");
        vgButtonHoverText21 = vgYaml.getStringList("vg.button.21.hoverText");

        vgButtonText22 = vgYaml.getString("vg.button.22.text");
        vgButtonUrl122 = vgYaml.getString("vg.button.22.url1");
        vgButtonUrl222 = vgYaml.getString("vg.button.22.url2");
        vgButtonID22 = vgYaml.getInt("vg.button.22.id");
        vgButtonX22 = vgYaml.getInt("vg.button.22.x");
        vgButtonY22 = vgYaml.getInt("vg.button.22.y");
        vgButtonW22 = vgYaml.getInt("vg.button.22.w");
        vgButtonH22 = vgYaml.getInt("vg.button.22.h");

        vgButtonText23 = vgYaml.getString("vg.button.23.text");
        vgButtonUrl123 = vgYaml.getString("vg.button.23.url1");
        vgButtonUrl223 = vgYaml.getString("vg.button.23.url2");
        vgButtonID23 = vgYaml.getInt("vg.button.23.id");
        vgButtonX23 = vgYaml.getInt("vg.button.23.x");
        vgButtonY23 = vgYaml.getInt("vg.button.23.y");
        vgButtonW23 = vgYaml.getInt("vg.button.23.w");
        vgButtonH23 = vgYaml.getInt("vg.button.23.h");

        vgButtonText24 = vgYaml.getString("vg.button.24.text");
        vgButtonUrl124 = vgYaml.getString("vg.button.24.url1");
        vgButtonUrl224 = vgYaml.getString("vg.button.24.url2");
        vgButtonID24 = vgYaml.getInt("vg.button.24.id");
        vgButtonX24 = vgYaml.getInt("vg.button.24.x");
        vgButtonY24 = vgYaml.getInt("vg.button.24.y");
        vgButtonW24 = vgYaml.getInt("vg.button.24.w");
        vgButtonH24 = vgYaml.getInt("vg.button.24.h");

        vgButtonText25 = vgYaml.getString("vg.button.25.text");
        vgButtonUrl125 = vgYaml.getString("vg.button.25.url1");
        vgButtonUrl225 = vgYaml.getString("vg.button.25.url2");
        vgButtonID25 = vgYaml.getInt("vg.button.25.id");
        vgButtonX25 = vgYaml.getInt("vg.button.25.x");
        vgButtonY25 = vgYaml.getInt("vg.button.25.y");
        vgButtonW25 = vgYaml.getInt("vg.button.25.w");
        vgButtonH25 = vgYaml.getInt("vg.button.25.h");
        vgButtonHoverText25 = vgYaml.getStringList("vg.button.25.hoverText");

        vslTextX1 = vgYaml.getInt("vsl.text.1.x");
        vslTextY1 = vgYaml.getInt("vsl.text.1.y");
        vslTextAddY1 = vgYaml.getInt("vsl.text.1.addY");
        vslTextString1 = vgYaml.getStringList("vsl.text.1.string");

        vslImageUrl11 = vgYaml.getString("vsl.image.11.url");
        vslImageX11 = vgYaml.getInt("vsl.image.11.x");
        vslImageY11 = vgYaml.getInt("vsl.image.11.y");
        vslImageW11 = vgYaml.getInt("vsl.image.11.w");
        vslImageH11 = vgYaml.getInt("vsl.image.11.h");
        vslImageAddY11 = vgYaml.getInt("vsl.image.11.addY");

        vgButtonText26 = vgYaml.getString("vg.button.26.text");
        vgButtonUrl126 = vgYaml.getString("vg.button.26.url1");
        vgButtonUrl226 = vgYaml.getString("vg.button.26.url2");
        vgButtonID26 = vgYaml.getInt("vg.button.26.id");
        vgButtonX26 = vgYaml.getInt("vg.button.26.x");
        vgButtonY26 = vgYaml.getInt("vg.button.26.y");
        vgButtonW26 = vgYaml.getInt("vg.button.26.w");
        vgButtonH26 = vgYaml.getInt("vg.button.26.h");

        vgButtonText27 = vgYaml.getString("vg.button.27.text");
        vgButtonUrl127 = vgYaml.getString("vg.button.27.url1");
        vgButtonUrl227 = vgYaml.getString("vg.button.27.url2");
        vgButtonID27 = vgYaml.getInt("vg.button.27.id");
        vgButtonX27 = vgYaml.getInt("vg.button.27.x");
        vgButtonY27 = vgYaml.getInt("vg.button.27.y");
        vgButtonW27 = vgYaml.getInt("vg.button.27.w");
        vgButtonH27 = vgYaml.getInt("vg.button.27.h");
        vgButtonAddX27 = vgYaml.getInt("vg.button.27.addX");

        vgTextX8 = vgYaml.getInt("vg.text.8.x");
        vgTextY8 = vgYaml.getInt("vg.text.8.y");
        vgTextString8 = vgYaml.getStringList("vg.text.8.string");
    }
}
