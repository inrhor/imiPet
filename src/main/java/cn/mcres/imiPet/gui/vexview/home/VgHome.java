package cn.mcres.imiPet.gui.vexview.home;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.data.Info;
import cn.mcres.imiPet.api.other.GetBooleanValue;
import cn.mcres.imiPet.api.vexgui.component.VexHomePage;
import cn.mcres.imiPet.build.utils.entity.EntityCreator;
import cn.mcres.imiPet.build.utils.Msg;
import cn.mcres.imiPet.gui.vexview.GlobalGet;
import cn.mcres.imiPet.gui.vexview.GlobalVsl;
import cn.mcres.imiPet.api.fastHandle.FollowSetHandle;
import cn.mcres.imiPet.gui.vexview.home.expansion.ReleasePetButton;
import cn.mcres.imiPet.model.ModelInfoManager;
import cn.mcres.imiPet.other.MapAll;
import io.izzel.taboolib.module.locale.TLocale;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;


import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class VgHome {
    private static Info info() {
        return ImiPet.loader.getInfo();
    }

    public static VexGui gui(Player player) {
        VexGui vg = new VexGui(vgGui1Url, vgGui1X, vgGui1Y, vgGui1W, vgGui1H, vgGui1W, vgGui1H);
        VexScrollingList vsl = new VexScrollingList(vgVsl1X, vgVsl1Y, vgVsl1W, vgVsl1H, vgVsl1Full);
        UUID playerUUID = player.getUniqueId();

        // 图片-宠物模型背景
        vg.addComponent(new VexImage(vgImage1Url, vgImage1X, vgImage1Y, vgImage1W, vgImage1H));
        // 图片-宠物信息背景
        vg.addComponent(new VexImage(vgImage2Url, vgImage2X, vgImage2Y, vgImage2W, vgImage2H));

        if (havePetPackList(playerUUID)) {
            ArmorStand armorStand = (ArmorStand) EntityCreator.create(EntityType.ARMOR_STAND, player.getLocation());

            // 图片-宠物模型
            List<UUID> list = info().getPetsPackList(playerUUID);
            if (!list.isEmpty()) {
//                String imageShowBigUrl = GetPetsYaml.getString(info().getPetModelId(player, list.get(MapAll.guiHomeSelectPet.get(player)), "pets"), "imageShowBig");
//                vg.addComponent(new VexImage(imageShowBigUrl, vgImage1X, vgImage1Y + 5, vgImage1W - 10, vgImage1H - 10));

                String modelId = info().getPetModelId(player, list.get(MapAll.guiHomeSelectPet.get(player)), "pets");

                /*new BukkitRunnable() {
                    @Override
                    public void run() {
                        // 异步处理，防止阻塞
                        Bukkit.getScheduler().runTaskAsynchronously(ImiPet.loader.getPlugin(), () -> {

                        });
                    }
                }.runTaskLater(ImiPet.loader.getPlugin(), 1);*/

                ModelInfoManager infoManager = ModelInfoManager.petModelList.get(modelId);

                /*String itemName = GetPetsYaml.getString(modelId, "entityDraw.itemName");
                int customModelData = GetPetsYaml.getInt(modelId, "entityDraw.customModelData");
                int bigSize = GetPetsYaml.getInt(modelId, "entityDraw.vgHome.big.size");
                int bigX = GetPetsYaml.getInt(modelId, "entityDraw.vgHome.big.x");
                int bigY = GetPetsYaml.getInt(modelId, "entityDraw.vgHome.big.y");*/
                String itemName = infoManager.getAnimationItemNameIdle();
                int customModelData = infoManager.getAnimationCustomModelDataIdle();
                int bigSize = infoManager.getVgHomeDrawBigSize();
                int bigX = infoManager.getVgHomeDrawBigX();
                int bigY = infoManager.getVgHomeDrawBigY();

                ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(itemName);
                if (!ImiPet.loader.getServerInfo().isOldVersion()) {
                    itemMeta.setCustomModelData(customModelData);
                }
                itemStack.setItemMeta(itemMeta);

                armorStand.setSmall(true);
                armorStand.setVisible(false);
                armorStand.setHelmet(itemStack);
                VexEntityDraw vexEntityDraw = new VexEntityDraw(bigX, bigY, bigSize, armorStand);
                vg.addComponent(vexEntityDraw);

                // 图片-已选择宠物
                UUID petUUID = list.get(MapAll.guiHomeSelectPet.get(player));
                int i = MapAll.guiHomeSelectPet.get(player);
                vg.addComponent(new VexImage(vgImage3Url, vgImage3X + vgImage3W * i + vgImage3AddX * i, vgImage3Y, vgImage3W, vgImage3H));

                // 按钮-跟随与否
                vg.addComponent(new VexButton(vgButton2ID, vgButton2Text, vgButton2Url1, vgButton2Url2, vgButton2X, vgButton2Y, vgButton2W, vgButton2H, pp -> {
                    if (!GetBooleanValue.inDisablePetWorld(pp)) {
                        FollowSetHandle.runInfo(pp, petUUID);
                        VexViewAPI.openGui(pp, gui(pp));
                    }else {
//                        Msg.send(pp, DISABLE_PET_FOLLOW_WORLD);
                        TLocale.sendTo(player, "DISABLE_PET_FOLLOW_WORLD");
                    }
                }, new VexHoverText(vgButtonHoverText2)));

                // 按钮-放生
                vg.addComponent(new VexButton(vgButton5ID, vgButton5Text, vgButton5Url1, vgButton5Url2, vgButton5X, vgButton5Y, vgButton5W, vgButton5H, pp -> {
                    VexViewAPI.openGui(pp, ReleasePetButton.open(pp, petUUID));
                }, new VexHoverText(vgButtonHoverText5)));

                // 为了宠物信息列表能够间接获得petUUID
                MapAll.vgHomePagePetUUID.put(player, petUUID);
                // 宠物信息列表相关组件
                if (MapAll.vgHomePagePlayer.get(player)!=null) {
                    for (VexComponents vexComponents : VexHomePage.vgHomePageComponents.get(MapAll.vgHomePagePlayer.get(player))) {
                        vg.addComponent(vexComponents);
                    }

                    int nowPage = MapAll.vgHomePagePlayer.get(player);
                    // 宠物信息列表 上下页
                    vg.addComponent(new VexButton(vgButtonID22, vgButtonText22, vgButtonUrl122, vgButtonUrl222, vgButtonX22, vgButtonY22, vgButtonW22, vgButtonH22, pp -> {
                        if ((nowPage > VexHomePage.vgHomePageInt)) {
                            MapAll.vgHomePagePlayer.put(pp, nowPage-1);
                            VexViewAPI.openGui(pp, gui(pp));
                        }
                    }));
                    vg.addComponent(new VexButton(vgButtonID23, vgButtonText23, vgButtonUrl123, vgButtonUrl223, vgButtonX23, vgButtonY23, vgButtonW23, vgButtonH23, pp -> {
                        if ((nowPage < VexHomePage.vgHomePageInt)) {
                            MapAll.vgHomePagePlayer.put(pp, nowPage+1);
                        }
                    }));
                }

                // 实体绘制
                for (int i3 = 0; i3 < 6; i3++) {
                    int i2 = i3;
                    if (list.size() > i2) {
                        vg.addComponent(new VexButton(vgButton1ID + i2, vgButton1Text, vgButton1Url1, vgButton1Url2, vgButton1X + vgButton1W * i2 + vgButton1AddX * i2, vgButton1Y, vgButton1W, vgButton1H, pp -> {
                            selectPetOpen(pp, i2);
                        }));
                        String selModelId = info().getPetModelId(player, list.get(i2), "pets");
                        ModelInfoManager infoManager2 = ModelInfoManager.petModelList.get(selModelId);
                        /*int x = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.x");
                        int y = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.y");
                        int addX = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.addX");*/
                        int x = infoManager2.getVgHomeDrawSmallX();
                        int y = infoManager2.getVgHomeDrawSmallY();
                        int addX = infoManager2.getVgHomeDrawSmallAddX();
                        int newX = x + vgButton1X + vgButton1W * i2 + addX * i2;
                        int newY = vgButton1Y + y;
                        GlobalGet.vgButtonEntity(armorStand, player, list, i2, vg, newX, newY, "vgHome");


                        /*Bukkit.getScheduler().runTask(ImiPet.loader.getPlugin(), () -> {
                            GlobalGet.vgButtonEntity(armorStand, player, list, i2, vg, newX, newY, "vgHome");
                        });*/

                        /*new BukkitRunnable() {
                            @Override
                            public void run() {
                                // 异步处理，防止阻塞
                                Bukkit.getScheduler().runTaskAsynchronously(ImiPet.loader.getPlugin(), () -> {
                                    GlobalGet.vgButtonEntity(armorStand, player, list, i2, vg, newX, newY, "vgHome");
                                });
                            }
                        }.runTaskLater(ImiPet.loader.getPlugin(), 1);*/
                        /*Thread thread = new Thread(() -> {
                            GlobalGet.vgButtonEntity(armorStand, player, list, i2, vg, newX, newY, "vgHome");
                        });
                        thread.start();
                        thread.interrupt();*/
                        /*Thread thread = new Thread(() -> {
                            vg.addComponent(new VexButton(vgButton1ID + i2, vgButton1Text, vgButton1Url1, vgButton1Url2, vgButton1X + vgButton1W * i2 + vgButton1AddX * i2, vgButton1Y, vgButton1W, vgButton1H, pp -> {
                                selectPetOpen(pp, i2);
                            }));
                            String selModelId = info().getPetModelId(player, list.get(i2), "pets");
                            int x = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.x");
                            int y = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.y");
                            int addX = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.addX");
                            int newX = x + vgButton1X + vgButton1W * i2 + addX * i2;
                            int newY = vgButton1Y + y;
                            GlobalGet.vgButtonEntity(armorStand, player, list, i2, vg, newX, newY, "vgHome");
                        });
                        thread.start();
                        thread.interrupt();*/
                        /*Bukkit.getScheduler().runTask(ImiPet.loader.getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                vg.addComponent(new VexButton(vgButton1ID + i2, vgButton1Text, vgButton1Url1, vgButton1Url2, vgButton1X + vgButton1W * i2 + vgButton1AddX * i2, vgButton1Y, vgButton1W, vgButton1H, pp -> {
                                    selectPetOpen(pp, i2);
                                }));
                                String selModelId = info().getPetModelId(player, list.get(i2), "pets");
                                int x = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.x");
                                int y = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.y");
                                int addX = GetPetsYaml.getInt(selModelId, "entityDraw.vgHome.small.addX");
                                int newX = x + vgButton1X + vgButton1W * i2 + addX * i2;
                                int newY = vgButton1Y + y;
                                GlobalGet.vgButtonEntity(armorStand, player, list, i2, vg, newX, newY, "vgHome");
                            }
                        });*/
                    }
                }
            }
        }

        GlobalVsl.addVsl(vg, vsl);

        return vg;
    }

    private static boolean havePetPackList(UUID playerUUID) {
        return !info().getPetsPackList(playerUUID).isEmpty();
    }

    private static void selectPetOpen(Player player, int index) {
        UUID playerUUID = player.getUniqueId();
        if (havePetPackList(playerUUID) && info().getPetsPackList(playerUUID).size() > index) {
            MapAll.guiHomeSelectPet.put(player, index);
            VexViewAPI.openGui(player, gui(player));
        }
    }
}
