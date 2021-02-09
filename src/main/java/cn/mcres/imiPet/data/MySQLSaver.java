package cn.mcres.imiPet.data;

import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.build.utils.StringListChange;
import cn.mcres.imiPet.data.sql.ConnectionPool;
import cn.mcres.imiPet.data.sql.SQLConfig;
import cn.mcres.karlatemp.mxlib.nbt.NBTCompressedStreamTools;
import cn.mcres.karlatemp.mxlib.nbt.NBTReadLimiter;
import cn.mcres.karlatemp.mxlib.nbt.NBTTagCompound;

import java.util.Objects;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Create at 2019/11/17 10:36
 * Copyright Karlatemp
 * imiPet $ cn.mcres.imiPet.data
 */
public class MySQLSaver implements Info {
    private final ConnectionPool pool;
    private final SQLConfig config;

    private static class SQLKey {
        int type;
        String uniKey;

        SQLKey(int a, String b) {
            type = a;
            uniKey = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SQLKey sqlKey = (SQLKey) o;
            return type == sqlKey.type &&
                    Objects.equals(uniKey, sqlKey.uniKey);
        }

        @Override
        public int hashCode() {
            return type ^ uniKey.hashCode();
        }
    }

    private final LoadingCache<SQLKey, NBTTagCompound> cached;

    public MySQLSaver(SQLConfig config) {
        this.pool = new ConnectionPool(config.min, config.url, config.usr, config.pwd);
        this.config = config;
        pool.post(connection -> {
            return connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS " + config.table_name + " (" +
                            "  `type` INT NOT NULL," +
                            "  `unikey` VARCHAR(45) NOT NULL," +
                            "  `data` BLOB NOT NULL," +
                            "  PRIMARY KEY (`type`, `unikey`));");
        });
        cached = CacheBuilder.newBuilder()
                .expireAfterWrite(config.cache_duration, config.cache_time_unit)
                .build(new CacheLoader<SQLKey, NBTTagCompound>() {
                    @Override
                    public NBTTagCompound load(@NotNull SQLKey sqlKey) throws Exception {
                        return pool.post(connection -> {
                            try (PreparedStatement p = connection.prepareStatement(
                                    "select `data` FROM " + config.table_name + " where `type` = " + sqlKey.type + " and `unikey` = ? "
                            )) {
                                p.setString(1, sqlKey.uniKey);
                                final ResultSet query = p.executeQuery();
                                if (query.next()) {
                                    final Blob blob = query.getBlob(1);
                                    try (DataInputStream data = new DataInputStream(blob.getBinaryStream())) {
                                        return NBTCompressedStreamTools.loadCompound(
                                                data, NBTReadLimiter.UN_LIMITED
                                        );
                                    }
                                }
                            }
                            return new NBTTagCompound();
                        });
                    }
                });
    }

    private NBTTagCompound get(int type, String unikey) {
        try {
            return cached.get(new SQLKey(type, unikey));
        } catch (ExecutionException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new RuntimeException(cause);
        }
    }

    private void store(int type, String unikey, NBTTagCompound compound) {
        pool.post(connection -> {
            boolean upd;
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT `unikey` FROM " + config.table_name + " WHERE `type` = " + type + " AND `unikey`=?"
            )) {
                ps.setString(1, unikey);
                final ResultSet query = ps.executeQuery();
                upd = query.next();
            }
            final Blob blob = connection.createBlob();
            try (OutputStream out = blob.setBinaryStream(1)) {
                NBTCompressedStreamTools.write(compound, new DataOutputStream(out));
            }
            if (upd) try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE " + config.table_name + " SET `data` = ? WHERE (`type` = " + type + " AND `unikey` = ?)"
            )) {
                ps.setBlob(1, blob);
                ps.setString(2, unikey);
                ps.executeUpdate();
            }
            else try (PreparedStatement p = connection.prepareStatement(
                    "INSERT INTO " + this.config.table_name +
                            " (`type`, `unikey`, `data`) VALUES (" + type + ", ?, ?);"
            )) {
                p.setString(1, unikey);
                p.setBlob(2, blob);
                p.execute();
            }
            cached.put(new SQLKey(type, unikey), compound);
            return null;
        });
    }

    @Override
    public boolean havePet(@NotNull UUID player) {
        final NBTTagCompound compound = get(0, String.valueOf(player));
        if (compound.hasKey("pets")) {
            return !compound.getCompound("pets").isEmpty();
        }
        return false;
    }

    @Override
    public boolean haveWarehousePet(@NotNull UUID player) {
        final NBTTagCompound compound = get(0, String.valueOf(player));
        if (compound.hasKey("warehouse")) {
            return !compound.getCompound("warehouse").isEmpty();
        }
        return false;
    }

    public static String UUIDKey(UUID uuid) {
        return Long.toHexString(uuid.getMostSignificantBits()) + "_" + Long.toHexString(uuid.getLeastSignificantBits());
    }

    public static long parseLong(String hex) {
        return hex.chars().map(ww -> {
            if (ww >= '0' && ww <= '9') {
                return ww - '0';
            }
            if (ww >= 'a' && ww <= 'f') {
                return ww - 'a' + 0xa;
            }
            if (ww >= 'A' && ww <= 'F') {
                return ww - 'A' + 0xa;
            }
            return 0;
        }).collect(AtomicLong::new, (a, b) -> a.set((a.get() << 4) | (((long) b) & 0xF)), (a, b) -> {
            throw new RuntimeException("Combiner should not used in parseLong(" + hex + ")");
        }).get();
    }

    public static UUID parseUUIDKey(String key) {
        int dp = key.indexOf('_');
        if (dp > 0) {
            String most = key.substring(0, dp);
            String least = key.substring(dp + 1);
            return new UUID(parseLong(most), parseLong(least));
        }
        return null;
    }

    @Override
    public String getPetModelId(@NotNull UUID player, @NotNull UUID uuid, String type) {
        final NBTTagCompound compound = get(0, String.valueOf(player));
        if (compound.hasKey(type)) {
            String key = UUIDKey(uuid);
            final NBTTagCompound pets = compound.getCompound(type);
            if (pets.hasKey(key)) {
                return pets.getCompound(key).getString("model");
            }
        }
        return null;
    }

    private static NBTTagCompound getOrCreate(NBTTagCompound parent, String sub) {
        if (parent.hasKeyOfType(sub, 10))
            return parent.getCompound(sub);
        NBTTagCompound created = new NBTTagCompound();
        parent.set(sub, created);
        return created;
    }

    @Override
    public void setPetModelId(@NotNull UUID player, @NotNull String modelId, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setString("model", modelId);
        store(0, String.valueOf(player), root);
    }

    @Override
    public String getPetName(@NotNull UUID player, @NotNull UUID uuid, String type) {
        final NBTTagCompound compound = get(0, String.valueOf(player));
        if (compound.hasKey(type)) {
            String key = UUIDKey(uuid);
            final NBTTagCompound pets = compound.getCompound(type);
            if (pets.hasKey(key)) {
                return pets.getCompound(key).getString("name");
            }
        }
        return null;
    }

    @Override
    public void setPetName(@NotNull UUID player, String petName, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setString("name", petName);
        store(0, String.valueOf(player), root);
    }

    @Override
    public int getPetNowExp(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getInt("exp");
    }

    @Override
    public void setPetNowExp(@NotNull UUID player, int petNowExp, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setInt("exp", petNowExp);
        store(0, String.valueOf(player), root);
    }

    @Override
    public int getPetMaxExp(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getInt("m_exp");
    }

    @Override
    public void setPetMaxExp(@NotNull UUID player, int petMaxExp, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setInt("m_exp", petMaxExp);
        store(0, String.valueOf(player), root);
    }

    @Override
    public int getPetLevel(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getInt("lv");
    }

    @Override
    public void setPetLevel(@NotNull UUID player, int petLevel, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setInt("lv", petLevel);
        store(0, String.valueOf(player), root);
    }

    @Override
    public boolean getPetFollow(@NotNull UUID player, @NotNull UUID uuid) {
        return get(0, String.valueOf(player))
                .getCompound("pets")
                .getCompound(UUIDKey(uuid))
                .getBoolean("follow");
    }

    @Override
    public void setPetFollow(@NotNull UUID player, boolean follow, @NotNull UUID uuid) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, "pets"), UUIDKey(uuid)).setBoolean("follow", follow);
        store(0, String.valueOf(player), root);
    }

    @Override
    public double getPetNowHP(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getDouble("health");
    }

    @Override
    public void setPetNowHP(@NotNull UUID player, double petNowHP, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setDouble("health", petNowHP);
        store(0, String.valueOf(player), root);
    }

    @Override
    public double getPetMaxHP(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getDouble("max_health");
    }

    @Override
    public void setPetMaxHP(@NotNull UUID player, double petMaxHP, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setDouble("max_health", petMaxHP);
        store(0, String.valueOf(player), root);
    }

    @Override
    public int getPetNowFood(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getInt("food");
    }

    @Override
    public void setPetNowFood(@NotNull UUID player, int petNowFood, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setInt("food", petNowFood);
        store(0, String.valueOf(player), root);
    }

    @Override
    public int getPetMaxFood(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getInt("m_food");
    }

    @Override
    public void setPetMaxFood(@NotNull UUID player, int petMaxFood, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setInt("m_food", petMaxFood);
        store(0, String.valueOf(player), root);
    }

    @Override
    public double getPetMinDamage(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getInt("min_damage");
    }

    @Override
    public void setPetMinDamage(@NotNull UUID player, double petMinDamage, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setDouble("min_damage", petMinDamage);
        store(0, String.valueOf(player), root);
    }

    @Override
    public double getPetMaxDamage(@NotNull UUID player, @NotNull UUID uuid, String type) {
        return get(0, String.valueOf(player))
                .getCompound(type)
                .getCompound(UUIDKey(uuid))
                .getInt("max_damage");
    }

    @Override
    public void setPetMaxDamage(@NotNull UUID player, double petMaxDamage, @NotNull UUID uuid, String type) {
        final NBTTagCompound root = get(0, String.valueOf(player));
        getOrCreate(getOrCreate(root, type), UUIDKey(uuid)).setDouble("max_damage", petMaxDamage);
        store(0, String.valueOf(player), root);
    }

    @Override
    public void setExpBox(@NotNull UUID playerUUID, int exp) {
        final NBTTagCompound root = get(0, String.valueOf(playerUUID));
        root.setInt("exp_box", exp);
        store(0, String.valueOf(playerUUID), root);
    }

    @Override
    public int getExpBox(@NotNull UUID playerUUID) {
        return get(0, String.valueOf(playerUUID))
                .getInt("exp_box");
    }

    @Override
    public void addExpBox(@NotNull UUID playerUUID, int addExp) {
        final NBTTagCompound root = get(0, String.valueOf(playerUUID));
        root.setInt("exp_box", root.getInt("exp_box") + addExp);
        store(0, String.valueOf(playerUUID), root);
    }

    @Override
    public void delExpBox(@NotNull UUID playerUUID, int delExp) {
        final NBTTagCompound root = get(0, String.valueOf(playerUUID));
        root.setInt("exp_box", root.getInt("exp_box") - delExp);
        store(0, String.valueOf(playerUUID), root);
    }

    @NotNull
    @Override
    public Set<UUID> getFollowingPetUUID(@NotNull UUID player) {
        NBTTagCompound pets = get(0, String.valueOf(player))
                .getCompound("pets");
        Set<UUID> result = new HashSet<>();
        for (String key : pets.getKeys()) {
            final NBTTagCompound compound = pets.getCompound(key);
            if (compound.getBoolean("follow")) {
                result.add(parseUUIDKey(key));
            }
        }
        return result;
    }

    @Override
    public int getPetsPackAmount(@NotNull UUID playerUUID) {
        return get(0, String.valueOf(playerUUID))
                .getCompound("pets").getKeys().size();
    }

    @NotNull
    @Override
    public List<UUID> getPetsPackList(@NotNull UUID playerUUID) {
        return get(0, String.valueOf(playerUUID)).getCompound("pets").getKeys().stream().map(
                MySQLSaver::parseUUIDKey
        ).collect(Collectors.toList());
    }

    @Override
    public int getPetsWarehouseAmount(@NotNull UUID playerUUID) {
        return get(0, String.valueOf(playerUUID))
                .getCompound("warehouse").getKeys().size();
    }

    @NotNull
    @Override
    public List<UUID> getPetsWarehouseList(@NotNull UUID playerUUID) {
        return get(0, String.valueOf(playerUUID)).getCompound("warehouse").getKeys().stream().map(
                MySQLSaver::parseUUIDKey
        ).collect(Collectors.toList());
    }

    @Override
    public void removePet(@NotNull UUID playerUUID, @NotNull UUID petUUID, String type) {
        final NBTTagCompound nbt = get(0, String.valueOf(playerUUID));
        nbt.getCompound("pets").remove(UUIDKey(petUUID));
        store(0, String.valueOf(playerUUID), nbt);
    }

    @Override
    public void setPetApAttribute(@NotNull UUID playerUUID, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        final NBTTagCompound root = get(0, String.valueOf(playerUUID));
        getOrCreate(getOrCreate(root, type), UUIDKey(petUUID)).setStringArray("apAttribute", attributeList);
        store(0, String.valueOf(playerUUID), root);
    }

    @Override
    public void setPetApAttribute(@NotNull Player player, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        setPetApAttribute(player.getUniqueId(), attributeList, petUUID, type);
    }

    @Override
    public List<String> getPetApAttribute(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        String str =  get(0, String.valueOf(playerUUID))
                .getCompound(type)
                .getCompound(UUIDKey(petUUID))
                .getString("apAttribute");
        return StringListChange.stringToList(str);
    }

    @Override
    public void setPetBuffAP(@NotNull UUID playerUUID, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        final NBTTagCompound root = get(0, String.valueOf(playerUUID));
        getOrCreate(getOrCreate(root, type), UUIDKey(petUUID)).setStringArray("buffAP", attributeList);
        store(0, String.valueOf(playerUUID), root);
    }

    @Override
    public void setPetBuffAP(@NotNull Player player, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        setPetBuffAP(player.getUniqueId(), attributeList, petUUID, type);
    }

    @Override
    public List<String> getPetBuffAP(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        String str =  get(0, String.valueOf(playerUUID))
                .getCompound(type)
                .getCompound(UUIDKey(petUUID))
                .getString("buffAP");
        return StringListChange.stringToList(str);
    }

    @Override
    public void setPetSkills(@NotNull UUID playerUUID, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        final NBTTagCompound root = get(0, String.valueOf(playerUUID));
        getOrCreate(getOrCreate(root, type), UUIDKey(petUUID)).setStringArray("skillList", attributeList);
        store(0, String.valueOf(playerUUID), root);
    }

    @Override
    public void setPetSkills(@NotNull Player player, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        setPetSkills(player.getUniqueId(), attributeList, petUUID, type);
    }

    @Override
    public List<String> getPetSkills(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        String str =  get(0, String.valueOf(playerUUID))
                .getCompound(type)
                .getCompound(UUIDKey(petUUID))
                .getString("skillList");
        return StringListChange.stringToList(str);
    }

    @Override
    public void setPetSkillsUn(@NotNull UUID playerUUID, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        final NBTTagCompound root = get(0, String.valueOf(playerUUID));
        getOrCreate(getOrCreate(root, type), UUIDKey(petUUID)).setStringArray("skillUnList", attributeList);
        store(0, String.valueOf(playerUUID), root);
    }

    @Override
    public void setPetSkillsUn(@NotNull Player player, List<String> attributeList, @NotNull UUID petUUID, @NotNull String type) {
        setPetSkillsUn(player.getUniqueId(), attributeList, petUUID, type);
    }

    @Override
    public List<String> getPetSkillsUn(@NotNull UUID playerUUID, @NotNull UUID petUUID, @NotNull String type) {
        String str =  get(0, String.valueOf(playerUUID))
                .getCompound(type)
                .getCompound(UUIDKey(petUUID))
                .getString("skillUnList");
        return StringListChange.stringToList(str);
    }
}
