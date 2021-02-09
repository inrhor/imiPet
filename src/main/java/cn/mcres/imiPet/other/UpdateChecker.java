package cn.mcres.imiPet.other;

import cn.mcres.imiPet.build.utils.LogUtil;
import cn.mcres.imiPet.build.utils.Msg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateChecker {
    private final JavaPlugin javaPlugin;

    // 插件当前版本
    private final String pluginNowVersion;

    // 插件最新版本
    private String pluginNewVersion;

    // 为了获取网页指定内容
    private final String getContent = "imipet - version:";

    // 插件有最新版本消息
    private final String PLUGIN_UPDATE = "&f[&bimiPet&f] &a发现新版本更新：&fhttp://www.imipet.com/resources/2/";

    // 更新检查失败
    private final String CHECK_UPDATE_FAILED = "&f[&bimiPet&f] &c插件更新检查失败，请检查您的网络或者反馈作者";

    // 提醒OP插件更新
    private final List<String> UPDATE_SEND_OP = Arrays.asList("&f", "&f   §7§l[§6§limiPet§7§l]", "&f   &a新版本插件发布了", "&f   &f请前往： &6http://www.imipet.com/resources/2/ &f查看新版本内容并更新吧", "&f");

    private final List<String> UPDATE_LOG = new ArrayList<>();

    public UpdateChecker(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        // 从plugin.yml获取当前版本
        this.pluginNowVersion = javaPlugin.getDescription().getVersion();
    }

    public void checkForUpdate() {
        /*new BukkitRunnable() {
            @Override
            public void run() {*/
                // 异步处理，防止阻塞
                Bukkit.getScheduler().runTaskAsynchronously(javaPlugin, () -> {
                    try {
                        // 访问更新检查器网页
                        final URL url = new URL("http://www.imipet.com/updatecheck.html");

                        // 获得一个输入流
                        InputStream stream = url.openStream();

                        // 字节流转换为字符流
                        InputStreamReader inputStreamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);

                        // 为字符输入流添加缓冲
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        // 多行读取内容
                        String read = bufferedReader.readLine();

                        //输入内容
                        while (read != null ) {
                            if (read.contains(getContent)) {
                                pluginNewVersion = read.substring(read.indexOf(":")+1);
                            }else {
                                UPDATE_LOG.add(read);
                            }
                            read = bufferedReader.readLine();
                        }

                        //关闭相应的流
                        bufferedReader.close();
                        inputStreamReader.close();
                        stream.close();
                    } catch (final IOException e) {
                        // 更新检查失败
                        LogUtil.send(CHECK_UPDATE_FAILED);
//                        cancel();
                        return;
                    }

                    if (pluginNewVersion.isEmpty()) {
                        LogUtil.send(CHECK_UPDATE_FAILED);
                        return;
                    }

                    // 如果当前版本是最新版本则不执行下面内容
                    if (pluginNowVersion.equals(pluginNewVersion)) return;

                    // 如果不是最新版本，则提醒更新
                    LogUtil.send(PLUGIN_UPDATE);

                    // 当玩家进入游戏
                    Bukkit.getScheduler().runTask(javaPlugin, () -> Bukkit.getPluginManager().registerEvents(new Listener() {
                        @EventHandler(priority = EventPriority.MONITOR)
                        public void onPlayerJoin(final PlayerJoinEvent event) {
                            final Player player = event.getPlayer();
                            // 如果玩家有更新检查权限，则提醒他
                            if (player.hasPermission("imipet.update")) {
                                Msg.send(player, UPDATE_LOG);
                            }
                        }
                    }, javaPlugin));

                    // 找到了更新，取消任务
//                    cancel();
                });
            /*}
        }.runTaskTimer(javaPlugin, 0, 10_000);*/
    }
}