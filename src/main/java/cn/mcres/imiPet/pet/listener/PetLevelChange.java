package cn.mcres.imiPet.pet.listener;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.attributePlus.AttributeManager;
import cn.mcres.imiPet.api.buff.BuffAPManager;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.events.PetLevelChangeEvent;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PetLevelChange implements Listener {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    @EventHandler
    void onPetLevelChange(PetLevelChangeEvent ev) {
        Player player = ev.getPlayer();
        UUID petUUID = ev.getPetUUID();
        int oldLevel = ev.getOldLevel();
        int newLevel = ev.getNewLevel();
        // 判断是否升级
        if (newLevel > oldLevel) {
            String modelId = info().getPetModelId(player, petUUID, "pets");

            ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);

            // 获取公式
            double newMinAttack = MathTool.keepTwoDouble(MathTool.stringOperation(
                    Objects.requireNonNull(infoManager.getAddMinAttackFormula()),
                    MathTool.map(new HashMap<>(),
                            "level", oldLevel,
                            "min_damage", info().getPetMinDamage(player, petUUID, "pets")),
                    modelId + "#addMinAttackFormula"
            ));
            double newMaxAttack = MathTool.keepTwoDouble(MathTool.stringOperation(
                    Objects.requireNonNull(infoManager.getAddMaxAttackFormula()),
                    MathTool.map(new HashMap<>(),
                            "level", oldLevel,
                            "max_damage", info().getPetMaxDamage(player, petUUID, "pets")),
                    modelId + "#addMaxAttackFormula"
            ));
            int newFood = MathTool.rounding(MathTool.stringOperation(
                    Objects.requireNonNull(infoManager.getAddFoodFormula()),
                    MathTool.map(new HashMap<>(),
                            "level", oldLevel,
                            "max_food", info().getPetMaxFood(player, petUUID, "pets")),
                    modelId + "#addFoodFormula"
            ));
            int newMaxExp = MathTool.rounding(MathTool.stringOperation(
                    Objects.requireNonNull(infoManager.getLevelExpFormula()),
                    MathTool.map(new HashMap<>(),
                            "level", oldLevel),
                    modelId + "#levelExpFormula"
            ));
            double newMaxHP = MathTool.rounding(MathTool.stringOperation(
                    Objects.requireNonNull(infoManager.getAddMaxHPFormula()),
                    MathTool.map(new HashMap<>(),
                            "level", oldLevel,
                            "max_hp", info().getPetMaxHP(player, petUUID, "pets")),
                    modelId + "#addMaxHPFormula"
            ));
            info().setPetMaxExp(player, newMaxExp, petUUID, "pets");
            info().setPetMinDamage(player, newMinAttack, petUUID, "pets");
            info().setPetMaxDamage(player, newMaxAttack, petUUID, "pets");
            info().setPetMaxFood(player, newFood, petUUID, "pets");
            info().setPetMaxHP(player, newMaxHP, petUUID, "pets");

            UUID playerUUID = player.getUniqueId();

            if (APLib.APLibEnable && infoManager.isApEnable()) {
                AttributeManager.entityUp(
                        playerUUID,
                        petUUID,
                        info().getPetApAttribute(player.getUniqueId(), petUUID, "pets"),
                        infoManager.getApLevelUpList(),
                        "pets");
                BuffAPManager.upPetBuffAP(playerUUID, petUUID, infoManager, "pets");

            }

            /*for (String msg : PET_LEVEL_UP) {
                Msg.send(player, msg.replaceAll("%imipet_level%", String.valueOf(newLevel)));
            }*/
            TLocale.sendTo(player, "PET_LEVEL_UP", String.valueOf(newLevel));
        }
    }

}
