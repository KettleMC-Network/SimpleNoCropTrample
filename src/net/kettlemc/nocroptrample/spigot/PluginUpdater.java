package net.kettlemc.nocroptrample.spigot;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class PluginUpdater {

    private static PluginUpdater updater;
    private static final String UPDATE_CHECK_LINK = "https://api.spigotmc.org/legacy/update.php?resource=%resource%";
    private static final String UPDATE_DOWNLOAD_LINK = "https://spigotmc.org/resources/%resource%/";

    private final int id;
    private final JavaPlugin plugin;

    public static PluginUpdater initUpdater(JavaPlugin plugin, int id) {
        return updater != null ? updater : new PluginUpdater(plugin, id);
    }

    private PluginUpdater(JavaPlugin plugin, int id) {
        this.id = id;
        this.plugin = plugin;
    }

    enum UpdateStatus {
        UPDATE_AVAILABLE(),
        LATEST_VERSION(),
        BETA_VERSION(),
        ERROR(); // currently unused
    }

    public void startChecker() {
        new BukkitRunnable() {

            @Override
            public void run() {
                String latest = getLatestVersion(UPDATE_CHECK_LINK.replace("%resource%", String.valueOf(id)));
                String current = plugin.getDescription().getVersion();
                plugin.getLogger().info("You're running version " + current + ". Latest version is " + latest + ".");
                switch (getUpdate(current, latest)) {
                    case BETA_VERSION:
                        plugin.getLogger().info("You're currently running a beta-version.");
                        break;
                    case LATEST_VERSION:
                        plugin.getLogger().info("You're currently running the latest version.");
                        break;
                    case UPDATE_AVAILABLE:
                        plugin.getLogger().info("There is an update available!");
                        plugin.getLogger().info("Link: " + UPDATE_DOWNLOAD_LINK.replace("%resource%", String.valueOf(id)));
                        break;
                    default:
                        plugin.getLogger().severe("Couldn't check for any updates!");
                        break;
                }
            }
        }.runTaskLaterAsynchronously(this.plugin, 20L);
    }



    public UpdateStatus getUpdate(String version1, String version2) {
       try {
            String version1Use = version1.split("-")[0], version2Use = version2.split("-")[0];
            if (!version1Use.matches("[0-9]+(\\.[0-9]+)*")
                    || !version2Use.matches("[0-9]+(\\.[0-9]+)*"))
                throw new IllegalArgumentException("Invalid version format");

            String[] version1Parts = version1Use.split("\\.");
            String[] version2Parts = version2Use.split("\\.");

            for (int i = 0; i < Math.max(version1Parts.length, version2Parts.length); i++) {
                int version1Part = i < version1Parts.length ? Integer.parseInt(version1Parts[i]) : 0;
                int version2Part = i < version2Parts.length ? Integer.parseInt(version2Parts[i]) : 0;
                if (version1Part < version2Part)
                    return UpdateStatus.UPDATE_AVAILABLE;
                if (version1Part > version2Part)
                    return UpdateStatus.BETA_VERSION;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return UpdateStatus.ERROR;
        }

        return UpdateStatus.LATEST_VERSION;
    }

    private String getLatestVersion(String url) {
        String version = "";

        try {
            Scanner scanner = new Scanner(new URL(url).openStream());
            String all = "";
            while (scanner.hasNextLine()) {
                all += scanner.nextLine();
            }
            scanner.close();
            version = all;
        } catch (IOException e) {
            this.plugin.getLogger().severe("Couldn't check the latest version!");
        }
        return version;
    }



}
