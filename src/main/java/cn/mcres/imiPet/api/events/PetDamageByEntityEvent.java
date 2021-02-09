package cn.mcres.imiPet.api.events;

import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PetDamageByEntityEvent extends Event {
    private static final HandlerList petBeHitHandler = new HandlerList();

    private BuildPet pet;
    private UUID petUUID;
    private UUID ownerUUID;
    private Player owner;
    private Entity attacker;
    private double damage;

    /**
     * 宠物被伤害，不可能是主人造成的事件
     * @param pet 宠物
     * @param owner 宠物的主人
     * @param attacker 攻击者
     * @param damage 伤害值
     */
    public PetDamageByEntityEvent(BuildPet pet, final Player owner, final Entity attacker, final double damage) {
        this.pet = pet;
        this.owner = owner;
        this.attacker = attacker;
        this.damage = damage;
    }

    /**
     *
     * @param petUUID 宠物
     * @param ownerUUID 宠物的主人
     * @param attacker 攻击者
     * @param damage 伤害值
     */
    public PetDamageByEntityEvent(UUID petUUID, final UUID ownerUUID, final Entity attacker, final double damage) {
        this.petUUID = petUUID;
        this.ownerUUID = ownerUUID;
        this.attacker = attacker;
        this.damage = damage;
    }

    /**
     *
     * @return 受到攻击的宠物
     */
    public BuildPet getPet() {
        return pet;
    }

    /**
     *
     * @return 宠物的主人
     */
    public Player getOwner() {
        return owner;
    }

    /**
     *
     * @return 攻击者
     */
    public Entity getAttacker() {
        return attacker;
    }

    /**
     *
     * @return 造成的伤害
     */
    public double getDamage() {
        return damage;
    }

    public static HandlerList getHandlerList(){
        return petBeHitHandler;
    }

    @Override
    public HandlerList getHandlers(){
        return petBeHitHandler;
    }
}
