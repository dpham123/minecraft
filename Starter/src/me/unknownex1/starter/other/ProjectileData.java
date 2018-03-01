package me.unknownex1.starter.other;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class ProjectileData {
	private int entityID;
	private String startTime;
	private Vector initialVelocity;
	private Location initialProjectileLocation;
	private Location entityLocation;
	private Projectile p;

	ProjectileData(Integer entityID, String startTime, Vector initialVelocity,
			Location initialProjectileLocation, Location projectileShooterLocation, Projectile p) {
		this.entityID = entityID;
		this.startTime = startTime;
		this.initialVelocity = initialVelocity;
		this.initialProjectileLocation = initialProjectileLocation;
		this.entityLocation = projectileShooterLocation;
		this.p = p;
	}
	
	int getEntityID() {
		return entityID;
	}
	
	String getStartTime() {
		return startTime;
	}
	
	Vector getInitialVelocity() {
		return initialVelocity;
	}
	
	Location getInitialProjectileLocation() {
		return initialProjectileLocation;
	}
	
	Location getEntityLocation() {
		return entityLocation;
	}
	
	Projectile getP() {
		return p;
	}
	
	boolean equals(ProjectileData pd) {
		if (this.getEntityID() == pd.getEntityID()) {
			sop("Equals accessed");
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return entityID;
	}
	
	private void sop(Object x) {
		System.out.println(x);
	}

}
