package cn.mcres.imiPet.build.utils;

import java.util.function.Consumer;

/**
 * Create at 2020/2/10 17:43
 * Copyright Karlatemp
 * imiPet $ cn.mcres.imiPet.build.utils
 */
public class ScriptInvokingCallback<T> implements Consumer<T> {
    public T value;
    public boolean hasValue;

    @Override
    public void accept(T o) {
        hasValue = true;
        value = o;
    }
}
