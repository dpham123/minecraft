package me.unknownex1.starter.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.unknownex1.starter.items.CustomItems;

public class Interact implements Listener {
	//private CustomItems ci = new CustomItems();
	
	/*
	@EventHandler
	public void onPunch(PlayerInteractEvent event) {
		// Gives player Axe of Zeus
		Player player = event.getPlayer();
		ci.giveItems(player);
	}
	*/
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		// Gives player health on block clicked
		Action action = event.getAction();
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if (action.equals(Action.LEFT_CLICK_BLOCK)) {
			if (block.getType().equals(Material.EMERALD_BLOCK)) {

				if (player.getHealth() <= 19) {
					player.sendMessage("You have been healed:" + " +1 health");
					player.setHealth(player.getHealth() + 1);
				} else {
					player.setHealth(20);
				}
			} else {
				player.sendMessage("You clicked " + block.getType().toString().toUpperCase());
			}
		}
	}

}
