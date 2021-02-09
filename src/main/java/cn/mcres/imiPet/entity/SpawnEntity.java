package cn.mcres.imiPet.entity;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.entity.ride.v1_12_R1.RidePet;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class SpawnEntity {
    public static void ridePet(Player player) {
        String serverVersion = ImiPet.loader.getServerInfo().getServerVersion();
        switch (serverVersion) {
            case "v1_16_R3":
                cn.mcres.imiPet.entity.ride.v1_16_R3.RidePet.spawnEntity(player);
                break;
            case "v1_16_R2":
                cn.mcres.imiPet.entity.ride.v1_16_R2.RidePet.spawnEntity(player);
                break;
            case "v1_16_R1":
                cn.mcres.imiPet.entity.ride.v1_16_R1.RidePet.spawnEntity(player);
                break;
            case "v1_15_R1":
                cn.mcres.imiPet.entity.ride.v1_15_R1.RidePet.spawnEntity(player);
                break;
            case "v1_14_R1":
                cn.mcres.imiPet.entity.ride.v1_14_R1.RidePet.spawnEntity(player);
                break;
            case "v1_13_R2":
                cn.mcres.imiPet.entity.ride.v1_13_R2.RidePet.spawnEntity(player);
                break;
            case "v1_13_R1":
                cn.mcres.imiPet.entity.ride.v1_13_R1.RidePet.spawnEntity(player);
                break;
            case "v1_12_R1":
                new RidePet(player).spawnEntity();
                break;
            case "v1_11_R1":
                new cn.mcres.imiPet.entity.ride.v1_11_R1.RidePet(player).spawnEntity();
                break;
            case "v1_10_R1":
                new cn.mcres.imiPet.entity.ride.v1_10_R1.RidePet(player).spawnEntity();
                break;
            case "v1_9_R2":
                new cn.mcres.imiPet.entity.ride.v1_9_R2.RidePet(player).spawnEntity();
                break;
            case "v1_9_R1":
                new cn.mcres.imiPet.entity.ride.v1_9_R1.RidePet(player).spawnEntity();
                break;
        }
    }

    public static Wolf followPet(Player player, Location location) {
        String serverVersion = ImiPet.loader.getServerInfo().getServerVersion();
        switch (serverVersion) {
            case "v1_16_R3":
                return cn.mcres.imiPet.entity.v1_16_R3.FollowPet.getWolf(player, location);
            case "v1_16_R2":
                return cn.mcres.imiPet.entity.v1_16_R2.FollowPet.getWolf(player, location);
            case "v1_16_R1":
                return cn.mcres.imiPet.entity.v1_16_R1.FollowPet.getWolf(player, location);
            case "v1_15_R1":
                return cn.mcres.imiPet.entity.v1_15_R1.FollowPet.getWolf(player, location);
            case "v1_14_R1":
                return cn.mcres.imiPet.entity.v1_14_R1.FollowPet.getWolf(player, location);
            case "v1_13_R2":
                return cn.mcres.imiPet.entity.v1_13_R2.FollowPet.getWolf(player, location);
            case "v1_13_R1":
                return cn.mcres.imiPet.entity.v1_13_R1.FollowPet.getWolf(player, location);
            case "v1_12_R1":
                return new cn.mcres.imiPet.entity.v1_12_R1.FollowPet(player, location).getWolf();
            case "v1_11_R1":
                return new cn.mcres.imiPet.entity.v1_11_R1.FollowPet(player, location).getWolf();
            case "v1_10_R1":
                return new cn.mcres.imiPet.entity.v1_10_R1.FollowPet(player, location).getWolf();
            case "v1_9_R2":
                return new cn.mcres.imiPet.entity.v1_9_R2.FollowPet(player, location).getWolf();
            case "v1_9_R1":
                return new cn.mcres.imiPet.entity.v1_9_R1.FollowPet(player, location).getWolf();
        }
        return null;
    }
}
