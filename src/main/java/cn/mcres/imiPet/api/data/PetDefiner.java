package cn.mcres.imiPet.api.data;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Create at 2019/12/28 1:02
 * Copyright Karlatemp
 * imiPet $ cn.mcres.imiPet.api.data
 */
public class PetDefiner {
    public UUID player;
    public UUID petUUID;
    public String petName;
    public double petMaxHP;
    public double petNowHP;
    public double petMinDamage;
    public double petMaxDamage;
    public int petLevel;
    public int petMaxExp;
    public int petNowExp;
    public int petMaxFood;
    public int petNowFood;
    public String modelId;
    public boolean apEnable;
    public List<String> apAttribute;
    public boolean buffAPEnable;
    public List<String> buffAPList;

    public void checkup() {
        Objects.requireNonNull(player);
        Objects.requireNonNull(petUUID);
        Objects.requireNonNull(modelId);
    }
}
