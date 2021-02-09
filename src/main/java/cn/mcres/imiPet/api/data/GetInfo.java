package cn.mcres.imiPet.api.data;

import cn.inrhor.imipet.ImiPet;

public class GetInfo {
    /**
     * @return 返回Info类
     */
    public static Info data() {
        return ImiPet.loader.getInfo();
    }
}
