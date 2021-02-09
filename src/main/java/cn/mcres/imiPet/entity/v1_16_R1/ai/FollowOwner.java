package cn.mcres.imiPet.entity.v1_16_R1.ai;

import net.minecraft.server.v1_16_R1.EntityInsentient;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.PathfinderGoal;

public class FollowOwner extends PathfinderGoal {
    private EntityInsentient entity;
    private EntityPlayer owner;
    private double speed;
    private float distanceSquared;

    public FollowOwner(EntityInsentient entity, EntityPlayer owner, double speed, float distance) {
        this.entity = entity;
        this.owner = owner;
        this.speed = speed;
        this.distanceSquared = distance * distance;
    }

    @Override
    public boolean a() {
        // 条件
        if (entity.getGoalTarget() != null) { // 如果目标不是空的
            if (!entity.getGoalTarget().isAlive()) { // 如果目标实体死了
                entity.setGoalTarget(null); // 清除攻击目标
            }else { // 如果目标活着
                float newDistanceStopAttack = distanceSquared+160.0F;
                return  (entity.h(owner) > (double) newDistanceStopAttack); // 距离过长时停止攻击
            }
        }
        return (owner != null && owner.isAlive() && entity.h(owner) > (double)distanceSquared);
    }

    @Override
    public void d() {
        entity.getNavigation().n();
    }

    @Override
    public void e() {
        // 控制
        entity.setGoalTarget(null);
        float newDistance = distanceSquared+350.0F;
        if (entity.h(owner) > (double) newDistance) {
            if (owner.isOnGround() || owner.inWater) {
                entity.getBukkitEntity().teleport(owner.getBukkitEntity().getLocation());
            }
        }else {
            entity.getNavigation().a(owner, speed);
        }
    }
}
