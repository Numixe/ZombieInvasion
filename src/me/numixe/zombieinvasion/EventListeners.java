package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*; // import all variable

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
		
		plugin.game.onDeathPlayer(p);
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
		  plugin.actionbar.message = Timer.square + String.valueOf(i);
		  plugin.actionbar.sendMessage(p);
	  }
	  }
	  
}
