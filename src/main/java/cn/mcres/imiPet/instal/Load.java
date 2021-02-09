package cn.mcres.imiPet.instal;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.gui.vanilla.GuiUtil;
import cn.mcres.imiPet.gui.vexview.home.VgHomePageComponents;
import cn.mcres.imiPet.instal.lib.*;
import cn.mcres.imiPet.pet.file.NewPetFile;
import cn.mcres.imiPet.skill.SkillFile;
import cn.mcres.imiPet.task.ModelTask;
import cn.mcres.imiPet.task.PetFollow;
import cn.mcres.imiPet.task.SaveData;

public class Load {

    public static void run() {

        // 开始检查
        if (!ImiPet.loader.isReloading()) {
            // 检查依赖
            PapiLib.load();
            new VaultLib().load();
            HDLib.load();
            TrHDLib.load();
            APLib.load();
            CMILib.load();
            MMLib.load();
            ModelLib.load();
            VexLib.load();

            ImiPet.loader.loadInfo();

            // 存储数据方式
            new Storage().run();
        }

        // 添加文件夹
        new SkillFile().run();
        new NewPetFile().run();

        // 添加YAML文件
        new YamlLoad().reload();

        // 加载贴图模型器
//        ModelManager.renderModel();

        // 注册监听器
        RegisterEvents reg = new RegisterEvents();
        reg.run();

        if (VexLib.VexLibEnable) {
            // 实例化主界面VgHomePageComponents组件
            new VgHomePageComponents().run();
        }

        // 注册技能
        SkillFile.addSkill();

        // 注册文件里的实体模型配置
        NewPetFile.addCustomModel();

        // 定时器
        PetFollow.petNameFollowTask();
        GuiUtil.runCoolDownTask();
        SaveData.run();
        ModelTask.renderModel();
        ModelTask.skillModel();
    }
}
