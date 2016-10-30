package me.numixe.zombieinvasion;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class Hub {

	private static Location hub;
	
	public static void initHub() {	// call in onEnable
		
		if (!plugin.getConfig().contains("hub")) {
			
			plugin.getConfig().createSection("hub");
			plugin.getConfig().createSection("hub.vec");
			plugin.getConfig().createSection("hub.world");
			hub = new Location(Bukkit.getWorld("world"), 0, 0, 0);
			plugin.getConfig().set("hub.vec", hub.toVector());
			plugin.getConfig().set("hub.world", hub.getWorld().getName());
			
		} else {
			
			Vector vec = plugin.getConfig().getVector("hub.vec");
			World world = Bukkit.getWorld(plugin.getConfig().getString("hub.world"));
			hub = new Location(world, vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
		}
	}
	
	public static void teleport(Player player) {
		
		player.teleport(hub);
	}
}
