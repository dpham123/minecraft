package me.unknownex1.starter.other;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHit implements Listener {

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile p = event.getEntity();
		
		// Returns if the projectile is not an arrow
		if (!(p.getType() == EntityType.ARROW))
			return;
		
		// Makes sure the shooter is a skeleton or a player
		if (!(p.getShooter() instanceof Skeleton)){
			return;
		}
		
		// Cast shooter to Entity
		Entity shooter = (Entity) p.getShooter();
		
		// Gets location and world of where the arrow hit
		Location l = p.getLocation();
		World world = l.getWorld();
		
		// If the object hit is an entity, don't spawn skeleton
		// If the entity hit is a skeleton, then bounce the arrow
		Entity hitEntity = event.getHitEntity();
		if (hitEntity != null) {
			if (hitEntity.getType() == EntityType.SKELETON) {
				p.setBounce(true);
			}
			return;
		}

		// Gets nearby entities in a 20 x 20 x 20 box
		Collection<Entity> nearbyEntities = world.getNearbyEntities(l, 20, 10, 20);

		// Counts for skeletons
		int skellyCounter = 0;
		for (Entity e : nearbyEntities) {
			if (e.getType() == EntityType.SKELETON) {
				skellyCounter++;
				LivingEntity le = (LivingEntity) e;
				
				if (le.getHealth() < 5) {
					artilleryFire(le);
				}
			}
		}
		
		// Spawns skeletons only if there are less than 5 skeletons in the 20 x 20 x 20 cube
		if (skellyCounter < 5 && shooter.getFireTicks() == -1) {
			skellyMultiply(l, world);
		}
	}
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		// Gets the damager
		Entity damager = event.getDamager();
		
		// Checks if the damager is an arrow
		if (damager.getType() != EntityType.ARROW)
			return;
		
		Projectile p = (Projectile) damager;
		
		// Checks if the shooter of the arrow is a skeleton
		if (!(p.getShooter() instanceof Skeleton))
			return;
		
		// Checks if the damagee is a skeleton
		Entity damagee = event.getEntity();
		if (damagee.getType() == EntityType.SKELETON) {
			event.setCancelled(true);
		}
	}

	private void skellyMultiply(Location l, World world) {
		world.spawnEntity(l, EntityType.SKELETON);
		world.spawnEntity(l, EntityType.SKELETON);
	}

	private void artilleryFire(LivingEntity le) {
		le.launchProjectile(Arrow.class);
	}
	
	private static void sop(Object x) {
		System.out.println(x);
	}

}
