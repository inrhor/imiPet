package cn.mcres.imiPet.yaml.get;

import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.yaml.create.VanillaGuiYaml;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class GetVanillaGuiYaml {
    // Home Gui
    public static String homeTitle, followPetHomeName, followPetHomeString, curePetHomeString, curePetHomeName,
            foodPetHomeString, foodPetHomeName, transferHomeString, releaseHomeString,
            transferHomeName, releaseHomeName;
    public static Material followPetHomeMaterial, curePetHomeMaterial, foodPetHomeMaterial,
            transferHomeMaterial, releaseHomeMaterial;

    // ExpBox Gui
    public static String expBoxTitle, giveExpExpBoxString, giveExpExpBoxName;
    public static Material giveExpExpBoxMaterial;
    public static List<String> giveExpExpBoxLore;

    // Warehouse Gui
    public static String warehouseTitle, intoPackWarehouseString, intoPackWarehouseName;
    public static Material intoPackWarehouseMaterial;

    // UpdateInfo Gui
    public static String updateInfoTitle, renameUpdateInfoString, renameUpdateInfoName;
    public static Material renameUpdateInfoMaterial;

    // Evolution Gui
    public static String evolutionTitle, selectEvolutionString, selectEvolutionName,
            startEvolutionString, cannotEvolutionName;
    public static Material selectEvolutionMaterial, startEvolutionMaterial;

    // SelectPet
    public static String selectPetTitle, emptySelectPetString, emptySelectPetName;
    public static Material emptySelectPetMaterial;

    // Global
    public static String closeMaterialString, closeName, previousPageString, previousPageName, nextPageString, nextPageName,
            noSelectPetString, noSelectPetName, warehouseString, warehouseName, expBoxString, expBoxName,
            evolutionString, evolutionName, updatePetInfoString, updatePetInfoName;
    public static Material closeMaterial, previousPageMaterial, nextPageMaterial, noSelectPetMaterial, warehouseMaterial,
            expBoxMaterial, evolutionMaterial, updatePetInfoMaterial;
    public static List<String> petInfoLore, warehousePetInfo;

    // CastSkill
    public static String castSkillTitle, unSkillTitle;


    public static void get() {
        FileConfiguration guiYaml = VanillaGuiYaml.gui;

        // Home Gui
        homeTitle = GuiUtil.getName(guiYaml.getString("gui.home.title"));

        followPetHomeString = GuiUtil.getName(guiYaml.getString("gui.home.followPet.material"));
        followPetHomeMaterial = GuiUtil.getMaterial(followPetHomeString);
        followPetHomeName = GuiUtil.getName(guiYaml.getString("gui.home.followPet.name"));

        curePetHomeString = GuiUtil.getName(guiYaml.getString("gui.home.curePet.material"));
        curePetHomeMaterial = GuiUtil.getMaterial(curePetHomeString);
        curePetHomeName = GuiUtil.getName(guiYaml.getString("gui.home.curePet.name"));

        foodPetHomeString = GuiUtil.getName(guiYaml.getString("gui.home.foodPet.material"));
        foodPetHomeMaterial = GuiUtil.getMaterial(foodPetHomeString);
        foodPetHomeName = GuiUtil.getName(guiYaml.getString("gui.home.foodPet.name"));

        transferHomeString = GuiUtil.getName(guiYaml.getString("gui.home.transfer.material"));
        transferHomeMaterial = GuiUtil.getMaterial(transferHomeString);
        transferHomeName = GuiUtil.getName(guiYaml.getString("gui.home.transfer.name"));

        releaseHomeString = GuiUtil.getName(guiYaml.getString("gui.home.release.material"));
        releaseHomeMaterial = GuiUtil.getMaterial(releaseHomeString);
        releaseHomeName = GuiUtil.getName(guiYaml.getString("gui.home.release.name"));


        // ExpBox
        expBoxTitle = GuiUtil.getName(guiYaml.getString("gui.expBox.title"));

        giveExpExpBoxString = GuiUtil.getName(guiYaml.getString("gui.expBox.giveExp.material"));
        giveExpExpBoxMaterial = GuiUtil.getMaterial(giveExpExpBoxString);
        giveExpExpBoxName = GuiUtil.getName(guiYaml.getString("gui.expBox.giveExp.name"));
        giveExpExpBoxLore = guiYaml.getStringList("gui.expBox.giveExp.lore");


        // Warehouse
        warehouseTitle = GuiUtil.getName(guiYaml.getString("gui.warehouse.title"));

        intoPackWarehouseString = GuiUtil.getName(guiYaml.getString("gui.warehouse.intoPack.material"));
        intoPackWarehouseMaterial = GuiUtil.getMaterial(intoPackWarehouseString);
        intoPackWarehouseName = GuiUtil.getName(guiYaml.getString("gui.warehouse.intoPack.name"));


        // UpdateInfo
        updateInfoTitle = GuiUtil.getName(guiYaml.getString("gui.updateInfo.title"));

        renameUpdateInfoString = GuiUtil.getName(guiYaml.getString("gui.updateInfo.rename.material"));
        renameUpdateInfoMaterial = GuiUtil.getMaterial(renameUpdateInfoString);
        renameUpdateInfoName = GuiUtil.getName(guiYaml.getString("gui.updateInfo.rename.name"));


        // Evolution
        evolutionTitle = GuiUtil.getName(guiYaml.getString("gui.evolution.title"));

        selectEvolutionString = GuiUtil.getName(guiYaml.getString("gui.evolution.select.material"));
        selectEvolutionMaterial = GuiUtil.getMaterial(selectEvolutionString);
        selectEvolutionName = GuiUtil.getName(guiYaml.getString("gui.evolution.select.name"));

        startEvolutionString = GuiUtil.getName(guiYaml.getString("gui.evolution.start.material"));
        startEvolutionMaterial = GuiUtil.getMaterial(startEvolutionString);

        cannotEvolutionName = GuiUtil.getName(guiYaml.getString("gui.evolution.cannot.name"));


        // SelectPet
        selectPetTitle  = GuiUtil.getName(guiYaml.getString("gui.selectPet.title"));

        emptySelectPetString = GuiUtil.getName(guiYaml.getString("gui.selectPet.emptyPet.material"));
        emptySelectPetMaterial = GuiUtil.getMaterial(emptySelectPetString);
        emptySelectPetName = GuiUtil.getName(guiYaml.getString("gui.selectPet.emptyPet.name"));



        // Global
        closeMaterialString = guiYaml.getString("gui.global.close.material");
        closeMaterial = GuiUtil.getMaterial(closeMaterialString);
        closeName = GuiUtil.getName(guiYaml.getString("gui.global.close.name"));

        previousPageString = guiYaml.getString("gui.global.previousPage.material");
        previousPageMaterial = GuiUtil.getMaterial(previousPageString);
        previousPageName = GuiUtil.getName(guiYaml.getString("gui.global.previousPage.name"));

        nextPageString = guiYaml.getString("gui.global.nextPage.material");
        nextPageMaterial = GuiUtil.getMaterial(nextPageString);
        nextPageName = GuiUtil.getName(guiYaml.getString("gui.global.nextPage.name"));

        noSelectPetString = guiYaml.getString("gui.global.noSelectPet.material");
        noSelectPetMaterial = GuiUtil.getMaterial(noSelectPetString);
        noSelectPetName = GuiUtil.getName(guiYaml.getString("gui.global.noSelectPet.name"));

        warehouseString = guiYaml.getString("gui.global.warehouse.material");
        warehouseMaterial = GuiUtil.getMaterial(warehouseString);
        warehouseName = GuiUtil.getName(guiYaml.getString("gui.global.warehouse.name"));

        expBoxString = guiYaml.getString("gui.global.expBox.material");
        expBoxMaterial = GuiUtil.getMaterial(expBoxString);
        expBoxName = GuiUtil.getName(guiYaml.getString("gui.global.expBox.name"));

        updatePetInfoString = guiYaml.getString("gui.global.updatePetInfo.material");
        updatePetInfoMaterial = GuiUtil.getMaterial(updatePetInfoString);
        updatePetInfoName = GuiUtil.getName(guiYaml.getString("gui.global.updatePetInfo.name"));

        evolutionString = guiYaml.getString("gui.global.evolution.material");
        evolutionMaterial = GuiUtil.getMaterial(evolutionString);
        evolutionName = GuiUtil.getName(guiYaml.getString("gui.global.evolution.name"));

        petInfoLore = guiYaml.getStringList("gui.global.petInfo.lore");
        warehousePetInfo = guiYaml.getStringList("gui.global.warehousePetInfo.lore");

        castSkillTitle = GuiUtil.getName(guiYaml.getString("gui.castSkill.title"));
        unSkillTitle = GuiUtil.getName(guiYaml.getString("gui.unSkillTitle.title"));
    }
}
