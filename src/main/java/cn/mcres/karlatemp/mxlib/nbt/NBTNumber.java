/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/02/15 24:50:41
 *
 * MXLib/mxlib.message/NBTNumber.java
 */

package cn.mcres.karlatemp.mxlib.nbt;

public interface NBTNumber extends NBTBase {
    long asLong();

    int asInt();

    short asShort();

    byte asByte();

    double asDouble();

    float asFloat();

    Number getValue();
}
