package net.kettlemc.nocroptrample;

import net.kettlemc.nocroptrample.configuration.Configuration;
import net.kettlemc.nocroptrample.listener.TrampleListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NoCropTramplePlugin extends JavaPlugin {
	
	private Configuration configuration;
	
	public void onEnable() {
		this.configuration = new Configuration();
		this.registerBukkit();
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	private void registerBukkit() {
		Bukkit.getPluginManager().registerEvents(new TrampleListener(this), this);
	}
}