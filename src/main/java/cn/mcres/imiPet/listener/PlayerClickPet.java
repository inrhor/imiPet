package cn.mcres.imiPet.listener;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.fastHandle.FoodHandle;
import cn.mcres.imiPet.api.fastHandle.RideHandle;
import cn.mcres.imiPet.build.utils.interactionSend.CMIHDHologram;
import cn.mcres.imiPet.build.utils.interactionSend.HDHologram;
import cn.mcres.imiPet.build.utils.interactionSend.TrHologram;
import cn.mcres.imiPet.instal.lib.CMILib;
import cn.mcres.imiPet.instal.lib.HDLib;
import cn.mcres.imiPet.instal.lib.TrHDLib;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;

public class PlayerClickPet implements Listener {

    @EventHandler
    void rightClick(PlayerInteractAtEntityEvent ev) {
        Entity entity = ev.getRightClicked();
        EntityType entityType = entity.getType();

        // 禁止取下部件
        if (entityType.equals(EntityType.ARMOR_STAND)) {
            if (entity.hasMetadata("imipet:modelId") || entity.hasMetadata("imipet.skill")) {
                ev.setCancelled(true);
            }
        }

        Player player = ev.getPlayer();

        if (entityType.equals(EntityType.ARMOR_STAND) || entityType.equals(EntityType.WOLF)) {
            if (entity.hasMetadata("imipet.uuid")) {
                UUID playerUUID = player.getUniqueId();
                UUID metadataPlayerUUID = UUID.fromString(entity.getMetadata("imipet:playerUUID").get(0).asString());
                if (MapAll.clickPetCool.get(playerUUID)!=null) return;
                MapAll.clickPetCool.put(playerUUID, true);
                String modelId = entity.getMetadata("imipet:modelId").get(0).asString();
                UUID petUUID = UUID.fromString(entity.getMetadata("imipet.uuid").get(0).asString());
                ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);
                if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    // 拿着物品右键宠物
                    if (!metadataPlayerUUID.equals(playerUUID)) return;
                    if (MapAll.ridePetList.get(playerUUID)!=null) return;
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
//                    if (!GetPetsYaml.getBoolean(modelId, "eat.enable")) return;
                    if (!infoManager.isEatEnable()) return;
                    if (!player.hasPermission("imipet.food") || !player.hasPermission("imipet.player.use")) return;
                    FoodHandle.foodItemCheck(player, petUUID, modelId, itemStack, "pets");
                }else {
                    if (player.isSneaking()) {
                        // 交互式显示信息 空手潜行右键
                        if (/*GetPetsYaml.getBoolean(modelId, "interaction.info.enable")*/infoManager.isInfoClickEnable()) {
                            String useShow = infoManager.getInfoClickLib();
                            Player owner = Bukkit.getPlayer(metadataPlayerUUID);
                            switch (useShow) {
                                case "tr":
                                    if (TrHDLib.TrHDLibEnable) {
                                        TrHologram.send(owner, player, entity, modelId, petUUID);
                                    }
                                    break;
                                case "hd":
                                    if (HDLib.HDLibEnable) {
                                        HDHologram.send(owner, player, entity, modelId, petUUID);
                                    }
                                    break;
                                case "cmi":
                                    if (CMILib.CMILibEnable) {
                                        CMIHDHologram.send(owner, entity, modelId, petUUID);
                                    }
                                    break;
                            }
                        }
                    }else {
                        // 空手右键
                        // 坐骑
                        if (!metadataPlayerUUID.equals(playerUUID)) return;
                        if (!player.hasPermission("imipet.ride") || !player.hasPermission("imipet.player.use")) return;
                        RideHandle.ridePet(player, petUUID);
                    }
                }
                Bukkit.getScheduler().runTaskLater(ImiPet.loader.getPlugin(), () -> {
                    MapAll.clickPetCool.remove(playerUUID);
                }, 20);
            }
        }
    }
}
