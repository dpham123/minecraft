package me.unknownex1.starter.items;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.unknownex1.starter.StarterPlugin;
import net.md_5.bungee.api.ChatColor;

public class CustomItems implements Listener {
	private Plugin plugin = StarterPlugin.getPlugin(StarterPlugin.class);

	public void giveItems(Player player) {

	}

	public void customRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "AXE OF ZEUS");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.WHITE + "Used by Zeus in the great god battle");
		meta.setLore(lore);
		item.setItemMeta(meta);
		sop("Custom Items : Checkpoint 1");

		NamespacedKey key = new NamespacedKey(plugin, "AXE_OF_ZEUS");
		sop("Custom Items : Checkpoint 2");
		ShapedRecipe zeusAxe = new ShapedRecipe(key, item);
		sop("Custom Items : Checkpoint 3");

		zeusAxe.shape("#% ", "#$ ", " $ ");
		sop("Custom Items : Checkpoint 4");
		zeusAxe.setIngredient('#', Material.DIAMOND);
		sop("Custom Items : Checkpoint 5");
		zeusAxe.setIngredient('%', Material.IRON_INGOT);
		sop("Custom Items : Checkpoint 6");
		zeusAxe.setIngredient('$', Material.STICK);
		sop("Custom Items : Checkpoint 7");
		Bukkit.getServer().addRecipe(zeusAxe);
		sop("Custom Items : Checkpoint 8");
	}

	public void unShaped() {
		ItemStack item = new ItemStack(Material.BLAZE_POWDER, 1);
		NamespacedKey key = new NamespacedKey(plugin, "Blaze_Powder");
		ShapelessRecipe slr = new ShapelessRecipe(key, item);
		slr.addIngredient(3, Material.LAVA_BUCKET);
		slr.addIngredient(3, Material.FLINT);
		Bukkit.getServer().addRecipe(slr);
	}

	private static void sop(Object x) {
		boolean debugEnabled = false;
		if (debugEnabled) {
			System.out.println(x);
		}
	}
}
