package cn.mcres.imiPet.build.utils;

import cn.inrhor.imipet.ImiPet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogUtil {
    // 日志
    public static void send(String println) {
        logger.info(ChatColor.translateAlternateColorCodes('&', println));
    }

    public static void send(List<String> printlnList) {
        for (String println : printlnList) {
            logger.info(ChatColor.translateAlternateColorCodes('&', println));
        }
    }

    private static boolean initialized;
    private static Logger logger;

    public static synchronized void load(ImiPet imiPet) {
        if (initialized) return;
        final Logger logger = imiPet.getPlugin().getLogger();
        LogUtil.logger = logger;
        if (logger.getClass() == PluginLogger.class) { // Plugin Logger override by MXBukkitLib.
            try {
                final Field field = JavaPlugin.class.getDeclaredField("logger");
                field.setAccessible(true);
                Logger pt = logger.getParent();
                SimpleFormatter formatter = new SimpleFormatter();
                field.set(imiPet, LogUtil.logger = new PluginLogger(imiPet.getPlugin()) {
                    @Override
                    public void log(@NotNull LogRecord logRecord) {
                        if (!isLoggable(logRecord.getLevel())) return;
                        String message = logRecord.getMessage();
                        Throwable t = logRecord.getThrown();
                        logRecord.setThrown(null);
                        if (message != null) {
                            message = formatter.formatMessage(logRecord);
                            post(message);
                        }
                        if (t != null) {
                            if (logRecord.getMessage() == null) {
                                logRecord.setMessage("[ImiPet] Plugin Exception.");
                                logRecord.setParameters(new Object[0]);
                            } else {
                                logRecord.setMessage(null);
                            }
                            pt.log(logRecord);
                        }
                    }

                    private void post(String message) {
                        int i = 0;
                        final ConsoleCommandSender console = Bukkit.getConsoleSender();
                        do {
                            int x = message.indexOf('\n', i);
                            if (x == -1) {
                                console.sendMessage("[ImiPet] " + message.substring(i));
                                return;
                            }
                            console.sendMessage("[ImiPet] " + message.substring(i, x));
                            i = x + 1;
                        } while (true);
                    }
                });
            } catch (Throwable e) {
                logger.log(Level.SEVERE, "Error in replace System Logger", e);
            }
        }
        initialized = true;
    }
}
