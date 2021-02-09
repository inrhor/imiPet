package cn.mcres.imiPet.api.fastHandle;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.entity.SpawnEntity;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class RideHandle {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }


    /**
     * 快速使用骑行功能
     *
     * @param player
     * @param petUUID
     */
    public static void ridePet(Player player, UUID petUUID) {
        if (MapAll.ridePetList.get(player.getUniqueId()) != null) {
//            Msg.send(player, RIDING_PET);
            TLocale.sendTo(player, "RIDING_PET");
            return;
        }
        String modelId = info().getPetModelId(player, petUUID, "pets");
        ModelInfoManager modelInfoManager = ModelInfoManager.petModelList.get(modelId);
        if (info().getPetNowHP(player, petUUID, "pets") > 0) {
            if (modelInfoManager.isRideEnable()) {
                addRideList(player, modelId);
                ridePetSpawn(player);
            }else {
//                Msg.send(player, CAN_NOT_RIDE);
                TLocale.sendTo(player, "CAN_NOT_RIDE");
            }
        }else {
//            Msg.send(player, PET_NO_HP);
            TLocale.sendTo(player, "PET_NO_HP");
        }
    }

    /**
     * 将模型配置相关信息放入MapAll.ridePet
     *
     * @param player
     * @param modelId
     */
    public static void addRideList(Player player, String modelId) {
        List<String> rideList = new ArrayList<>();
        rideList.add(modelId); // 模型ID
        ModelInfoManager modelInfoManager = ModelInfoManager.petModelList.get(modelId);
        rideList.add(/*GetPetsYaml.getString(modelId, "basis.ride.isSmall")*/modelInfoManager.getRideIsSmall()); // 是否为小型盔甲架
        rideList.add(/*GetPetsYaml.getString(modelId, "basis.ride.canFly")*/modelInfoManager.getRideCanFly()); // 是否可以飞行，否则是跳跃
        MapAll.ridePetList.put(player.getUniqueId(), rideList);
    }

    /**
     * 实现坐骑
     *
     * @param player
     */
    public static void ridePetSpawn(Player player) {
        if (MapAll.ridePetList.get(player.getUniqueId()) == null) return;
        if (!info().getFollowingPetUUID(player).isEmpty()) {
            for (UUID follow : info().getFollowingPetUUID(player)) {
                FollowSetHandle.runPetRemove(player, follow);
            }
        }
        SpawnEntity.ridePet(player);
    }

    /**
     * 停止坐骑
     *
     * @param player
     * @param ridePet
     * @param needSpawnFollowPet 停止坐骑后是否召唤出宠物，不改变跟随状态信息
     */
    public static void ridePetStop(Player player, Entity ridePet, boolean needSpawnFollowPet) {
        ridePetStop(player.getUniqueId(), ridePet, needSpawnFollowPet);
    }

    /**
     * 停止坐骑
     *
     * @param playerUUID
     * @param ridePet
     * @param needSpawnFollowPet 停止坐骑后是否召唤出宠物，不改变跟随状态信息
     */
    public static void ridePetStop(UUID playerUUID, Entity ridePet, boolean needSpawnFollowPet) {
        ridePet.remove();
        MapAll.ridePetList.remove(playerUUID);
        if (!needSpawnFollowPet) return;
        if (info().getFollowingPetUUID(playerUUID).isEmpty()) return;
        Player player = Bukkit.getPlayer(playerUUID);
        runCheckAndRide(player);
    }

    private static void runCheckAndRide(Player player) {
        new BukkitRunnable() {
            public void run() {
                if (player == null) return;
                if (!player.isOnGround()) {
                    runCheckAndRide(player);
                }else {
                    FollowSetHandle.runPetSpawn(player);
                }
            }
        }.runTaskLater(ImiPet.loader.getPlugin(), 20);
    }
}
