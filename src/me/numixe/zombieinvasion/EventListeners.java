package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*; // import all variable

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListeners implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.setScoreboard(scoreboard.getKillBoard());	
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (p instanceof Player) {
		Disguiser.api.undisguiseToAll(p);
		p.sendMessage("Sei morto");
		
	     } else { return; }
	  }
	
	
	public void setSpawn(Player p) {
	if (p instanceof Player) {
	 if (p.hasPermission("ZombieInvasion.setspawn")) {
		plugin.getConfig().set("Spawn.world", p.getLocation().getWorld().getName());
        plugin.getConfig().set("Spawn.x", p.getLocation().getX());
        plugin.getConfig().set("Spawn.y", p.getLocation().getY());
        plugin.getConfig().set("Spawn.z", p.getLocation().getZ());
        plugin.saveConfig();
        p.sendMessage("§6ZombieInvasion> " + "§7Spawn settato con §asuccesso§7!");
        return;
		  }
		  }
	}
	
	public void getSpawn(Player p) {
	if (p instanceof Player) {
        if (plugin.getConfig().getConfigurationSection("Spawn") == null) {
                p.sendMessage("§6ZombieInvasion> " + "§cLo spawn non e' settato!");
                return;
        }
        World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Spawn.world"));
        double x = plugin.getConfig().getDouble("Spawn.x");
        double y = plugin.getConfig().getDouble("Spawn.y");
        double z = plugin.getConfig().getDouble("Spawn.z");
        p.teleport(new Location(w, x, y, z));
}
	  }
}
