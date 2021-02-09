package cn.mcres.imiPet.api.vexgui.component;

import lk.vexview.gui.components.VexComponents;

import java.util.*;

public class VexHomePage {
    /**
     * 设置主界面的信息列表的最大页数
     */
    public static int vgHomePageInt;
    /**
     * 界面的信息列表的组件列表，允许添加组件(参数：组件列表所在页数 组件列表)
     */
    public static HashMap<Integer, List<VexComponents>> vgHomePageComponents = new LinkedHashMap<>();
}
