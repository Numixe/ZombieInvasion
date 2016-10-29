package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*; // import all variable

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
}
