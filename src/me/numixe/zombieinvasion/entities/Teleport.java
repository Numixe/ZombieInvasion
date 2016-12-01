package me.numixe.zombieinvasion.entities;

import me.numixe.zombieinvasion.math.Math;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class Teleport {

	private Location hub;
	private Location spectator;
	private Map<String, Location> villSpawns, zombieSpawns;	// name, location
	
	public Teleport() {
		hub = null;
		spectator = null;
			
		villSpawns = new HashMap<String, Location>();
		zombieSpawns = new HashMap<String, Location>();
	}
	
	public void setHub(Location loc) {
		
		if (!s.getSpawns().contains("Hub"))
			s.getSpawns().createSection("Hub");
		
		setLocation(s.getSpawns().getConfigurationSection("Hub"), loc);
		hub = loc.clone();
		s.saveSpawnsConfig();
	}
	
	public void setSpectator(Location loc) {
		
		if (!s.getSpawns().contains("Spectator"))
			s.getSpawns().createSection("Spectator");
		
		setLocation(s.getSpawns().getConfigurationSection("Spectator"), loc);
		spectator = loc.clone();
		s.saveSpawnsConfig();
	}
	
	public void loadSpectator() {	// call in onEnable
		
		if (!s.getSpawns().contains("Spectator"))
			return;
		
		hub = getLocation(s.getSpawns().getConfigurationSection("Hub"));
	}
	
	public void toSpectator(Player player) {
		
		if (spectator == null) {
			player.sendMessage(pl.prefix + m.getMessage().getString("nohub").replace("&", "§"));
			return;
		}
		
		player.teleport(spectator);
	}
	
	public void loadHub() {	// call in onEnable
		
		if (!s.getSpawns().contains("Hub"))
			return;
		
		hub = getLocation(s.getSpawns().getConfigurationSection("Hub"));
	}
	
	public static void setLocation(ConfigurationSection section, Location value) {
		 
		if (value == null)
			section.set("", null);
		
		if (!section.contains("vector"))
			section.createSection("vector");
		
		section.set("vector", value.toVector());
		
		if (!section.contains("world"))
			section.createSection("world");
		
		section.set("world", value.getWorld().getName());
	}
	
	public static Location getLocation(ConfigurationSection section) {
		
		if (!section.contains("vector") || !section.contains("world"))
			return null;
		
		Vector v = section.getVector("vector");
		World world = Bukkit.getWorld(section.getString("world"));
		return v.toLocation(world);
	}
	
	public void loadSpawns() {	// call in onEnable
		
		if (!s.getSpawns().contains("Spawns")) {
			s.getSpawns().createSection("Spawns");
			s.getSpawns().createSection("Spawns.zombie");
			s.getSpawns().createSection("Spawns.villager");
		}
		
		for (String key : s.getSpawns().getConfigurationSection("Spawns.zombie").getKeys(false))
			zombieSpawns.put(key, getLocation(s.getSpawns().getConfigurationSection("Spawns.zombie." + key)));
		
		for (String key : s.getSpawns().getConfigurationSection("Spawns.villager").getKeys(false))
			villSpawns.put(key, getLocation(s.getSpawns().getConfigurationSection("Spawns.villager." + key)));
	}
	
	public void setSpawn(PlayerID id, String name, Location loc) {
		
		switch(id) {
		
		case VILLAGER:
			villSpawns.put(name, loc);
			if (!s.getSpawns().contains("Spawns.villager." + name))
				s.getSpawns().createSection("Spawns.villager." + name);
			setLocation(s.getSpawns().getConfigurationSection("Spawns.villager." + name), loc);
			break;
		case ZOMBIE:
			zombieSpawns.put(name, loc);
			if (!s.getSpawns().contains("Spawns.zombie." + name))
				s.getSpawns().createSection("Spawns.zombie." + name);
			setLocation(s.getSpawns().getConfigurationSection("Spawns.zombie." + name), loc);
			break;
		default:
			break;
		}
		
		s.saveSpawnsConfig();
	}
	
	public void removeSpawn(PlayerID id, String name) {
		
		switch(id) {
		
		case VILLAGER:
			villSpawns.remove(name);
			s.getSpawns().set("Spawns.villager." + name, null);
			break;
		case ZOMBIE:
			zombieSpawns.remove(name);
			s.getSpawns().set("Spawns.zombie." + name, null);
			break;
		default:
			break;
		}
		
		s.saveSpawnsConfig();
	}
	
	public void toHub(Player player) {
		
		if (hub == null) {
			player.sendMessage(pl.prefix + m.getMessage().getString("nohub").replace("&", "§"));
			return;
		}
		
		player.teleport(hub);
	}
	
	public void toRandomSpawn(Player player, PlayerID id) {
		
		if (id == null)
			return;
		
		int rand = 0;
		List<Location> locs = null;
		
		switch(id) {
		
		case VILLAGER:
			rand = Math.randomInt(0, villSpawns.size());
			locs = new ArrayList<Location>(villSpawns.values());
			player.teleport(locs.get(rand));
			break;
		case ZOMBIE:
			rand = Math.randomInt(0, zombieSpawns.size());
			locs = new ArrayList<Location>(zombieSpawns.values());
			player.teleport(locs.get(rand));
			break;
		default:
			break;
		}
	}
	
	public boolean canSpawn() {		// check if one spawn for each type exists
		
		return villSpawns.size() > 0 && zombieSpawns.size() > 0;
	}
}
