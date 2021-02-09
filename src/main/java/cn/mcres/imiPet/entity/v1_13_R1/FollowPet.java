package cn.mcres.imiPet.entity.v1_13_R1;

import cn.mcres.imiPet.entity.v1_13_R1.ai.FollowOwner;
import com.mojang.datafixers.types.Type;
import java.util.function.Function;
import net.minecraft.server.v1_13_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Map;

public class FollowPet extends EntityWolf {
    public static EntityTypes entityTypes;

    public static Wolf getWolf(Player player, Location location) {
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        Entity entity = entityTypes.b(
                world,
                null,
                null,
                null,
                new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                false,
                false);

        EntityInsentient nmsEntity = (EntityInsentient) (entity.getBukkitEntity()).getHandle();
        PathfinderGoalSelector goalSelector = nmsEntity.goalSelector;
        goalSelector.a(3, new FollowOwner(nmsEntity, ((CraftPlayer) player).getHandle(), 1.0F, 3.0F));

        world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return  (Wolf) entity.getBukkitEntity();
    }

    public static EntityTypes injectNewEntity(String name, String extend_from, Class<? extends net.minecraft.server.v1_13_R1.Entity> clazz, Function<? super World, ? extends Entity> function) {
        Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.n).types();
        dataTypes.put("minecraft:" + name, dataTypes.get("minecraft:" + extend_from));
        return EntityTypes.a(name, EntityTypes.a.a(clazz, function));
    }

    public FollowPet(World world) {
        super(world);
    }

    @Override
    protected void n() {
        this.goalSit = new PathfinderGoalSit(this);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
        this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntitySkeletonAbstract.class, false));
    }
}
