package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*; // import all variable

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		Disguiser.setNull(p);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player p = e.getEntity();
		if (!(p instanceof Player))
			return;
		
		PlayerID id = plugin.lobby.getPlayerID(p);
		
		if (id == null)
			return;
		
		switch (id) {
		
		case VILLAGER:
			plugin.lobby.setPlayerID(p, PlayerID.ZOMBIE);
			Disguiser.setZombie(p);
			break;
		case ZOMBIE:
			plugin.lobby.setPlayerID(p, PlayerID.NONE);
			Disguiser.setNull(p);
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage("Sei morto");
			break;
		default:
			// do nothing
			break;
		}
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
				
				switch (plugin.lobby.addPlayer(p)) {
				
				case Lobby.FAIL_NAME:
					p.sendMessage("§6ZombieInvasion> " + "§cSei gia' entrato in game!");
					break;
				case Lobby.FAIL_FULL:
					p.sendMessage("§6ZombieInvasion> " + "§cLa lobby e' piena!");
					break;
				default:
					p.sendMessage("§6ZombieInvasion> " + "§aSei entrato in game!");
					break;
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
