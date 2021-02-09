package cn.mcres.imiPet.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PetLevelChangeEvent extends Event {
    private static final HandlerList petLevelChangHandler = new HandlerList();
    private Player player;
    private int oldLevel;
    private int newLevel;
    private UUID petUUID;

    /**
     * 宠物改变等级事件(通过给予经验升级时)
     *
     * @param player 玩家
     * @param oldLevel 旧等级
     * @param newLevel 新等级
     * @param petUUID 宠物
     */
    public PetLevelChangeEvent(final Player player, final int oldLevel, final int newLevel, final UUID petUUID) {
        this.player = player;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
        this.petUUID = petUUID;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getOldLevel() {
        return this.oldLevel;
    }

    public int getNewLevel() {
        return this.newLevel;
    }

    public UUID getPetUUID() {
        return petUUID;
    }

    public static HandlerList getHandlerList(){
        return petLevelChangHandler;
    }

    @Override
    public HandlerList getHandlers(){
        return petLevelChangHandler;
    }
}
