package cn.mcres.imiPet.entity.ride.v1_10_R1;

/*
 * This file is part of MyPet
 *
 * Copyright © 2011-2019 Keyle
 * MyPet is licensed under the GNU Lesser General Public License.
 *
 * MyPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.entity.Utils;
import cn.mcres.imiPet.other.MapAll;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class RidePet extends EntityArmorStand {
    private Player player;

    public RidePet(Player player) {
        super(((CraftWorld) player.getLocation().getWorld()).getHandle());
        this.player = player;
    }

    static {
        String arm = "custom_armorstand";
        ((Map) Utils.getPrivateField("c", EntityTypes.class, null)).put(arm, RidePet.class);
        ((Map)Utils.getPrivateField("d", EntityTypes.class, null)).put(RidePet.class, arm);
        ((Map)Utils.getPrivateField("f", EntityTypes.class, null)).put(RidePet.class, 30);
        ((Map)Utils.getPrivateField("g", EntityTypes.class, null)).put(arm, 30);
    }

    public void spawnEntity() {
        WorldServer world = ((CraftWorld) this.player.getWorld()).getHandle();
        UUID playerUUID = this.player.getUniqueId();
        String modelId = MapAll.ridePetList.get(playerUUID).get(0);
        boolean isSmall = Boolean.parseBoolean(MapAll.ridePetList.get(playerUUID).get(1));
        ArmorStand armorStand = (ArmorStand) this.getBukkitEntity();
        armorStand.setAI(false);
        armorStand.setSmall(isSmall);
        armorStand.setSilent(true);
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);
        armorStand.setInvulnerable(true);
        armorStand.teleport(this.player);
        armorStand.setPassenger(this.player);
        armorStand.setMetadata("imipet.ride", new FixedMetadataValue(ImiPet.loader.getPlugin(), true));
        MapAll.ridePet.put(playerUUID, armorStand);
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

        ModelEntityManager.fastCommonSpawnModel(armorStand, modelId);
//        new ModelEntity(armorStand, modelId, false);
    }

    private static Field jump = null; // 跳跃字段
    static {
        // 反射一下EntityLiving里的跳跃字段
        try {
            jump = EntityLiving.class.getDeclaredField("be");
            jump.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e1) {
            e1.printStackTrace();
        }
    }

    protected boolean isJumpOrFly = false;

    public void e(float f, float f1) {
        if (!this.isJumpOrFly) {
            super.e(f, f1);
        }
    }

    public void g(float motionSideways, float motionForward) {
        if (this.passengers != null && !this.passengers.isEmpty()) {
            EntityLiving passenger = (EntityLiving) this.passengers.get(0);

            UUID passengerUUID = passenger.getUniqueID();

            if (MapAll.ridePetList.get(passengerUUID) == null) return;

            boolean canFly = Boolean.parseBoolean(MapAll.ridePetList.get(passengerUUID).get(2));

            if (this.onGround) {
                this.isJumpOrFly = false;
            }

            //apply pitch & yaw
            this.lastYaw = (this.yaw = passenger.yaw);
            this.pitch = passenger.pitch * 0.5F;
            setYawPitch(this.yaw, this.pitch);
            this.aP = (this.aN = this.yaw);

            // get motion from passenger (player)
            motionSideways = passenger.bf * 0.5F;
            motionForward = passenger.bg;

            // backwards is slower
            if (motionForward <= 0.0F) {
                motionForward *= 0.25F;
            }
            // sideways is slower too but not as slow as backwards
            motionSideways *= 0.85F;

            float speed = 0.2F;
            double jumpHeight = 0.5f;
            float ascendSpeed = 0.2f;

            ride(motionSideways, motionForward, speed); // apply motion

            if (jump != null && this.isVehicle()) {
                boolean doJump = false;
                if (jump != null) {
                    try {
                        doJump = jump.getBoolean(passenger);
                    } catch (IllegalAccessException ignored) {
                    }
                }

                if (doJump) {
                    if (canFly) {
                        if (this.motY < ascendSpeed) {
                            this.motY = ascendSpeed;
                            this.isJumpOrFly = true;
                        }
                    } else {
                        if (onGround) {
                            this.motY = jumpHeight;
                            this.isJumpOrFly = true;
                        }
                    }
                }
            }
        }
    }

    private void ride(float motionSideways, float motionForward, float speedModifier) {
        double locY;
        float f2;
        float speed;
        float swimmSpeed;

        if (this.isInWater()) {
            locY = this.locY;
            speed = 0.8F;
            swimmSpeed = 0.02F;

            this.a(motionSideways, motionForward, swimmSpeed);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= (double) speed;
            this.motY *= 0.800000011920929D;
            this.motZ *= (double) speed;
            this.motY -= 0.02D;
            if (this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579D - this.locY + locY, this.motZ)) {
                this.motY = 0.30000001192092896D;
            }
        } else if (this.ao()) { // in lava
            locY = this.locY;
            this.a(motionSideways, motionForward, 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
            this.motY -= 0.02D;
            if (this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579D - this.locY + locY, this.motZ)) {
                this.motY = 0.30000001192092896D;
            }
        } else {
            float friction = 0.91F;
            if (this.onGround) {
                friction = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
            }

            speed = speedModifier * (0.16277136F / (friction * friction * friction));

            this.a(motionSideways, motionForward, speed);
            friction = 0.91F;
            if (this.onGround) {
                friction = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
            }

            if (this.m_()) {
                swimmSpeed = 0.15F;
                this.motX = MathHelper.a(this.motX, (double) (-swimmSpeed), (double) swimmSpeed);
                this.motZ = MathHelper.a(this.motZ, (double) (-swimmSpeed), (double) swimmSpeed);
                this.fallDistance = 0.0F;
                if (this.motY < -0.15D) {
                    this.motY = -0.15D;
                }
            }

            this.move(this.motX, this.motY, this.motZ);
            if (this.positionChanged && this.m_()) {
                this.motY = 0.2D;
            }

            this.motY -= 0.08D;

            this.motY *= 0.9800000190734863D;
            this.motX *= (double) friction;
            this.motZ *= (double) friction;
        }

        this.aG = this.aH;
        locY = this.locX - this.lastX;
        double d1 = this.locZ - this.lastZ;
        f2 = MathHelper.sqrt(locY * locY + d1 * d1) * 4.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        this.aH += (f2 - this.aH) * 0.4F;
        this.aI += this.aH;
    }
}
