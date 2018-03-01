
package me.unknownex1.starter.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Move implements Listener{

	@EventHandler
	public void onMove(PlayerMoveEvent event) {

		Player player = event.getPlayer();
		Inventory pInven = player.getInventory();
		ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);

		if (!player.isOnGround()) {
			player.sendMessage("You are moving");
			pInven.addItem(item);
		}
	}
}
