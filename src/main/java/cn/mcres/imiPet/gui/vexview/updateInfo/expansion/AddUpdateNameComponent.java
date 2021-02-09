package cn.mcres.imiPet.gui.vexview.updateInfo.expansion;

import cn.inrhor.imipet.ImiPet;
import cn.mcres.imiPet.api.fastHandle.UpdatePetName;
import cn.mcres.imiPet.gui.vexview.updateInfo.VgUpdateInfo;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexHoverText;
import lk.vexview.gui.components.VexTextField;
import org.bukkit.entity.Player;

import java.util.UUID;

import static cn.mcres.imiPet.yaml.get.GetVexGuiYaml.*;

public class AddUpdateNameComponent {

    public static VexGui open(Player player, UUID petUUID) {
        VexGui vg = VgUpdateInfo.gui(player);

        vg.addComponent(new VexTextField(vgVTFX4, vgVTFY4, vgVTFW4, vgVTFH4, ImiPet.config.getInt("petName.maximum"), vgVTFID4));

        vg.addComponent(new VexButton(vgButtonID21, vgButtonText21, vgButtonUrl121, vgButtonUrl221, vgButtonX21, vgButtonY21, vgButtonW21, vgButtonH21, pp -> {
            UpdatePetName.run(pp.getUniqueId(), petUUID, vg.getTextField(vgVTFID4).getTypedText(), true, "pets");
        }, new VexHoverText(vgButtonHoverText21)));
        return vg;
    }
}
