package cn.mcres.imiPet.entity.ride.v1_16_R2;

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
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

public class RidePet extends EntityArmorStand {
    private static EntityTypes entityTypes;

    public static void registerEntity() {
        String customName = "custom";
        Map<String, Type<?>> types = (Map<String, Type<?>>)DataConverterRegistry.a()
                .getSchema(DataFixUtils.makeKey(SharedConstants
                        .getGameVersion().getWorldVersion()))
                .findChoiceType(DataConverterTypes.ENTITY_TREE).types();
        types.put("minecraft:" + customName, types.get("minecraft:armor_stand"));
        EntityTypes.Builder<Entity> a = EntityTypes.Builder.a(RidePet::new, EnumCreatureType.MONSTER);
        entityTypes = IRegistry.a(IRegistry.ENTITY_TYPE, customName, a.a(customName));
    }

    public static void spawnEntity(Player player) {
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        Location location = player.getLocation();
        UUID playerUUID = player.getUniqueId();
        String modelId = MapAll.ridePetList.get(playerUUID).get(0);
        boolean isSmall = Boolean.parseBoolean(MapAll.ridePetList.get(playerUUID).get(1));
        Entity entity = entityTypes.createCreature(world,
                null,
                null,
                null,
                new BlockPosition(location.getX(), location.getY(), location.getZ()), EnumMobSpawn.EVENT,
                false,
                false);
        ArmorStand armorStand = (ArmorStand) entity.getBukkitEntity();
        armorStand.setAI(false);
        armorStand.setSmall(isSmall);
        armorStand.setSilent(true);
        armorStand.setBasePlate(false);
        armorStand.setInvulnerable(true);
        armorStand.setMetadata("imipet.ride", new FixedMetadataValue(ImiPet.loader.getPlugin(), true));
        armorStand.addPassenger(player);
        MapAll.ridePet.put(playerUUID, armorStand);
        world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);

        ModelEntityManager.fastCommonSpawnModel(armorStand, modelId);

        armorStand.setVisible(false);
//        new ModelEntity(armorStand, modelId, false);
    }

    public RidePet(EntityTypes<? extends EntityMonster> entitytypes, World world) {
        super(EntityTypes.ARMOR_STAND, world);
    }

    private static Field jump = null; // 跳跃字段
    // 反射跳跃字段
    static {
        try {
            jump = EntityLiving.class.getDeclaredField("jumping");
            jump.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e1) {
            e1.printStackTrace();
        }
    }

    protected boolean isJumpOrFly = false;

    @Override
    public boolean b(float f, float f1) {
        if (!this.isJumpOrFly) {
            return super.b(f, f1);
        }
        return false;
    }

    public void g(Vec3D vec3d) {
        if (this.passengers != null && !this.passengers.isEmpty()) {
            EntityLiving passenger = (EntityLiving) this.passengers.get(0);

            UUID passengerUUID = passenger.getUniqueID();
            if (MapAll.ridePetList.get(passengerUUID) == null) return;

            boolean canFly = Boolean.parseBoolean(MapAll.ridePetList.get(passengerUUID).get(2));

            if (this.onGround) {
                this.isJumpOrFly = false;
            }

            if (this.a(TagsFluid.WATER)) {
                this.setMot(this.getMot().add(0, 0.4, 0));
            }

            this.lastYaw = (this.yaw = passenger.yaw);
            this.pitch = passenger.pitch * 0.5F;
            setYawPitch(this.yaw, this.pitch);
            this.aD = (this.aB = this.yaw);

            double motionSideways = passenger.aS * 0.5F;
            double motionForward = passenger.aT;

            if (motionForward <= 0.0F) {
                motionForward *= 0.25F;
            }

            motionSideways *= 0.85F;

            float speed = 0.2F;
            double jumpHeight = 0.5f;
            float ascendSpeed = 0.2f;

            ride(motionSideways, motionForward, vec3d.y, speed);

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
                        if (this.getMot().y < ascendSpeed) {
                            this.setMot(this.getMot().x, ascendSpeed, this.getMot().z);
                            this.isJumpOrFly = true;
                        }
                    }else {
                        if (onGround) {
                            this.setMot(this.getMot().x, jumpHeight, this.getMot().z);
                            this.isJumpOrFly = true;
                        }
                    }
                }
            }
        }
    }

    private void ride(double motionSideways, double motionForward, double motionUpwards, float speedModifier) {
        double locY;
        float f2;
        float speed;
        float swimmSpeed;

        if (this.a(TagsFluid.WATER)) {
            locY = this.locY();
            speed = 0.8F;
            swimmSpeed = 0.02F;

            this.a(swimmSpeed, new Vec3D(motionSideways, motionUpwards, motionForward));
            this.move(EnumMoveType.SELF, this.getMot());
            double motX = this.getMot().x * (double) speed;
            double motY = this.getMot().y * 0.800000011920929D;
            double motZ = this.getMot().z * (double) speed;
            motY -= 0.02D;
            if (this.positionChanged && this.e(this.getMot().x, this.getMot().y + 0.6000000238418579D - this.locY() + locY, this.getMot().z)) {
                motY = 0.30000001192092896D;
            }
            this.setMot(motX, motY, motZ);
        } else if (this.a(TagsFluid.LAVA)) {
            locY = this.locY();
            this.a(0.02F, new Vec3D(motionSideways, motionUpwards, motionForward));
            this.move(EnumMoveType.SELF, this.getMot());
            double motX = this.getMot().x * 0.5D;
            double motY = this.getMot().y * 0.5D;
            double motZ = this.getMot().z * 0.5D;
            motY -= 0.02D;
            if (this.positionChanged && this.e(this.getMot().x, this.getMot().y + 0.6000000238418579D - this.locY() + locY, this.getMot().z)) {
                motY = 0.30000001192092896D;
            }
            this.setMot(motX, motY, motZ);
        } else {
            double minY;
            minY = this.getBoundingBox().minY;

            float friction = 0.91F;
            if (this.onGround) {
                friction = this.world.getType(new BlockPosition(MathHelper.floor(this.locX()), MathHelper.floor(minY) - 1, MathHelper.floor(this.locZ()))).getBlock().getFrictionFactor() * 0.91F;
            }

            speed = speedModifier * (0.16277136F / (friction * friction * friction));

            this.a(speed, new Vec3D(motionSideways, motionUpwards, motionForward));
            friction = 0.91F;
            if (this.onGround) {
                friction = this.world.getType(new BlockPosition(MathHelper.floor(this.locX()), MathHelper.floor(minY) - 1, MathHelper.floor(this.locZ()))).getBlock().getFrictionFactor() * 0.91F;
            }

            double motX = this.getMot().x;
            double motY = this.getMot().y;
            double motZ = this.getMot().z;

            if (this.isClimbing()) {
                swimmSpeed = 0.15F;
                motX = MathHelper.a(motX, -swimmSpeed, swimmSpeed);
                motZ = MathHelper.a(motZ, -swimmSpeed, swimmSpeed);
                this.fallDistance = 0.0F;
                if (motY < -0.15D) {
                    motY = -0.15D;
                }
            }

            Vec3D mot = new Vec3D(motX, motY, motZ);

            this.move(EnumMoveType.SELF, mot);
            if (this.positionChanged && this.isClimbing()) {
                motY = 0.2D;
            }

            motY -= 0.08D;

            motY *= 0.9800000190734863D;
            motX *= friction;
            motZ *= friction;

            this.setMot(motX, motY, motZ);
        }

        this.av = this.aw;
        locY = this.locX() - this.lastX;
        double d1 = this.locZ() - this.lastZ;
        f2 = MathHelper.sqrt(locY * locY + d1 * d1) * 4.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        this.aw += (f2 - this.aw) * 0.4F;
        this.au += this.aw;
    }
}
