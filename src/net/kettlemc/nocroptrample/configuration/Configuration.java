package net.kettlemc.nocroptrample.configuration;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {

    private BasicConfigurationHandler config;

    private boolean disableMobTrampling, disablePlayerTrampling, enableUpdateChecker;
    private List<Material> farmlandBlocks;

    public Configuration() {
        this.config = new BasicConfigurationHandler("plugins/SimpleNoCropTrample/config.yml");
        this.enableUpdateChecker = this.config.getBool("enable-update-checker", true);
        this.disableMobTrampling = this.config.getBool("disable-mob-trampling", true);
        this.disablePlayerTrampling = this.config.getBool("disable-player-trampling", true);
        this.farmlandBlocks = new ArrayList<>();
        ((List<String>) this.config.getValue("farmlands-blocks", Arrays.asList(new String[]{"FARMLAND", "SOIL"}))).forEach(string -> {
            if (isValid(string))
                farmlandBlocks.add(Material.getMaterial(string));
        });
    }

    public List<Material> getFarmlandBlocks() {
        return this.farmlandBlocks;
    }

    public boolean isFarmlandsBlock(Material material) {
        return this.farmlandBlocks.contains(material);
    }

    public boolean isMobTramplingDisabled() {
        return this.disableMobTrampling;
    }

    public boolean isPlayerTramplingDisabled() {
        return this.disablePlayerTrampling;
    }

    public boolean checkForUpdates() {
        return this.enableUpdateChecker;
    }

    private boolean isValid(String material) {
        return Material.getMaterial(material) != null;
    }

}
