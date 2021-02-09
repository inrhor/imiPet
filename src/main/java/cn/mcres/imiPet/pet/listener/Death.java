package cn.mcres.imiPet.pet.listener;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class Death implements Listener {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    @EventHandler
    void onPetDeath(EntityDeathEvent ev) {
        if (!ModelEntityManager.buildPets.isEmpty()) {
            for (BuildPet pets : ModelEntityManager.buildPets) {
                Entity pet = pets.getPet();
                Player player = pets.getPlayer();
                UUID petUUID = pets.getPetUUID();
                if (ev.getEntity().equals(pet)) {
                    FollowSetHandle.runPetRemove(player, petUUID);
                    info().setPetNowHP(player, 0, petUUID, "pets");
                    info().setPetFollow(player, false, petUUID);
                    break;
                }
            }
        }
    }
}
