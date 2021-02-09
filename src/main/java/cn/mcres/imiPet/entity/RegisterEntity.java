package cn.mcres.imiPet.entity;

import cn.mcres.imiPet.entity.ride.v1_13_R1.RidePet;

public class RegisterEntity {
    public void run(String serverVersion) {
        switch (serverVersion) {
            case "v1_16_R3":
                cn.mcres.imiPet.entity.ride.v1_16_R3.RidePet.registerEntity();
                cn.mcres.imiPet.entity.v1_16_R3.FollowPet.registerEntity();
                break;
            case "v1_16_R2":
                cn.mcres.imiPet.entity.ride.v1_16_R2.RidePet.registerEntity();
                cn.mcres.imiPet.entity.v1_16_R2.FollowPet.registerEntity();
                break;
            case "v1_16_R1":
                cn.mcres.imiPet.entity.ride.v1_16_R1.RidePet.registerEntity();
                cn.mcres.imiPet.entity.v1_16_R1.FollowPet.registerEntity();
                break;
            case "v1_15_R1":
                cn.mcres.imiPet.entity.ride.v1_15_R1.RidePet.registerEntity();
                cn.mcres.imiPet.entity.v1_15_R1.FollowPet.registerEntity();
                break;
            case "v1_14_R1":
                cn.mcres.imiPet.entity.ride.v1_14_R1.RidePet.registerEntity();
                cn.mcres.imiPet.entity.v1_14_R1.FollowPet.registerEntity();
                break;
            case "v1_13_R2":
                cn.mcres.imiPet.entity.ride.v1_13_R2.RidePet.entityTypes =
                        cn.mcres.imiPet.entity.ride.v1_13_R2.RidePet.injectNewEntity(
                                "custom_armorstand",
                                "armor_stand",
                                cn.mcres.imiPet.entity.ride.v1_13_R2.RidePet.class,
                                cn.mcres.imiPet.entity.ride.v1_13_R2.RidePet::new);
                cn.mcres.imiPet.entity.v1_13_R2.FollowPet.entityTypes =
                        cn.mcres.imiPet.entity.v1_13_R2.FollowPet.injectNewEntity(
                                "custom_wolf",
                                "wolf",
                                cn.mcres.imiPet.entity.v1_13_R2.FollowPet.class,
                                cn.mcres.imiPet.entity.v1_13_R2.FollowPet::new);
                break;
            case "v1_13_R1":
                RidePet.entityTypes =
                        RidePet.injectNewEntity(
                                "custom_armorstand",
                                "armor_stand",
                                RidePet.class,
                                RidePet::new);
                cn.mcres.imiPet.entity.v1_13_R1.FollowPet.entityTypes =
                        cn.mcres.imiPet.entity.v1_13_R1.FollowPet.injectNewEntity(
                                "custom_wolf",
                                "wolf",
                                cn.mcres.imiPet.entity.v1_13_R1.FollowPet.class,
                                cn.mcres.imiPet.entity.v1_13_R1.FollowPet::new);
                break;
        }
    }
}
