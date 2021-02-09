package cn.mcres.imiPet.build.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocTool {
    /**
     * 固定跟踪
     *
     * 偏移量 offset
     *   -90 实体的左边
     *   90  实体的右边
     *   180 实体的后面
     *   想要在前面，直接负数乘法完事了，因为-180也是在后面
     *
     * @param entityLoc
     * @param offset
     * @param multiply 乘 越大越远之类
     * @param height
     * @return
     */
    public static Location getFixedLoc(Location entityLoc, float offset, double multiply, double height) {
        // 复制实体位置
        final Location fixedLoc = entityLoc.clone();
        fixedLoc.setYaw(entityLoc.getYaw()+offset);
        final Vector vectorAdd = fixedLoc.getDirection().normalize().multiply(multiply);
        final Location fixed = fixedLoc.add(vectorAdd);
        fixed.add(0, height, 0);
        return fixed;
    }

    public static float getOffset(String direType) {
        switch (direType.toLowerCase()) {
            case "left":
                return -90;
            case "right":
                return 90;
            case "behind":
                return 180;
        }
        return 0;
    }

    public static Location getTpLoc(String[] castLocList, Location entityLoc) {
        String direType = castLocList[1];
        double multiply = Double.parseDouble(castLocList[2]);
        double height = Double.parseDouble(castLocList[3]);
        entityLoc = LocTool.getFixedLoc(entityLoc, LocTool.getOffset(direType), multiply, height);
        return entityLoc;
    }
}
