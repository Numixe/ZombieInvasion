package me.numixe.zombieinvasion.listeners;

import java.util.Map;

import me.numixe.zombieinvasion.ZombieInvasion;
import me.numixe.zombieinvasion.entities.PlayerID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListeners implements Listener {

	private ZombieInvasion plugin;	// pointer to plugin
	
	public GameListeners(ZombieInvasion plugin) {
		
		this.plugin = plugin;
	}
	
	public void register() {
		
		System.out.println("Registrazione eventi di gioco");
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void unregister() {
		
		System.out.println("Annullamento eventi di gioco");
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {	// case a player exits or disconnects
		
		Player p = e.getPlayer();
		
		if (plugin.getLobby().getPlayerID(p) == null)	// check if it's relative to the game
			return;
		
		// update scoreboard
		Map<PlayerID, Integer> count = plugin.getLobby().getCount();
				
		plugin.getScoreboard().refresh(count);
		
		// control if someone win
		plugin.getGame().winControl(count);
		System.out.println(p.getName() + " ha abbandonato la partita");
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player p = e.getEntity();
		
		e.getDrops().clear();
		plugin.getGame().onDeathPlayer(p);
		
		// update scoreboard
		Map<PlayerID, Integer> count = plugin.getLobby().getCount();
						
		plugin.getScoreboard().refresh(count);
		
		// control if someone win
		plugin.getGame().winControl(count);
	 }
}

