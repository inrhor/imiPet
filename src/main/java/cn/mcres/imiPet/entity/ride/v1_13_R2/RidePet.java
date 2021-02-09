package cn.mcres.imiPet.entity.ride.v1_13_R2;

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
import cn.mcres.imiPet.other.MapAll;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class RidePet extends EntityArmorStand {
    public static EntityTypes entityTypes;

    public static void spawnEntity(Player player) {
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        Location location = player.getLocation();
        UUID playerUUID = player.getUniqueId();
        String modelId = MapAll.ridePetList.get(playerUUID).get(0);
        boolean isSmall = Boolean.parseBoolean(MapAll.ridePetList.get(playerUUID).get(1));
        Entity entity = entityTypes.a(
                world,
                null,
                null,
                null,
                new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                false,
                false);
        ArmorStand armorStand = (ArmorStand) entity.getBukkitEntity();
        armorStand.setAI(false);
        armorStand.setSmall(isSmall);
        armorStand.setSilent(true);
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);
        armorStand.setInvulnerable(true);
        armorStand.setMetadata("imipet.ride", new FixedMetadataValue(ImiPet.loader.getPlugin(), true));
        armorStand.addPassenger(player);
        MapAll.ridePet.put(playerUUID, armorStand);
        world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);

        ModelEntityManager.fastCommonSpawnModel(armorStand, modelId);
//        new ModelEntity(armorStand, modelId, false);
    }

    public static EntityTypes injectNewEntity(String name, String extend_from, Class<? extends net.minecraft.server.v1_13_R2.Entity> clazz, Function<? super World, ? extends Entity> function) {
        Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.n).types();
        dataTypes.put("minecraft:" + name, dataTypes.get("minecraft:" + extend_from));
        return EntityTypes.a(name, EntityTypes.a.a(clazz, function));
    }

    public RidePet(World world) {
        super(world);
    }

    private static Field jump = null;
    static {
        try {
            jump = EntityLiving.class.getDeclaredField("bg");
            jump.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e1) {
            e1.printStackTrace();
        }
    }

    protected boolean isJumpOrFly = false;

    public void c(float f, float f1) {
        if (!this.isJumpOrFly) {
            super.c(f, f1);
        }
    }

    public void a(float motionSideways, float motionForward, float f) {
        if (this.passengers != null && !this.passengers.isEmpty()) {
            EntityLiving passenger = (EntityLiving) this.passengers.get(0);

            UUID passengerUUID = passenger.getUniqueID();

            if (MapAll.ridePetList.get(passengerUUID) == null) return;

            boolean canFly = Boolean.parseBoolean(MapAll.ridePetList.get(passengerUUID).get(2));

            if (this.onGround) {
                this.isJumpOrFly = false;
            }

            if (this.a(TagsFluid.WATER)) {
                this.motY += 0.4;
            }

            this.lastYaw = (this.yaw = passenger.yaw);
            this.pitch = passenger.pitch * 0.5F;
            setYawPitch(this.yaw, this.pitch);
            this.aS = (this.aQ = this.yaw);

            motionSideways = passenger.bh * 0.5F;
            motionForward = passenger.bj;

            if (motionForward <= 0.0F) {
                motionForward *= 0.25F;
            }

            motionSideways *= 0.85F;

            float speed = 0.2F;
            double jumpHeight = 0.5f;
            float ascendSpeed = 0.2f;

            ride(motionSideways, motionForward, f, speed);

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
                    }else {
                        if (onGround) {
                            this.motY = jumpHeight;
                            this.isJumpOrFly = true;
                        }
                    }
                }
            }
        }
    }

    private void ride(float motionSideways, float f, float motionForward, float speedModifier) {
        double locY;
        float f2;
        float speed;
        float swimSpeed;

        if (this.isInWater()) {
            locY = this.locY;
            speed = 0.8F;
            swimSpeed = 0.02F;

            this.a(motionSideways, motionForward, f, swimSpeed);
            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
            this.motX *= speed;
            this.motY *= 0.800000011920929D;
            this.motZ *= speed;
            this.motY -= 0.02D;
            if (this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579D - this.locY + locY, this.motZ)) {
                this.motY = 0.30000001192092896D;
            }
        } else if (this.ax()) {
            locY = this.locY;
            this.a(motionSideways, motionForward, f, 0.02F);
            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
            this.motY -= 0.02D;
            if (this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579D - this.locY + locY, this.motZ)) {
                this.motY = 0.30000001192092896D;
            }
        } else {
            float friction = 0.91F;

            speed = speedModifier * (0.16277136F / (friction * friction * friction));

            this.a(motionSideways, motionForward, f, speed);
            friction = 0.91F;

            if (this.z_()) {
                swimSpeed = 0.15F;
                this.motX = MathHelper.a(this.motX, -swimSpeed, swimSpeed);
                this.motZ = MathHelper.a(this.motZ, -swimSpeed, swimSpeed);
                this.fallDistance = 0.0F;
                if (this.motY < -0.15D) {
                    this.motY = -0.15D;
                }
            }

            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
            if (this.positionChanged && this.z_()) {
                this.motY = 0.2D;
            }

            this.motY -= 0.08D;

            this.motY *= 0.9800000190734863D;
            this.motX *= friction;
            this.motZ *= friction;
        }

        this.aI = this.aJ;
        locY = this.locX - this.lastX;
        double d1 = this.locZ - this.lastZ;
        f2 = MathHelper.sqrt(locY * locY + d1 * d1) * 4.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        this.aJ += (f2 - this.aJ) * 0.4F;
        this.aK += this.aJ;
    }
}
