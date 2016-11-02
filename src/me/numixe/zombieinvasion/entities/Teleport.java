package me.numixe.zombieinvasion.entities;

import me.numixe.zombieinvasion.math.Math;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teleport {

	private Location hub;
	private Map<String, Location> villSpawns, zombieSpawns;	// name, location
	private Plugin plugin;
	
	public Teleport(Plugin plugin) {
		
		this.plugin = plugin;
		this.hub = null;
			
		villSpawns = new HashMap<String, Location>();
		zombieSpawns = new HashMap<String, Location>();
	}
	
	public void setHub(Location loc) {
		
		if (!plugin.getConfig().contains("hub"))
			plugin.getConfig().createSection("hub");
		
		setLocation(plugin.getConfig().getConfigurationSection("hub"), loc);
		hub = loc.clone();
	}
	
	public void loadHub() {	// call in onEnable
		
		if (!plugin.getConfig().contains("hub"))
			return;
		
		hub = getLocation(plugin.getConfig().getConfigurationSection("hub"));
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
		
		if (!plugin.getConfig().contains("spawns")) {
			plugin.getConfig().createSection("spawns");
			plugin.getConfig().createSection("spawns.zombie");
			plugin.getConfig().createSection("spawns.villager");
		}
		
		for (String key : plugin.getConfig().getConfigurationSection("spawns.zombie").getKeys(false))
			zombieSpawns.put(key, getLocation(plugin.getConfig().getConfigurationSection("spawns.zombie." + key)));
		
		for (String key : plugin.getConfig().getConfigurationSection("spawns.villager").getKeys(false))
			villSpawns.put(key, getLocation(plugin.getConfig().getConfigurationSection("spawns.villager." + key)));
	}
	
	public void setSpawn(PlayerID id, String name, Location loc) {
		
		switch(id) {
		
		case VILLAGER:
			villSpawns.put(name, loc);
			if (!plugin.getConfig().contains("spawns.villager." + name))
				plugin.getConfig().createSection("spawns.villager." + name);
			setLocation(plugin.getConfig().getConfigurationSection("spawns.villager." + name), loc);
			break;
		case ZOMBIE:
			zombieSpawns.put(name, loc);
			if (!plugin.getConfig().contains("spawns.zombie." + name))
				plugin.getConfig().createSection("spawns.zombie." + name);
			setLocation(plugin.getConfig().getConfigurationSection("spawns.zombie." + name), loc);
			break;
		default:
			break;
		}
		
		plugin.saveConfig();
	}
	
	public void removeSpawn(PlayerID id, String name) {
		
		switch(id) {
		
		case VILLAGER:
			villSpawns.remove(name);
			plugin.getConfig().set("spawns.villager." + name, null);
			break;
		case ZOMBIE:
			zombieSpawns.remove(name);
			plugin.getConfig().set("spawns.zombie." + name, null);
			break;
		default:
			break;
		}
		
		plugin.saveConfig();
	}
	
	public void toHub(Player player) {
		
		if (hub == null) {
			player.sendMessage("§6ZombieInvasion> §fNon esiste un hub");
			return;
		}
		
		player.teleport(hub);
		player.sendMessage("§6ZombieInvasion> §fSei stato teletrasportato all'hub");
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
			player.sendMessage("§6ZombieInvasion> §fSei stato teletrasportato in uno spawn villager");
			break;
		case ZOMBIE:
			rand = Math.randomInt(0, zombieSpawns.size());
			locs = new ArrayList<Location>(zombieSpawns.values());
			player.teleport(locs.get(rand));
			player.sendMessage("§6ZombieInvasion> §fSei stato teletrasportato in uno spawn zombie");
			break;
		default:
			break;
		}
	}
	
	public boolean canSpawn() {		// check if one spawn for each type exists
		
		return villSpawns.size() > 0 && zombieSpawns.size() > 0;
	}
}
