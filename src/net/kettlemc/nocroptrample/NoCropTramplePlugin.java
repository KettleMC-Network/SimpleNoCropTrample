package net.kettlemc.nocroptrample;

import net.kettlemc.nocroptrample.configuration.Configuration;
import net.kettlemc.nocroptrample.listener.TrampleListener;
import net.kettlemc.nocroptrample.bstats.bukkit.Metrics;
import net.kettlemc.nocroptrample.spigot.PluginUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NoCropTramplePlugin extends JavaPlugin {

	private static final int PLUGIN_METRICS_ID = 14051;
	private static final int PLUGIN_RESOURCE_ID = 73609;

	private Configuration configuration;
	private PluginUpdater updater;

	public void onEnable() {
		this.configuration = new Configuration();
		this.registerBukkit();
		this.startMetrics();
		if (configuration.checkForUpdates()) {
			this.updater = PluginUpdater.initUpdater(this, PLUGIN_RESOURCE_ID);
			this.updater.startChecker();
		} else {
			getLogger().info("Update checker disabled.");
		}
	}

	private void startMetrics() {
		new Metrics(this, PLUGIN_METRICS_ID);
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	private void registerBukkit() {
		Bukkit.getPluginManager().registerEvents(new TrampleListener(this), this);
	}
}