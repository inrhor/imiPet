package cn.mcres.imiPet.pet.file;

import cn.mcres.imiPet.model.ModelInfoManager;
import org.bukkit.configuration.file.FileConfiguration;

public class PublicUse {

    public static void setShowName(ModelInfoManager modelInfoManager, FileConfiguration petYml, String lib) {
        String section = "basis.display.";
        modelInfoManager.setShowNameH(petYml.getDouble(section+lib+".height"));
        modelInfoManager.setShowNameX(petYml.getDouble(section+lib+".x"));
        modelInfoManager.setShowNameZ(petYml.getDouble(section+lib+".z"));
        modelInfoManager.setShowNameFormatList(petYml.getStringList(section+lib+".formatList"));
    }

    public static void setClickIntoShow(ModelInfoManager modelInfoManager, FileConfiguration petYml, String lib) {
        String section = "interaction.info."+lib+".";
        modelInfoManager.setInfoClickTime(petYml.getInt(section+"stayTime"));
        modelInfoManager.setInfoClickStringList(petYml.getStringList(section+"stringList"));
        modelInfoManager.setInfoClickX(petYml.getInt(section+"x"));
        modelInfoManager.setInfoClickY(petYml.getInt(section+"y"));
        modelInfoManager.setInfoClickZ(petYml.getInt(section+"z"));
    }
}
