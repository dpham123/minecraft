package me.unknownex1.starter.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

import me.unknownex1.starter.StarterPlugin;

public class CustomFireWork implements Listener {
	private Player player;

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		this.player = event.getPlayer();
		Location spawn = new Location(player.getWorld(), -61, 119, 463);
		player.teleport(spawn);
		
		// Delay the firework launch for 1 second
		Plugin plugin = StarterPlugin.getPlugin(StarterPlugin.class);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
				
				@Override
				public void run() {
					Random rand = new Random();
					for (int i = 0; i < 200; i++) {
						int n = rand.nextInt(4);
						Firework fw = player.getWorld().spawn(player.getLocation().add(n * 3,n * n,n * -3), Firework.class);
						FireworkMeta fwMeta = fw.getFireworkMeta();
						Builder builder = FireworkEffect.builder();

						fwMeta.addEffect(builder.flicker(true).withColor(Color.RED).build());
						fwMeta.addEffect(builder.withFade(Color.GREEN).build());
						fwMeta.addEffect(builder.trail(true).build());
						fwMeta.addEffect(builder.with(Type.BURST).build());
						fwMeta.setPower(0);
						fw.setFireworkMeta(fwMeta);
					}
				}
			}, 20L);
		
	}

	
}
