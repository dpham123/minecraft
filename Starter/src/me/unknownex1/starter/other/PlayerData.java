package me.unknownex1.starter.other;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerData {
	private Player player;
	private String startTime;
	private Vector initialVelocity;
	private Location initialPlayerLocation;
	
	PlayerData(Player player, String startTime, Vector initialVelocity, Location playerInitialLocation) {
		this.player = player;
		this.startTime = startTime;
		this.initialVelocity = initialVelocity;
		this.initialPlayerLocation = playerInitialLocation;
	}
	
	Player getPlayer() {
		return player;
	}
	
	String getStartTime() {
		return startTime;
	}
	
	Vector getInitialVelocity() {
		return initialVelocity;
	}
	
	Location getInitialPlayerLocation() {
		return initialPlayerLocation;
	}
	
	boolean equals(PlayerData pd) {
		if (this.player.getEntityId() == pd.player.getEntityId()) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return player.getEntityId();
	}
	

}
