package cn.mcres.imiPet.build.files;

import cn.inrhor.imipet.ImiPet;

import java.io.File;

public class CreateFile {
    private File file;
    private String name;

    public CreateFile(String name) {
        this.name = name;
    }

    public void checkAndCreate() {
        this.file = new File(ImiPet.loader.getPlugin().getDataFolder(), this.name);
        if (!this.file.exists() || !this.file.isDirectory()) {
            this.file.mkdirs();
        }
    }

    public File getFile() {
        return this.file;
    }
}
