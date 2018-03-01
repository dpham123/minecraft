package me.unknownex1.starter;

import org.bukkit.plugin.java.JavaPlugin;

import me.unknownex1.starter.commands.SPCommand;
import me.unknownex1.starter.events.CustomFireWork;
import me.unknownex1.starter.events.Interact;
import me.unknownex1.starter.items.CustomItems;
import me.unknownex1.starter.other.ProjectileHit;
import me.unknownex1.starter.other.VectorDataCollector;

public class StarterPlugin extends JavaPlugin {
	
	@Override
	public void onEnable(){
		getLogger().info("Starter has been enabled");
		loadConfig();
		registerEvents();
		
		CustomItems items = new CustomItems();
		items.customRecipe();
		items.unShaped();
		
		// Register commands
		this.getCommand("kit").setExecutor(new SPCommand());
	}
	
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("Starter has been disabled");
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new VectorDataCollector(), this);
		//getServer().getPluginManager().registerEvents(new Interact(), this);
		getServer().getPluginManager().registerEvents(new ProjectileHit(), this);
	}
	
	private static void sop(Object x) {
		System.out.println(x);
	}
}
