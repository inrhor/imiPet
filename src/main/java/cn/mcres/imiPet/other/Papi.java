package cn.mcres.imiPet.other;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.build.utils.MathTool;
import cn.mcres.imiPet.model.ModelInfoManager;
import io.izzel.taboolib.module.locale.TLocale;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Papi extends PlaceholderExpansion {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    @Override
    public String getIdentifier() {
        return "imipet";
    }

    @Override
    public String getAuthor() {
        return "inrhor";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public String onPlaceholderRequest(Player player, String key) {
        if (player == null) {
            return null;
        }
        UUID playerUUID = player.getUniqueId();
        List<UUID> list = info().getPetsPackList(playerUUID);
        String type = "pets";
        if (!list.isEmpty()) {
            UUID petUUID = getPetUUID(list, player);
            ModelInfoManager infoManager = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, type));
            if ("name".equals(key)) {
                return info().getPetName(player, petUUID, type);
            }
            if ("level".equals(key)) {
                return String.valueOf(info().getPetLevel(player, petUUID, type));
            }
            if ("nowexp".equals(key)) {
                return String.valueOf(info().getPetNowExp(player, petUUID, type));
            }
            if ("maxexp".equals(key)) {
                return String.valueOf(info().getPetMaxExp(player, petUUID, type));
            }
            if ("nowhp".equals(key)) {
                double nowHP = Math.round(info().getPetNowHP(player, petUUID, type));
                return String.valueOf(nowHP);
            }
            if ("maxhp".equals(key)) {
                return String.valueOf(info().getPetMaxHP(player, petUUID, type));
            }
            if ("nowfood".equals(key)) {
                return String.valueOf(info().getPetNowFood(player, petUUID, type));
            }
            if ("maxfood".equals(key)) {
                return String.valueOf(info().getPetMaxFood(player, petUUID, type));
            }
            if (("damage").equals(key)) {
                return ReplaceAll.getDamageString(player, petUUID, type);
            }
            if ("follow".equals(key)) {
                if (info().getPetFollow(player, petUUID)) {
                    return TLocale.asString("PAPI_FOLLOWING");
                } else {
                    return TLocale.asString("PAPI_NOT_FOLLOWING");
                }
            }
            if ("cureneedmoney".equals(key)) {
                return String.valueOf(infoManager.getCureRequirementHPMoney());
            }
            if ("foodneedmoney".equals(key)) {
                /*if (GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "food.requirement.money") != null) {
                    return GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "food.requirement.money");
                }*/
                return String.valueOf(infoManager.getFoodRequirementMoney());
            }
            if ("evolutionlevel".equals(key)) {
                /*if (GetPetsYaml.getBoolean(info().getPetModelId(player, petUUID, type), "evolution.enable")) {
                    return GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "evolution.requirement.level");
                }*/
                return String.valueOf(infoManager.getEvolutionRequirementLevel());
            }
            String getLast = key.substring(key.lastIndexOf("_") + 1);
            if (MathTool.isIntNumber(getLast)) {
                int index = Integer.parseInt(getLast);
                if (index <= 6) {
                    petUUID = getPetUUID(list, index);
                    if (petUUID == null) return null;
                    ModelInfoManager infoManager2 = ModelInfoManager.petModelList.get(info().getPetModelId(player, petUUID, type));
                    if (("name_" + getLast).equals(key)) {
                        return info().getPetName(player, petUUID, type);
                    }
                    if (("level_" + getLast).equals(key)) {
                        return String.valueOf(info().getPetLevel(player, petUUID, type));
                    }
                    if (("nowexp_" + getLast).equals(key)) {
                        return String.valueOf(info().getPetNowExp(player, petUUID, type));
                    }
                    if (("maxexp_" + getLast).equals(key)) {
                        return String.valueOf(info().getPetMaxExp(player, petUUID, type));
                    }
                    if (("nowhp_" + getLast).equals(key)) {
                        double nowHP = Math.round(info().getPetNowHP(player, petUUID, type));
                        return String.valueOf(nowHP);
                    }
                    if (("maxhp_" + getLast).equals(key)) {
                        return String.valueOf(info().getPetMaxHP(player, petUUID, type));
                    }
                    if (("nowfood_" + getLast).equals(key)) {
                        return String.valueOf(info().getPetNowFood(player, petUUID, type));
                    }
                    if (("maxfood_" + getLast).equals(key)) {
                        return String.valueOf(info().getPetMaxFood(player, petUUID, type));
                    }
                    if (("damage_" + getLast).equals(key)) {
                        return ReplaceAll.getDamageString(player, petUUID, type);
                    }
                    if (("follow_" + getLast).equals(key)) {
                        if (info().getPetFollow(player, petUUID)) {
                            return TLocale.asString("PAPI_FOLLOWING");
                        } else {
                            return TLocale.asString("PAPI_NOT_FOLLOWING");
                        }
                    }
                    if (("cureneedmoney_" + getLast).equals(key)) {
                        /*if (GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "cureHP.requirement.money") != null) {
                            return GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "cureHP.requirement.money");
                        }*/
                        return String.valueOf(infoManager2.getCureRequirementHPMoney());
                    }
                    if (("foodneedmoney_" + getLast).equals(key)) {
                        /*if (GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "food.requirement.money") != null) {
                            return GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "food.requirement.money");
                        }*/
                        return String.valueOf(infoManager2.getFoodRequirementMoney());
                    }
                    if (("evolutionlevel_" + getLast).equals(key)) {
                        /*if (GetPetsYaml.getBoolean(info().getPetModelId(player, petUUID, type), "evolution.enable")) {
                            return GetPetsYaml.getString(info().getPetModelId(player, petUUID, type), "evolution.requirement");
                        }*/
                        return String.valueOf(infoManager2.getEvolutionRequirementMoney());
                    }
                }
            }
        }
        if ("packamount".equals(key)) {
            return String.valueOf(info().getPetsPackAmount(playerUUID));
        }
        if ("whamount".equals(key)) {
            return String.valueOf(info().getPetsWarehouseAmount(playerUUID));
        }
        if ("petamount".equals(key)) {
            int pack = info().getPetsPackAmount(playerUUID);
            int warehouse = info().getPetsWarehouseAmount(playerUUID);
            int amount = pack+warehouse;
            return String.valueOf(amount);
        }
        if ("expbox".equals(key)) {
            return String.valueOf(info().getExpBox(playerUUID));
        }

        return null;
    }

    private UUID getPetUUID(List<UUID> list, int index) {
        if (list.size() >= index) {
            return list.get(index);
        }
        return null;
    }

    private UUID getPetUUID(List<UUID> list, Player player) {
        if (list != null) {
            if (MapAll.guiHomeSelectPet.get(player) != null) {
                return list.get(MapAll.guiHomeSelectPet.get(player));
            } else if (MapAll.guiSelectPet.get(player) != null) {
                return list.get(MapAll.guiSelectPet.get(player));
            }
        }
        return null;
    }
}
