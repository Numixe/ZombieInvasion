package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*; // import all variable

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListeners implements Listener {
	
	public ArrayList<Player> ingame = new ArrayList<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (ingame.contains(p)) {
		ingame.remove(p);
		}
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
	
	
	  @EventHandler
	  public void onSignCreate(SignChangeEvent e) {
	    //Player p = e.getPlayer();
	    if (e.getLine(0).equalsIgnoreCase("[ZombieInvasion]")) {
	    	if (e.getLine(1).equalsIgnoreCase("lobby")) {
	    	  e.setLine(0, "§1§l[Invasion]");
	  	      e.setLine(1, "");
	  	      e.setLine(2, "§6§l» §2§lJoin §6§l«");
	  	    //e.setLine(3, Player dentro + "/" + MAX_LOBBY);
	    	}    
	    }
	  }
	  
	  @EventHandler
	  public void onPlayerInteract(PlayerInteractEvent event) {
      if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK))
				return;
      Player p = event.getPlayer();
	  Block block = event.getClickedBlock();
	  if (block.getState() instanceof Sign) {
		Sign sign = (Sign) block.getState();
			if (sign.getLine(0).equalsIgnoreCase("§1§l[Invasion]") && (sign.getLine(2).equalsIgnoreCase("§6§l» §2§lJoin §6§l«"))) {		
				if (ingame.contains(p)) {
					p.sendMessage("§6ZombieInvasion> " + "§cSei già entrato in game!");
				} else {
				p.sendMessage("§6ZombieInvasion> " + "§aSei entrato in game!");
				ingame.add(p);
				plugin.lobby.addPlayer(p);
			    }
			}			
		}
	  }
	  
	  public void sendbar(Player p, int i) {
		  if (p.hasPermission("ZombieInvasion.actionbar")) {
		  actionbar.message = Timer.square + String.valueOf(i);
		  actionbar.sendMessage(p);
	  }
	  }  
}
