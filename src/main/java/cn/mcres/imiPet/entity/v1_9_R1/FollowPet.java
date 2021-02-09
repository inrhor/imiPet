package cn.mcres.imiPet.entity.v1_9_R1;

import cn.mcres.imiPet.entity.Utils;
import cn.mcres.imiPet.entity.v1_9_R1.ai.FollowOwner;
import java.util.Map;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class FollowPet extends EntityWolf {
    private Player player;

    public FollowPet(Player player, Location location) {
        super(((CraftWorld) player.getLocation().getWorld()).getHandle());
        this.player = player;
        this.setPosition(location.getX(), location.getY(), location.getZ());
    }

    static {
        String arm = "custom_armorstand";
        ((Map) Utils.getPrivateField("c", EntityTypes.class, null)).put(arm, FollowPet.class);
        ((Map)Utils.getPrivateField("d", EntityTypes.class, null)).put(FollowPet.class, arm);
        ((Map)Utils.getPrivateField("f", EntityTypes.class, null)).put(FollowPet.class, 95);
        ((Map)Utils.getPrivateField("g", EntityTypes.class, null)).put(arm, 95);
    }

    public Wolf getWolf() {
        WorldServer world = ((CraftWorld) this.player.getWorld()).getHandle();
        EntityInsentient nmsEntity = (EntityInsentient) (this.getBukkitEntity()).getHandle();
        PathfinderGoalSelector goalSelector = nmsEntity.goalSelector;
        goalSelector.a(3, new FollowOwner(nmsEntity, ((CraftPlayer) this.player).getHandle(), 1.0F, 3.0F));
        this.setInvisible(true);
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return  (Wolf) this.getBukkitEntity();
    }

    @Override
    protected void r() {
        this.goalSit = new PathfinderGoalSit(this);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
        this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntitySkeleton.class, false));
    }
}
