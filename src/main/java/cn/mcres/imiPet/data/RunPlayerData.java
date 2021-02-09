package cn.mcres.imiPet.data;

import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.data.PetDefiner;
import com.google.common.cache.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RunPlayerData implements Info {
    private static final LoadingCache<UUID, YamlConfiguration> datas = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .removalListener((RemovalListener<UUID, YamlConfiguration>) removalNotification -> {
                store(removalNotification.getKey(), removalNotification.getValue());
            })
            .build(new CacheLoader<UUID, YamlConfiguration>() {
                @Override
                public YamlConfiguration load(@NotNull UUID uuid) throws Exception {
                    return loadData(uuid);
                }
            });

    private static void store(UUID uid, YamlConfiguration data) {
        store(getDataStore(uid), data);
    }

    @Override
    public void storeAll() {
        saveAll();
    }

    @Override
    public void forceSave(@NotNull UUID player) {
        datas.invalidate(player);
    }

    public static void saveAll() {
        datas.invalidateAll();
    }

    @Override
    public UUID definePet(final @NotNull PetDefiner info) {
        synchronized (info) {
            final YamlConfiguration yaml = getPlayerYaml(info.player);
            info.checkup();
            String k = info.petUUID.toString();
            if (yaml.getConfigurationSection("pets." + k) != null) {
                throw new RuntimeException("Pet [" + k + "] was defined.");
            }
            yaml.createSection("pets." + k);
            final List<String> list = new ArrayList<>(yaml.getStringList("pet-sort"));
            {
                if (!list.contains(k)) {
                    list.add(k);
                }
            }
            yaml.set("pets-sort", list);
            return Info.super.definePet(info);
        }
    }

    @Override
    public UUID warehouseDefinePet(final @NotNull PetDefiner info) {
        synchronized (info) {
            final YamlConfiguration yaml = getPlayerYaml(info.player);
            info.checkup();
            String k = info.petUUID.toString();
            if (yaml.getConfigurationSection("warehouse." + k) != null) {
                throw new RuntimeException("Pet [" + k + "] was defined.");
            }
            yaml.createSection("warehouse." + k);
            final List<String> list = new ArrayList<>(yaml.getStringList("warehouse-sort"));
            {
                if (!list.contains(k)) {
                    list.add(k);
                }
            }
            yaml.set("warehouse-sort", list);
            return Info.super.warehouseDefinePet(info);
        }
    }

    private static YamlConfiguration loadData(UUID uid) {
        File file = getDataStore(uid);
        if (!file.exists()) return new YamlConfiguration();
        return YamlConfiguration.loadConfiguration(file);
    }

    private static File getDataStore(@NotNull UUID uid) {
        return new File("plugins/imipet/player/", uid + ".yml");
    }

    private static YamlConfiguration getPlayerYaml(UUID uniqueId) {
        try {
            return datas.get(uniqueId);
        } catch (ExecutionException e) {
            throw new RuntimeException("Error in loading data", e);
        }
    }

    // 保存玩家数据
    private static void store(File file, YamlConfiguration yaml) {
        try {
            yaml.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 获得指定玩家背包是否有宠物
    public boolean havePet(@NotNull UUID e) {
        return !getPetsPackList(e).isEmpty();
    }

    // 获得指定玩家仓库是否有宠物
    public boolean haveWarehousePet(@NotNull UUID e) {
        return !getPetsWarehouseList(e).isEmpty();
    }

    // 获得指定玩家的宠物模型ID
    public String getPetModelId(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml(e).getString(type+"." + uuid + ".modelId");
    }

    // 设置玩家宠物的模型ID
    @Override
    public void setPetModelId(@NotNull UUID e, @NotNull String modelId, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e)
                .getConfigurationSection(type).getConfigurationSection(uuid.toString())
                .set("modelId", modelId.toLowerCase());
    }

    // 获得指定玩家的宠物名称
    public String getPetName(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        String val = getPlayerYaml((e)).getString(type+"." + uuid + ".name");
        if (val != null) val = ChatColor.translateAlternateColorCodes('&', val);
        return val;
    }

    // 设置玩家的宠物名称
    @Override
    public void setPetName(@NotNull UUID e, String petName, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e)
                .getConfigurationSection(type)
                .getConfigurationSection(uuid.toString())
                .set("name", petName);

    }

    // 获得指定玩家的宠物经验
    public int getPetNowExp(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml((e)).getInt(type+"." + uuid + ".nowExp");
    }

    // 设置玩家的宠物当前经验
    @Override
    public void setPetNowExp(@NotNull UUID e, int petNowExp, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e)
                .set(type+"." + uuid + ".nowExp", petNowExp);
    }

    // 获得指定玩家的宠物经验上限
    public int getPetMaxExp(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml(e).getInt(type+"." + uuid + ".maxExp");
    }

    // 设置玩家的宠物经验上限
    @Override
    public void setPetMaxExp(@NotNull UUID e, int petMaxExp, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e).getConfigurationSection(type)
                .getConfigurationSection(uuid.toString()).set("maxExp", petMaxExp);
    }

    // 设置指定玩家的宠物AP属性表
    @Override
    public void setPetApAttribute(@NotNull UUID playerUUID, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        getPlayerYaml(playerUUID).getConfigurationSection(type)
                .getConfigurationSection(petUUID.toString()).set("apAttribute", attributeList);
    }

    // 设置指定玩家的宠物AP属性表
    @Override
    public void setPetApAttribute(@NotNull Player player, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        setPetApAttribute(player.getUniqueId(), attributeList, petUUID, type);
    }

    // 获取指定玩家的宠物AP属性表
    public List<String> getPetApAttribute(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        return getPlayerYaml(playerUUID).getStringList(type+"."+petUUID+".apAttribute");
    }

    // 设置指定玩家的宠物BUFF AP属性表
    @Override
    public void setPetBuffAP(@NotNull UUID playerUUID, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        getPlayerYaml(playerUUID).getConfigurationSection(type)
                .getConfigurationSection(petUUID.toString()).set("buff.ap", attributeList);
    }

    // 设置指定玩家的宠物BUFF AP属性表
    @Override
    public void setPetBuffAP(@NotNull Player player, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        setPetBuffAP(player.getUniqueId(), attributeList, petUUID, type);
    }

    // 获取指定玩家的宠物BUFF AP属性表
    public List<String> getPetBuffAP(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        return getPlayerYaml(playerUUID).getStringList(type+"."+petUUID+".buff.ap");
    }

    // 获得指定玩家的宠物等级
    public int getPetLevel(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml((e)).getInt(type+"." + uuid + ".level");
    }

    // 设置玩家的宠物等级
    @Override
    public void setPetLevel(@NotNull UUID e, int petLevel, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e).getConfigurationSection(type).getConfigurationSection(uuid.toString())
                .set("level", petLevel);
    }

    // 获得指定玩家的宠物的跟随与否状态
    public boolean getPetFollow(@NotNull UUID e, @NotNull UUID uuid) {
        return getPlayerYaml(e).getBoolean("pets." + uuid + ".follow");
    }

    // 设置玩家的宠物的跟随与否状态
    @Override
    public void setPetFollow(@NotNull UUID e, boolean follow, @NotNull UUID uuid) {
        getPlayerYaml(e).getConfigurationSection("pets").getConfigurationSection(uuid.toString()).set("follow", follow);
    }

    // 获得指定玩家的宠物当前血量
    public double getPetNowHP(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml(e).getDouble(type+"." + uuid + ".nowHP");
    }

    // 设置玩家的宠物当前血量
    @Override
    public void setPetNowHP(@NotNull UUID e, double petNowHP, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e).getConfigurationSection(type).getConfigurationSection(uuid.toString()).set("nowHP", petNowHP);
    }

    // 获得指定玩家的宠物上限血量
    public double getPetMaxHP(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml(e).getDouble(type+"." + uuid + ".maxHP");
    }

    // 设置玩家的宠物上限血量
    @Override
    public void setPetMaxHP(@NotNull UUID e, double petMaxHP, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e).getConfigurationSection(type).getConfigurationSection(uuid.toString()).set(".maxHP", petMaxHP);
    }

    // 获得指定玩家的宠物当前活力值
    public int getPetNowFood(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml(e).getInt(type+"." + uuid + ".nowFood");
    }

    // 设置玩家的宠物当前活力值
    @Override
    public void setPetNowFood(@NotNull UUID e, int petNowFood, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e).getConfigurationSection(type).getConfigurationSection(uuid.toString()).set("nowFood", petNowFood);
    }

    // 获得指定玩家的宠物上限活力值
    public int getPetMaxFood(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml(e).getInt(type+"." + uuid + ".maxFood");
    }

    // 设置玩家的宠物上限活力值
    @Override
    public void setPetMaxFood(@NotNull UUID e, int petMaxFood, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e).getConfigurationSection(type).getConfigurationSection(uuid.toString()).set(".maxFood", petMaxFood);
    }

    // 获得指定玩家的宠物最低攻击力
    public double getPetMinDamage(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml(e).getDouble(type+"." + uuid + ".minDamage");
    }

    // 设置玩家的宠物最低攻击力
    @Override
    public void setPetMinDamage(@NotNull UUID e, double petMinDamage, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e).getConfigurationSection(type).getConfigurationSection(uuid.toString()).set("minDamage", petMinDamage);
    }

    // 获得指定玩家的宠物最高攻击力
    public double getPetMaxDamage(@NotNull UUID e, @NotNull UUID uuid, @NotNull String type) {
        return getPlayerYaml((e)).getDouble(type+"." + uuid + ".maxDamage");
    }

    // 设置玩家的宠物最高攻击力
    @Override
    public void setPetMaxDamage(@NotNull UUID e, double petMaxDamage, @NotNull UUID uuid, @NotNull String type) {
        getPlayerYaml(e).getConfigurationSection(type).getConfigurationSection(uuid.toString()).set("maxDamage", petMaxDamage);
    }

    // 设置玩家的经验存储盒的经验值值
    @Override
    public void setExpBox(@NotNull UUID playerUUID, int exp) {
        getPlayerYaml(playerUUID).set("expBox", exp);
    }

    // 获得玩家的经验存储盒的经验值值
    public int getExpBox(@NotNull UUID playerUUID) {
        return getPlayerYaml(playerUUID).getInt("expBox");
    }

    // 添加玩家的经验存储盒的经验值值
    @Override
    public void addExpBox(@NotNull UUID playerUUID, int addExp) {
        getPlayerYaml(playerUUID).set("expBox", getExpBox(playerUUID) + addExp);
    }

    // 减少玩家的经验存储盒的经验值值
    @Override
    public void delExpBox(@NotNull UUID playerUUID, int delExp) {
        getPlayerYaml(playerUUID).set("expBox", getExpBox(playerUUID) - delExp);
    }

    // 获得跟随玩家的宠物的UUID
    @NotNull
    public Set<UUID> getFollowingPetUUID(@NotNull UUID e) {
        Set<UUID> followings = new LinkedHashSet<>();
        YamlConfiguration yaml = getPlayerYaml(e);
        if (yaml.contains("pets")) {
            for (String petUUID : yaml.getConfigurationSection("pets").getKeys(false)) {
                if (yaml.getBoolean("pets." + petUUID + ".follow")) {
                    followings.add(UUID.fromString(petUUID));
                }
            }
        }
        return followings;
    }

    // 获得指定宠物名称的宠物的UUID
    /*public UUID getFollowPetUUID(UUID e, String petName) {
        String playerUUID = UUIDMode.uid(e);
        File file = getPlayerFile(playerUUID);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        if (yaml.contains(playerUUID)) {
            if (yaml.contains(playerUUID+".pets")) {
                for (String petUUID : yaml.getConfigurationSection(playerUUID+".pets").getKeys(false)) {
                    if (yaml.getString(playerUUID+".pets."+petUUID+".name").equals(petName)) {
                        return UUID.fromString(petUUID);
                    }
                }
            }
        }
        return null;
    }*/

    // 获得玩家的宠物背包拥有宠物量 上限6个
    public int getPetsPackAmount(@NotNull UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player!=null) {
            if (havePet(player)) {
                return getPetsPackList(playerUUID).size();
            }
        }
        return 0;
    }

    // 集合玩家背包宠物为列表
    @NotNull
    public List<UUID> getPetsPackList(@NotNull UUID playerUUID) {
        final YamlConfiguration yaml = getPlayerYaml((playerUUID));
        List<String> sort = yaml.getStringList("pets-sort");
        if (yaml.contains("pets")) {
            return yaml
                    .getConfigurationSection("pets")
                    .getKeys(false).stream()
                    .sorted(Comparator.comparingInt(w -> {
                        int result = sort.indexOf(w);
                        if (result == -1) return Integer.MAX_VALUE;
                        return result;
                    }))
                    .map(UUID::fromString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // 获得玩家的宠物仓库拥有宠物量
    public int getPetsWarehouseAmount(@NotNull UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player!=null) {
            if (havePet(player)) {
                return getPetsWarehouseList(playerUUID).size();
            }
        }
        return 0;
    }

    // 集合玩家仓库宠物为列表
    @NotNull
    public List<UUID> getPetsWarehouseList(@NotNull UUID playerUUID) {
        final YamlConfiguration yaml = getPlayerYaml((playerUUID));
        List<String> sort = yaml.getStringList("warehouse-sort");
        if (yaml.contains("warehouse")) {
            return yaml
                    .getConfigurationSection("warehouse")
                    .getKeys(false).stream()
                    .sorted(Comparator.comparingInt(w -> {
                        int result = sort.indexOf(w);
                        if (result == -1) return Integer.MAX_VALUE;
                        return result;
                    }))
                    .map(UUID::fromString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // 放生宠物
    public void removePet(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        YamlConfiguration yc = getPlayerYaml(playerUUID);
        yc.set(type+"." + petUUID, null);
        final List<String> list = new ArrayList<>(yc.getStringList(type+"-sort"));
        list.remove(petUUID.toString());
        yc.set(type+"-sort", list);
    }

    // 设置指定玩家的宠物技能表
    @Override
    public void setPetSkills(@NotNull UUID playerUUID, List<String> skillList, @NotNull UUID petUUID, @NotNull String type) {
        getPlayerYaml(playerUUID).getConfigurationSection(type)
                .getConfigurationSection(petUUID.toString()).set("skillList", skillList);
    }

    // 设置指定玩家的宠物技能表
    @Override
    public void setPetSkills(@NotNull Player player, List<String> skillList, @NotNull UUID petUUID, @NotNull String type) {
        setPetSkills(player.getUniqueId(), skillList, petUUID, type);
    }

    // 获取指定玩家的宠物技能表
    public List<String> getPetSkills(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        return getPlayerYaml(playerUUID).getStringList(type+"."+petUUID+".skillList");
    }

    @Override
    public void setPetSkillsUn(@NotNull UUID playerUUID, List<String> skillList, @NotNull UUID petUUID, @NotNull String type) {
        getPlayerYaml(playerUUID).getConfigurationSection(type)
                .getConfigurationSection(petUUID.toString()).set("skillUnList", skillList);
    }

    @Override
    public void setPetSkillsUn(@NotNull Player player, List<String> skillList, @NotNull UUID petUUID, @NotNull String type) {
        setPetSkillsUn(player.getUniqueId(), skillList, petUUID, type);
    }

    public List<String> getPetSkillsUn(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        return getPlayerYaml(playerUUID).getStringList(type+"."+petUUID+".skillUnList");
    }
}