package cn.mcres.imiPet.instal;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.entity.ai.OwnerEvent;
import cn.mcres.imiPet.gui.vanilla.ClickGuiListener;
import cn.mcres.imiPet.gui.vanilla.GuiSelectPet;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.gui.vanilla.castSkill.GuiCastSkill;
import cn.mcres.imiPet.gui.vanilla.castSkill.GuiUnSkill;
import cn.mcres.imiPet.gui.vanilla.evolution.GuiEvolution;
import cn.mcres.imiPet.gui.vanilla.exp.GuiExpBox;
import cn.mcres.imiPet.gui.vanilla.home.GuiHome;
import cn.mcres.imiPet.gui.vanilla.updateInfo.GuiUpdateInfo;
import cn.mcres.imiPet.gui.vanilla.warehouse.GuiWarehouse;
import cn.mcres.imiPet.listener.*;
import cn.mcres.imiPet.pet.listener.*;
import cn.mcres.imiPet.server.ServerInfo;
import org.bukkit.event.Listener;

import java.util.Arrays;
import org.bukkit.plugin.java.JavaPlugin;

class RegisterEvents {
    public static ServerInfo getServerInfo() {
        return ImiPet.loader.getServerInfo();
    }

    public static JavaPlugin getPlugin() {
        return ImiPet.loader.getPlugin();
    }

    void run() {
        for (Listener listener : Arrays.asList(
                new PlayerJoinQuit(),
                new Attack(),
                new Eat(),
                new Death(),
                new OwnerGetExp(),
                new PetPotionEffect(),
                new PetLevelChange(),
                new PlayerChangedWorld(),
                new Hurt(),
                new PlayerClickPet(),
                new StopRideEvent(),
                new OwnerEvent(),
                new ClickGuiListener(),
                new GuiHome(),
                new GuiExpBox(),
                new GuiWarehouse(),
                new GuiUpdateInfo(),
                new GuiEvolution(),
                new GuiSelectPet(),
                new GuiUtil(),
                new GuiCastSkill(),
                new GuiUnSkill()
        )) {
            getPlugin().getServer().getPluginManager().registerEvents(listener, getPlugin());
        }
        if (getServerInfo().isOldVersionMaterial()) {
            getPlugin().getServer().getPluginManager().registerEvents(new ArmorStandOldVer(), getPlugin());
        }
    }
}
