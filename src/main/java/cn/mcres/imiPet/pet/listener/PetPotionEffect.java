package cn.mcres.imiPet.pet.listener;

import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.pet.BuildPet;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PetPotionEffect implements Listener {
    @EventHandler
    void onPetPotion(PotionSplashEvent ev) {
        for (PotionEffect potionEffect : ev.getPotion().getEffects()) {
            PotionEffectType potionEffectType = potionEffect.getType();
            if (potionEffectType.equals(PotionEffectType.HEAL) || potionEffectType.equals(PotionEffectType.REGENERATION)) {
                for (Entity affectedEntity : ev.getAffectedEntities()) {
                    if (ModelEntityManager.buildPets.isEmpty()) {
                        for (BuildPet pets : ModelEntityManager.buildPets) {
                            Entity pet = pets.getPet();
                            if (affectedEntity.equals(pet)) {
                                ModelInfoManager infoManager = ModelInfoManager.petModelList.get(pets.getModelId());
                                if (!infoManager.isCureRequirementHPPotion()) {
                                    ev.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
