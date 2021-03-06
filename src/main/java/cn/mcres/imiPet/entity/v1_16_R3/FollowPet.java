package cn.mcres.imiPet.entity.v1_16_R3;

import cn.mcres.imiPet.entity.v1_16_R3.ai.FollowOwner;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Map;

public class FollowPet extends EntityWolf {
    private static EntityTypes entityTypes;

    public static void registerEntity() {
        String customName = "custom_wolf";
        Map<String, Type<?>> types = (Map<String, Type<?>>) DataConverterRegistry.a()
                .getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion()))
                .findChoiceType(DataConverterTypes.ENTITY).types();
        types.put("minecraft:" + customName, types.get("minecraft:wolf"));
        EntityTypes.Builder<Entity> a = EntityTypes.Builder.a(FollowPet::new, EnumCreatureType.MONSTER);
        entityTypes = IRegistry.a(IRegistry.ENTITY_TYPE, customName, a.a(customName));
    }

    public static Wolf getWolf(Player player, Location location) {
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        Entity entity = entityTypes.createCreature(world,
                null,
                null,
                null,
                new BlockPosition(location.getX(), location.getY(), location.getZ()), EnumMobSpawn.EVENT,
                false,
                false);

        EntityInsentient nmsEntity = (EntityInsentient) (entity.getBukkitEntity()).getHandle();
        PathfinderGoalSelector goalSelector = nmsEntity.goalSelector;
        goalSelector.a(3, new FollowOwner(nmsEntity, ((CraftPlayer) player).getHandle(), 1.0F, 3.0F));

        world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);

        return  (Wolf) entity.getBukkitEntity();
    }

    public FollowPet(EntityTypes<? extends EntityMonster> entitytypes, World world) {
        super(EntityTypes.WOLF, world);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
        this.targetSelector.a(3, (new PathfinderGoalHurtByTarget(this, new Class[0])).a(new Class[0]));
        this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntitySkeletonAbstract.class, false));
    }
}
