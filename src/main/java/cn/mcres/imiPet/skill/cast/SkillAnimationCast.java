package cn.mcres.imiPet.skill.cast;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.pet.BuildPet;
import cn.mcres.imiPet.skill.SkillAnimationCreator;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SkillAnimationCast {
    public static JavaPlugin getPlugin() {
        return ImiPet.loader.getPlugin();
    }

    private ArmorStand mainStand;
    private Entity target;
    private SkillAnimationCreator animationCreator;

    public SkillAnimationCast(ArmorStand mainStand/*, SkillAnimationCreator animationCreator*/) {
        this.mainStand = mainStand;
//        this.animationID = animationID;
    }

    public SkillAnimationCreator getAnimationCreator() {
        return animationCreator;
    }

    public void setAnimationCreator(SkillAnimationCreator animationCreator) {
        this.animationCreator = animationCreator;
    }

    /*public String getAnimationID() {
        return animationID;
    }*/

    public ArmorStand getMainStand() {
        return mainStand;
    }

    public void setMainStand(ArmorStand mainStand) {
        this.mainStand = mainStand;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public String getCastLocation() {
        return animationCreator.getCastLocationItem();
    }

    public void runScript(ArmorStand mainStand) {
        String[] scriptList = getScript().split(":");
        String type = scriptList[0];
//        this.target = null;
        // bug
        if (type.equals("animation")) {
            if (scriptList[1].equals("but_autoAim")) {
                boolean nod = Boolean.parseBoolean(scriptList[2]);
                String targetType = scriptList[3];
                double range = Double.parseDouble(scriptList[4]);
                // entityMetadata-xxx,xxxx,xxxxx   判断目标实体元数据，支持多个
                // mobs   一切敌对生物  !暂时不做!
                // animal   一切动物  !暂时不做!
                // allNoPlayer   一切生物，但没有玩家
                // allAndPlayer   一切生物包括其它玩家，除了释放者主人
                // all   一切实体
                checkTarget(mainStand, targetType, range, nod);
            }
        }
    }

    public void checkTarget(ArmorStand mainStand, String targetType, double range, boolean nod) {
        if (targetType.startsWith("entityMetadata")) {
            List<String> metadataList =
                    Arrays.stream(targetType.substring(targetType.indexOf("-"))
                            .split(",")).map(String::trim).collect(Collectors.toList());
            for (Entity target : mainStand.getNearbyEntities(range, range, range)) {
                if (!entityExistMeta(target, metadataList) || target instanceof ArmorStand) continue;
                // 开始瞄准目标
//                this.target = target;
                // bug
                setTarget(target);
                tpModel(mainStand, target, nod);
                return;
            }
        }else if (targetType.contains("allNoPlayer")) {
            for (Entity target : mainStand.getNearbyEntities(range, range, range)) {
                if (target instanceof Player || target instanceof ArmorStand) continue;
//                this.target = target;
                setTarget(target);
                tpModel(mainStand, target, nod);
                return;
            }
        }else if (targetType.contains("all")) {
            for (Entity target : mainStand.getNearbyEntities(range, range, range)) {
//                this.target = target;
                if (target instanceof ArmorStand) continue;
                setTarget(target);
                tpModel(mainStand, target, nod);
                return;
            }
        }else if (targetType.contains("allAndPlayer")) {
            if (!mainStand.hasMetadata("imipet.pet")) return;
            BuildPet buildPet = (BuildPet) mainStand.getMetadata("imipet.pet").get(0).value();
            if (buildPet == null) return;

            UUID ownerUUID = buildPet.getPlayer().getUniqueId();
            for (Entity target : mainStand.getNearbyEntities(range, range, range)) {
                if (target instanceof ArmorStand) continue;
                if (target instanceof Player) {
                    if (ownerUUID.equals(target.getUniqueId())) continue;
                }
//                this.target = target;
                setTarget(target);
                tpModel(mainStand, target, nod);
                return;
            }
        }
    }

    /*public void runBuffScript(ArmorStand subStand) {
        String[] scriptList = this.buffScript.split(":");
        String type = scriptList[0];
        int time = Integer.parseInt(scriptList[1])*20;
        if (type.equals("shoot")) {
            String targetType = scriptList[3];
            // 射击目标
            // targeted   已瞄准的目标
            // 其它同 runScript
            if (targetType.equals("targeted")) {
                if (this.target == null) return;
                System.out.println("eee  "+this.target);
                String keepLoc = scriptList[4];
                tpModel(subStand, this.target, true, true);
            }
        }
    }*/

    private BukkitRunnable runMain;

    public void taskMainCancel() {
        if (runMain != null) {
            runMain.cancel();
        }
    }

    public BukkitRunnable getRunMain() {
        return runMain;
    }

    // 设置盔甲架看向目标
    public void tpModel(ArmorStand mainStand, Entity target, boolean nod) {
        taskMainCancel();
        runMain = new BukkitRunnable() {
            public void run() {
                if (mainStand.isDead() || target.isDead()) {
                    SkillManager.remove(mainStand);
                    cancel();
                    return;
                }
                if (target != getTarget()) {
                    cancel();
                    return;
                }
                Vector direction = getVector(mainStand).subtract(getVector(target)).normalize();
                double x = direction.getX();
                double y = direction.getY();
                double z = direction.getZ();
                Location changed = mainStand.getLocation().clone();
                changed.setYaw(180 - toDegree(Math.atan2(x, z)));
                changed.setPitch(90 - toDegree(Math.acos(y)));
                mainStand.teleport(changed);
                if (nod) {
                    EulerAngle head = new EulerAngle(Math.toRadians(changed.getPitch()), 0, 0);
                    mainStand.setHeadPose(head);
                }
            }
        };
        runMain.runTaskTimer(getPlugin(), 0, 5);
    }

    // 盔甲架看向目标
    private float toDegree(double angle) {
        return (float) Math.toDegrees(angle);
    }

    // 盔甲架看向目标
    private Vector getVector(Entity entity) {
        if (entity instanceof Player)
            return ((Player) entity).getEyeLocation().toVector();
        else
            return entity.getLocation().toVector();
    }

    private boolean entityExistMeta(Entity target, List<String> metadataList) {
        for (String meta : metadataList) {
            if (target.hasMetadata(meta)) return true;
        }
        return false;
    }

    public String getScript() {
        return animationCreator.getScriptItem();
    }

    public String getCondition() {
        return animationCreator.getConditionItem();
    }



    public List<String> getBuffScript() {
        return animationCreator.getBuffScriptItem();
    }
}
