package cn.mcres.imiPet.build.utils.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Method;

public class EntityCreator {

    /**
     *
     * @param entityType The type of entity that you want to create
     * @param location The location where you want the entity.
     * @return Entity
     */
    public static Entity create(EntityType entityType, Location location) {
        try {
            Class<?> craftWorldClass = getNMSClass("org.bukkit.craftbukkit.", "CraftWorld");

            Object craftWorldObject = craftWorldClass.cast(location.getWorld());

            Method createEntityMethod = craftWorldObject.getClass().getMethod("createEntity", Location.class, Class.class);

            Object entity = createEntityMethod.invoke(craftWorldObject, location, entityType.getEntityClass());

            return (Entity) entity.getClass().getMethod("getBukkitEntity").invoke(entity);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param prefix What comes before the version number
     * @param nmsClassString What comes after the version number
     * @return Class The class that you tried to access
     * @throws ClassNotFoundException throws an exception if the class it not found
     */
    private static Class<?> getNMSClass(String prefix, String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = prefix + version + nmsClassString;
        return Class.forName(name);
    }
}
