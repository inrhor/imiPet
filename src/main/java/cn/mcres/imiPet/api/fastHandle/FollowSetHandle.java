package cn.mcres.imiPet.api.fastHandle;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.buff.BuffAPManager;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.api.skill.SkillCastManager;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.other.MapAll;
import cn.mcres.imiPet.pet.BuildPet;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;



public class FollowSetHandle {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    /**
     * 快速处理宠物跟随状态信息
     *
     * @param player       玩家名称
     * @param newFollowPet 新跟随的宠物
     */
    public static void runInfo(Player player, UUID newFollowPet) {
        // TODO: 需要更新! 有多个宠物可以跟随!!!!
        // TODO: 单个宠物跟随
        if (newFollowPet != null) {
            // 获得设置目标的宠物是否跟随
            if (info().getPetFollow(player, newFollowPet)) { // 如果是跟随
                // 收回背包
                info().setPetFollow(player, false, newFollowPet);
                runPetRemove(player, newFollowPet);
            } else { // 如果不是跟随
                // 检查是否在安全位置
                if (!SafeLocation.isSafeLocation(BuildPet.getLocation(player))) {
//                    Msg.send(player, UNSAFE_LOCATION);
                    TLocale.sendTo(player, "UNSAFE_LOCATION");
                    return;
                }
                // 检查是否有跟随的宠物
                final Set<UUID> followings = info().getFollowingPetUUID(player);
                if (!followings.isEmpty()) {
                    // 如果跟随的不是目标宠物
                    // TODO: 单个宠物
                    // 清除其他跟随
                    boolean fd = false;
                    for (UUID uid0 : followings) {
                        if (uid0.equals(newFollowPet)) {
                            fd = true;
                        } else {
                            info().setPetFollow(player, false, uid0);
                            runPetRemove(player, uid0);
                        }
                    }
                    if (fd) { //目标宠物已经在跟随了
                        return;
                    }
                }
                // 设置目标宠物跟随
                // 检查宠物是否有血量
                if (!(info().getPetNowHP(player, newFollowPet, "pets") > 0)) {
//                    Msg.send(player, PET_NO_HP);
                    TLocale.sendTo(player, "PET_NO_HP");
                    return;
                }
                info().setPetFollow(player, true, newFollowPet);
                runPetSpawn(player);
            }
        }

    }

    /**
     * 快速收回宠物，不改变跟随状态信息
     *
     * @param player 玩家名称
     * @param petUID 宠物UUID
     */
    public static void runPetRemove(@NotNull Player player, UUID petUID) {
        final Iterator<BuildPet> iterator = ModelEntityManager.buildPets.iterator();
        while (iterator.hasNext()) {
            BuildPet pet = iterator.next();
            if (pet.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                if (petUID == null || petUID.equals(pet.getPetUUID())) {
                    /*if (MapAll.getModelEntity(pet.getPet()) != null) {
//                        ModelManager.revertModel(MapAll.getModelEntity(pet.getPet()));
                    }*/
                    if (pet.getHDHologram() != null) {
                        pet.getHDHologram().delete();
                    }
                    if (pet.getTrHologram() != null) {
                        pet.getTrHologram().delete();
                    }
                    if (pet.getModelEntityManager().getState().startsWith("skill-")) {
                        SkillCastManager.removePetSkill(pet.getPet().getUniqueId());
                    }
                    BuffAPManager.removeAllBuff(pet);
                    MapAll.canNotMoveLoc.remove(pet.getPet());
                    pet.removePet();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 快速召唤宠物
     *
     * @param player 玩家名称
     */
    public static void runPetSpawn(Player player) {
        UUID puid = player.getUniqueId();
        root_loop:
        for (UUID follow : info().getFollowingPetUUID(player)) {
            if (info().getPetNowHP(player, follow, "pets") > 0) {
                for (BuildPet exists : ModelEntityManager.buildPets) {// Check pet is exists.
                    if (exists.getPlayer() != null) {
                        if (exists.getPlayer().getUniqueId().equals(puid)) {
                            if (exists.getPetUUID().equals(follow)) {
                                // Pet exists. Skip this pet,
                                continue root_loop;
                            }
                        }
                    }
                }
                if (SafeLocation.isSafeLocation(BuildPet.getLocation(player))) {
                    String modelId = info().getPetModelId(player, follow, "pets");
                    BuildPet pet = new BuildPet(player, modelId, info().getPetName(player, follow, "pets"));
                    pet.setPetUUID(follow);
                    pet.createPet();
                    ModelEntityManager.buildPets.add(pet);
                } else {
                    info().setPetFollow(player, false, follow);
//                    Msg.send(player, UNSAFE_LOCATION);
                    TLocale.sendTo(player, "UNSAFE_LOCATION");
                }
            }
        }
    }
}
