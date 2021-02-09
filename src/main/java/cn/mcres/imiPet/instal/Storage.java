package cn.mcres.imiPet.instal;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.data.MySQLSaver;
import cn.mcres.imiPet.data.RunPlayerData;
import cn.mcres.imiPet.data.sql.SQLConfig;
import cn.mcres.imiPet.data.yaml.PlayerFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Storage {

    private static JavaPlugin main() {
        return ImiPet.loader.getPlugin();
    }

    public static String storage = "YAML";

    public void run() {
        if (main().getConfig().getBoolean("mysql.enable")) {
            SQLConfig sqlConfig = new SQLConfig();
            sqlConfig.min = 0;
            sqlConfig.url = main().getConfig().getString("mysql.url");
            sqlConfig.table_name = main().getConfig().getString("mysql.tableName");
            sqlConfig.usr = main().getConfig().getString("mysql.user");
            sqlConfig.pwd = main().getConfig().getString("mysql.password");
            ImiPet.loader.setInfo(new MySQLSaver(sqlConfig));
            storage = "MySQL" ;
        }else {
            new PlayerFile().run();
            ImiPet.loader.setInfo(new RunPlayerData());
        }
    }
}
