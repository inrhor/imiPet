package cn.mcres.imiPet.server;

import java.util.Arrays;
import java.util.List;

public class ServerInfo {
    private String serverVersion, serverName;
    private List<String> oldVersion =
            Arrays.asList("v1_8_R1", "v1_8_R2", "v1_8_R3",
                    "v1_9_R1", "v1_9_R2",
                    "v1_10_R1", "v1_11_R1", "v1_12_R1", "v1_13_R1", "v1_13_R2");

    private List<String> oldVersionMaterial =
            Arrays.asList("v1_8_R1", "v1_8_R2", "v1_8_R3", "v1_9_R1", "v1_9_R2", "v1_10_R1", "v1_11_R1", "v1_12_R1");

    private List<String> oldVersionMethod = Arrays.asList("v1_8_R1", "v1_8_R2", "v1_8_R3", "v1_9_R1");

    public ServerInfo(String ServerVersion, String serverName) {
        this.serverVersion = ServerVersion;
        this.serverName = serverName;
    }

    public String getServerVersion() {
        return this.serverVersion;
    }

    public boolean isOldVersion() {
        return this.oldVersion.contains(this.serverVersion);
    }

    public boolean isOldVersionMaterial() {
        return this.oldVersionMaterial.contains(this.serverVersion);
    }

    public boolean isOldVersionMethod() {
        return this.oldVersionMethod.contains(this.serverVersion);
    }

    public String getServerName() {
        return serverName;
    }
}
