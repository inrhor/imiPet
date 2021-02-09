package cn.mcres.imiPet.data.yaml;

import cn.mcres.imiPet.build.files.CreateFile;

public class PlayerFile {
    public static CreateFile playerFile = new CreateFile("player");

    public void run() {
        playerFile.checkAndCreate();
    }

}
