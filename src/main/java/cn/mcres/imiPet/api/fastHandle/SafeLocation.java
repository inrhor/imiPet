package cn.mcres.imiPet.api.fastHandle;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class SafeLocation {
    /**
     *
     * @param location 位置
     * @return 检查是否是安全的位置
     */
    public static boolean isSafeLocation(Location location) {
        Block feetBlock = location.getBlock();
        if (!location.getBlock().getType().isTransparent() && !feetBlock.getLocation().add(0, 0, 0).getBlock().getType().isTransparent()) {
            return false; // 窒息
        }
        if (!feetBlock.getRelative(BlockFace.UP).getType().isTransparent()) {
            return false; // 窒息
        }
        return feetBlock.getRelative(BlockFace.DOWN).getType().isSolid(); // 是否非下落
    }
}
