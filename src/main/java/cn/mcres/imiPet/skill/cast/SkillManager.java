package cn.mcres.imiPet.skill.cast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.ArmorStand;

public class SkillManager {
    public static List<ArmorStand> skillStandList = new ArrayList<>();

    public static void removeAll() {
        if (!skillStandList.isEmpty()) {
            for (ArmorStand stand : skillStandList) {
                if (stand == null || stand.isDead()) continue;
                stand.remove();
            }
            skillStandList.clear();
        }
    }

    public static void removePetSkillStand(UUID wolfUUID) {
        if (getPetSkillStand(wolfUUID) != null) {
            remove(getPetSkillStand(wolfUUID));
        }
    }

    private static ArmorStand getPetSkillStand(UUID wolfUUID) {
        if (!skillStandList.isEmpty()) {
            for (ArmorStand stand : skillStandList) {
                if (stand == null || stand.isDead()) continue;
                String meta = "imipet.skill.entity";
                if (!stand.hasMetadata(meta)) continue;
                String uuid = stand.getMetadata(meta).get(0).asString();
                if (uuid.equals(wolfUUID.toString())) return stand;
            }
        }
        return null;
    }

    public static void remove(ArmorStand stand) {
        stand.remove();
        skillStandList.remove(stand);
    }
}
