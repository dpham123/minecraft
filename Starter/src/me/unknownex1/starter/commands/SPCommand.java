package me.unknownex1.starter.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class SPCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage("You are a player");
			
			ItemStack bricks = new ItemStack(Material.BRICK, 20);
			player.getInventory().addItem(bricks);
			return true;
		} else {
			sender.sendMessage("You must be a player");
		}
		return false;
	}

}
