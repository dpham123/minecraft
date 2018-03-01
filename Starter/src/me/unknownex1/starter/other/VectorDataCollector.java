package me.unknownex1.starter.other;

import org.bukkit.util.Vector;

import me.unknownex1.starter.StarterPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.BlockProjectileSource;

public class VectorDataCollector implements Listener {
	private HashSet<ProjectileData> projectiles;
	private HashSet<PlayerData> players;

	public VectorDataCollector() {
		this.projectiles = new HashSet<>();
		this.players = new HashSet<>();
	}

	@EventHandler
	private void onLaunch(ProjectileLaunchEvent event) {
		// Logs the time of the projectile launch
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String startTime = dateFormat.format(date);

		// Gets the projectile, entity ID, initial velocity, and initial projectile
		// location
		Projectile p = event.getEntity();
		int entityID = p.getEntityId();
		Vector initialVelocity = p.getVelocity();
		Location initialProjectileLocation = p.getLocation();

		// Checks if the shooter is a block
		if (p.getShooter() instanceof BlockProjectileSource) {
			BlockProjectileSource bps = (BlockProjectileSource) p.getShooter();
			Block projectileBlock = bps.getBlock();
			Location blockShooterLocation = projectileBlock.getLocation();
			ProjectileData projectile = new ProjectileData(entityID, startTime, initialVelocity,
					initialProjectileLocation, blockShooterLocation, p);
			projectiles.add(projectile);
		} else {
			// Gets the entity, entity location, and stores data in a ProjectileData instance
			Entity entity = (Entity) p.getShooter();
			Location entityShooterLocation = entity.getLocation();
			ProjectileData projectile = new ProjectileData(entityID, startTime, initialVelocity,
					initialProjectileLocation, entityShooterLocation, p);
			projectiles.add(projectile);
		}
		
		
	}

	@EventHandler
	private void onHit(ProjectileHitEvent event) {
		// Logs the time of the projectile
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String endTime = dateFormat.format(date);
		
		// Gets the projectile
		Projectile p = event.getEntity();
		
		// Finds the projectile created during the projectile launch event
		ProjectileData projectileLog = null;
		for (ProjectileData pd : projectiles) {
			if (p.getEntityId() == pd.getEntityID()) {
				projectileLog = pd;
				break;
			}
		}
		
		// Removes the projectile data from the HashSet
		projectiles.remove(projectileLog);
		if (projectileLog == null) {
			return;
		}
		
		// Gets final velocity and location of the projectile when makes contact
		Vector endVelocity = p.getVelocity();
		Location endProjectileLocation = p.getLocation();
		try {
			logToFileProjectile(projectileLog, endTime, endVelocity, endProjectileLocation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String startTime = dateFormat.format(date);

		Player player = event.getPlayer();
		Vector initialVelocity = player.getVelocity();
		Location playerInitialLocation = player.getLocation();

		PlayerData playerData = new PlayerData(player, startTime, initialVelocity, playerInitialLocation);
		players.add(playerData);
	}

	@EventHandler
	private void onHitGround(EntityDamageEvent event) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String endTime = dateFormat.format(date);

		if (event.getEntityType() != EntityType.PLAYER) {
			return;
		}

		if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
			return;
		}
		event.setCancelled(true);
		Player player = (Player) event.getEntity();

		if (player.getGameMode() == GameMode.CREATIVE) {
			return;
		}
		Vector endVelocity = player.getVelocity();
		Location endLocation = player.getLocation();

		PlayerData playerLog = null;
		if (players.size() == 0) {
			return;
		}
		for (PlayerData pd : players) {
			if (player == pd.getPlayer()) {
				playerLog = pd;
				break;
			}
		}
		players.remove(playerLog);
		try {
			logToFilePlayer(playerLog, endTime, endVelocity, endLocation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void logToFileProjectile(ProjectileData pd, String endTime, Vector endVelocity,
			Location endProjectileLocation) throws ParseException {
		Plugin plugin = StarterPlugin.getPlugin(StarterPlugin.class);
		File file = new File(plugin.getDataFolder() + "/vectorData.txt");
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date initialTime = dateFormat.parse(pd.getStartTime());
		Date finalTime = dateFormat.parse(endTime);
		double diff = finalTime.getTime() - initialTime.getTime();
		double diffInSecs = diff / 1000;

		try {
			FileWriter fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("Projectile: " + pd.getP());
			pw.println("ID: " + pd.getEntityID());
			pw.println("Start Time: " + pd.getStartTime());
			pw.println("End Time: " + endTime);
			pw.println("  Initial Velocity: " + pd.getInitialVelocity());
			pw.println("    Projectile Location: ");
			pw.println("    X: " + pd.getInitialProjectileLocation().getX());
			pw.println("    Y: " + pd.getInitialProjectileLocation().getY());
			pw.println("    Z: " + pd.getInitialProjectileLocation().getZ());
			pw.println("    Player Location: ");
			pw.println("    X: " + pd.getEntityLocation().getX());
			pw.println("    Y: " + pd.getEntityLocation().getY());
			pw.println("    Z: " + pd.getEntityLocation().getZ());
			pw.println("  End Velocity: " + endVelocity);
			pw.println("    Projectile Location: ");
			pw.println("    X: " + endProjectileLocation.getX());
			pw.println("    Y: " + endProjectileLocation.getY());
			pw.println("    Z: " + endProjectileLocation.getZ());
			pw.println(
					"Acceleration 1: " + (2 * (endProjectileLocation.getY() - pd.getInitialProjectileLocation().getY()
							- pd.getInitialVelocity().getY() * diffInSecs)) / (diffInSecs * diffInSecs));
			pw.println("Acceleration 2: " + ((endVelocity.getY() - pd.getInitialVelocity().getY()) / diffInSecs));
			pw.println("Acceleration 3: " + ((endVelocity.getY() * endVelocity.getY())
					- (pd.getInitialVelocity().getY() * pd.getInitialVelocity().getY()))
					/ (2 * (endProjectileLocation.getY() - pd.getInitialProjectileLocation().getY())));

			pw.println("");

			pw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void logToFilePlayer(PlayerData pd, String endTime, Vector endVelocity, Location endLocation)
			throws ParseException {
		Plugin plugin = StarterPlugin.getPlugin(StarterPlugin.class);
		File file = new File(plugin.getDataFolder() + "/vectorData.txt");
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date initialTime = dateFormat.parse(pd.getStartTime());
		Date finalTime = dateFormat.parse(endTime);
		double diff = finalTime.getTime() - initialTime.getTime();
		double diffInSecs = diff / 1000;

		try {
			FileWriter fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("Player: " + pd.getPlayer());
			pw.println("Start Time: " + pd.getStartTime());
			pw.println("End Time: " + endTime);
			pw.println("  Initial Velocity: " + pd.getInitialVelocity());
			pw.println("    Player Location: ");
			pw.println("    X: " + pd.getInitialPlayerLocation().getX());
			pw.println("    Y: " + pd.getInitialPlayerLocation().getY());
			pw.println("    Z: " + pd.getInitialPlayerLocation().getZ());
			pw.println("  End Velocity: " + endVelocity);
			pw.println("    Player Location: ");
			pw.println("    X: " + endLocation.getX());
			pw.println("    Y: " + endLocation.getY());
			pw.println("    Z: " + endLocation.getZ());
			pw.println("Acceleration 1: " + (2 * (endLocation.getY() - pd.getInitialPlayerLocation().getY()
					- pd.getInitialVelocity().getY() * diffInSecs)) / (diffInSecs * diffInSecs));
			pw.println("Acceleration 2: " + ((endVelocity.getY() - pd.getInitialVelocity().getY()) / diffInSecs));
			pw.println("Acceleration 3: " + ((endVelocity.getY() * endVelocity.getY())
					- (pd.getInitialVelocity().getY() * pd.getInitialVelocity().getY()))
					/ (2 * (endLocation.getY() - pd.getInitialPlayerLocation().getY())));

			pw.println("");

			pw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sop(Object x) {
		System.out.println(x);
	}

}
