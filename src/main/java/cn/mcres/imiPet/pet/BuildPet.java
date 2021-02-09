package cn.mcres.imiPet.pet;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.attributePlus.AttributeManager;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.other.ReplaceAll;
import cn.mcres.imiPet.entity.SpawnEntity;
import cn.mcres.imiPet.instal.lib.APLib;
import cn.mcres.imiPet.instal.lib.CMILib;
import cn.mcres.imiPet.instal.lib.HDLib;
import cn.mcres.imiPet.instal.lib.TrHDLib;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.server.ServerInfo;
import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class BuildPet {
    public static ServerInfo getServerInfo() {
        return ImiPet.loader.getServerInfo();
    }

    private Player player;
    private LivingEntity entity;
    private String modelId;
    private String petName;
    private UUID petUUID;
    private double height;
    private double x;
    private double z;
    private boolean enableShow;
    private String useShow = "";
    private Hologram hdHologram;
    private me.arasple.mc.trhologram.hologram.Hologram trHologram;
    private CMIHologram cmiHologram;
    private List<String> showPetName;
    private ModelInfoManager infoManager;
    private ModelEntityManager modelEntityManager;
    private Map<Integer, List<UUID>> buffAPPlayerList = new HashMap<>();
    private Map<String, Integer> skillCoolDown = new HashMap<>();

    public BuildPet(Player e, String modelId, String petName) {
        this.player = e;
        this.modelId = modelId;
        this.petName = petName;
        this.infoManager = ModelInfoManager.petModelList.get(modelId);
    }

    public Map<String, Integer> getSkillCoolDown() {
        return skillCoolDown;
    }

    public void setSkillCoolDown(Map<String, Integer> skillCoolDown) {
        this.skillCoolDown = skillCoolDown;
    }

    public void setPetUUID(UUID petUUID) {
        this.petUUID = petUUID;
    }

    public double getHeight() {
        return this.height;
    }

    public double getX() {
        return this.x;
    }

    public double getZ() {
        return this.z;
    }

    public UUID getPetUUID() {
        return petUUID;
    }

    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public Player getPlayer() {
        return this.player;
    }

    public LivingEntity getPet() {
        return (LivingEntity) this.entity;
    }

    public String getPetName() {
        return this.petName;
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isEnableShow() {
        return this.enableShow;
    }

    public String getUseShow() {
        return this.useShow;
    }

    public Hologram getHDHologram() {
        return this.hdHologram;
    }

    public me.arasple.mc.trhologram.hologram.Hologram getTrHologram() {
        return this.trHologram;
    }

    public CMIHologram getCmiHologram() {
        return cmiHologram;
    }

    public List<String> getShowPetName() {
        return this.showPetName;
    }

    public void setShowPetName(List<String> showPetName) {
        this.showPetName = showPetName;
    }

    /**
     * 生成自定义Cem模型宠物
     */
    /*public void createCemPet() {
        UUID petUUID = getPetUUID();
        if (petUUID == null) {
            throw new IllegalArgumentException("ERROR: No PetUUID Set!");
        }
        if (info().getPetFollow(player, petUUID)) {
            String entityType = GetPetsYaml.getString(this.modelId, "entityType");
            this.entity = SpawnEntity.followCemPet(player, getLocation(this.player), entityType);
            if (GetPetsYaml.getBoolean(this.modelId, "match.name.enable")) {
                entity.setCustomName(GetPetsYaml.getString(this.modelId, "match.name.string"));
            }
            fastSetPetInfo(entity);
        }
    }*/

    /**createPet
     * 生成自定义模型宠物，非Cem
     */
    public void createPet() {
        UUID petUUID = getPetUUID();
        if (petUUID == null) {
            throw new IllegalArgumentException("ERROR: No PetUUID Set!");
        }
        if (info().getPetFollow(player, petUUID)) {
            this.entity = SpawnEntity.followPet(player, getLocation(this.player));
            Wolf pet = (Wolf) entity;
            pet.setOwner(this.player);

            if (ModelInfoManager.petModelList.get(modelId) == null) return;
            infoManager = ModelInfoManager.petModelList.get(modelId);
            modelEntityManager = new ModelEntityManager(pet, this.modelId, this);

            boolean useModelEngine = infoManager.isUseModelEngine();
            modelEntityManager.setUseModelEngine(useModelEngine);

            if (!useModelEngine) {
                modelEntityManager.setItemStack(infoManager.getAnimationMaterialIdle(),
                        infoManager.getAnimationMaterialWalk(),
                        infoManager.getAnimationMaterialWalk());
                modelEntityManager.setAnimationItemNameIdle(infoManager.getAnimationItemNameIdle());
                modelEntityManager.setAnimationItemNameWalk(infoManager.getAnimationItemNameWalk());
                modelEntityManager.setAnimationItemNameAttack(infoManager.getAnimationItemNameAttack());
                modelEntityManager.setAnimationCustomModelDataIdle(infoManager.getAnimationCustomModelDataIdle());
                modelEntityManager.setAnimationCustomModelDataWalk(infoManager.getAnimationCustomModelDataWalk());
                modelEntityManager.setAnimationCustomModelDataAttack(infoManager.getAnimationCustomModelDataAttack());
                modelEntityManager.setModelLocationH(infoManager.getModelLocationH());
            }

            modelEntityManager.setAnimationModelList(infoManager.getAnimationModelList());

            modelEntityManager.spawnModel(useModelEngine, true);

            JavaPlugin plugin = ImiPet.loader.getPlugin();

            // 作为标识符
            pet.setMetadata(this.player.getName(), new FixedMetadataValue(plugin, true));
            pet.setMetadata("imipet.uuid", new FixedMetadataValue(plugin, petUUID));
            pet.setMetadata("imipet:playerUUID", new FixedMetadataValue(plugin, this.player.getUniqueId()));
            pet.setMetadata("imiPet", new FixedMetadataValue(plugin, true));
            pet.setMetadata("imiPet.attack", new FixedMetadataValue(plugin, false));
            /*int attackTime = GetPetsYaml.getInt(this.modelId, "animationTime.attackTime");
            if (attackTime <= 0) {
                attackTime = 1;
            }*/
//            pet.setMetadata("imiPet.attackTime", new FixedMetadataValue(ImiPet.loader.getPlugin(), 5));
            pet.setMetadata("imipet:modelId", new FixedMetadataValue(plugin, this.modelId));
            pet.setCustomName(ReplaceAll.petNameReplaceAll(infoManager.getPetDefaultNameForMat(), this.player, petUUID));
            if (APLib.APLibEnable) {
                if (infoManager.isApEnable()) {
                    AttributeManager.entitySet(pet, info().getPetApAttribute(player.getUniqueId(), petUUID, "pets"));
                }
            }
            fastSetPetInfo(pet);
        }
    }

    private void fastSetPetInfo(LivingEntity pet) {
        pet.setCustomNameVisible(false);
        // 静音
        if (!getServerInfo().isOldVersionMethod()) {
            pet.setSilent(true);
        }
        // 设置宠物血量
        pet.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(info().getPetMaxHP(this.player, petUUID, "pets"));
        pet.setHealth(info().getPetNowHP(this.player, petUUID, "pets"));

        JavaPlugin plugin = ImiPet.INSTANCE.getPlugin();

        if (/*GetPetsYaml.getBoolean(modelId, "basis.display.show")*/infoManager.isShowName()) {
            String useShow = /*GetPetsYaml.getString(modelId, "basis.display.useShow")*/infoManager.getShowNameLib();
            if (useShow == null) {
                useShow = "hd";
            }
            this.height = infoManager.getShowNameH();
            this.x = infoManager.getShowNameX();
            this.z = infoManager.getShowNameZ();
            this.showPetName = infoManager.getShowNameFormatList();
            switch (useShow) {
                case "tr":
                    if (TrHDLib.TrHDLibEnable) {
                        this.trHologram = me.arasple.mc.trhologram.hologram.Hologram.Companion.createHologram(
                                plugin,
                                String.valueOf(petUUID),
                                pet.getLocation(),
                                Collections.emptyList());
                        this.useShow = "tr";
                        this.enableShow = true;
                    }
                    break;
                case "hd":
                    if (HDLib.HDLibEnable) {
                        this.hdHologram = HologramsAPI.createHologram(plugin, pet.getLocation());
                        this.useShow = "hd";
                        this.enableShow = true;
                    }
                    break;
                case "cmi":
                    if (CMILib.CMILibEnable) {
                        this.cmiHologram = new CMIHologram(String.valueOf(petUUID), pet.getLocation());
                        CMI.getInstance().getHologramManager().addHologram(this.cmiHologram);
                        this.cmiHologram.update();
                        this.useShow = "cmi";
                        this.enableShow = true;
                    }
                    break;
            }
        }
    }

    public void removePet() {
        if (this.entity != null) {
            this.entity.remove();
            this.entity = null;
        }
    }

    public static Location getLocation(Player e) {
        Location loc = e.getLocation();
        loc.setPitch(0.0F);
        loc.setYaw(loc.getYaw() + 100.0F);
        loc.add(loc.getDirection().normalize().multiply(2D));
        loc.setYaw(e.getLocation().getYaw());
        loc.setPitch(e.getLocation().getPitch());
        return loc;
    }

    public void setBuffAPPlayerList(List<UUID> buffPlayerList, int i) {
        this.buffAPPlayerList.put(i , buffPlayerList);
    }

    public List<UUID> getBuffAPPlayerList(int i) {
        return buffAPPlayerList.get(i);
    }

    public ModelEntityManager getModelEntityManager() {
        return modelEntityManager;
    }

    public ModelInfoManager getModelInfoManager() {
        return infoManager;
    }

    public void removeBuffAPMap() {
        buffAPPlayerList.clear();
    }

    public Set<Integer> getAllBuffAPMap() {
        return buffAPPlayerList.keySet();
    }


}
