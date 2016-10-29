package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.api;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListeners implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		//Player p = e.getPlayer();
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();	
		api.undisguiseToAll(p);
		p.sendMessage("Sei morto");
		
	}
	
}
