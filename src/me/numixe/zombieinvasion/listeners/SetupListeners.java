package me.numixe.zombieinvasion.listeners;


import me.numixe.zombieinvasion.ZombieInvasion;
import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.Lobby;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SetupListeners implements Listener {
	
	private ZombieInvasion plugin;	// pointer to plugin
	
	public SetupListeners(ZombieInvasion plugin) {
		
		this.plugin = plugin;
	}
	
	public void register() {
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void unregister() {
		
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		plugin.getTeleportManager().toHub(e.getPlayer());
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {	// case a player exits or disconnects
		
		Player p = e.getPlayer();
		
		if (plugin.getLobby().getPlayerID(p) == null)	// check if it's relative to the game
			return;
		
		Disguiser.setNull(p);
		plugin.getLobby().removePlayer(p);
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		  
		if (!e.getLine(0).equalsIgnoreCase("[ZombieInvasion]") || !e.getLine(1).equalsIgnoreCase("lobby"))
			return;
	    		
		e.setLine(0, "\u00A71\u00A7l[Invasion]");
	  	e.setLine(1, "");
	  	e.setLine(2, "\u00A76\u00A7l» \u00A72\u00A7lJoin \u00A76\u00A7l«");   
	}
	  
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		  
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		  
		Player p = event.getPlayer();
	  	Block block = event.getClickedBlock();
	  	  
	  	if (!(block.getState() instanceof Sign))
	  		return;
	  		  
	  	Sign sign = (Sign) block.getState();
	  		
	  	if (!sign.getLine(0).equalsIgnoreCase("\u00A71\u00A7l[Invasion]") || !(sign.getLine(2).equalsIgnoreCase("\u00A76\u00A7l» \u00A72\u00A7lJoin \u00A76\u00A7l«")))
	  		return;
				
	  	switch (plugin.getLobby().addPlayer(p)) {
				
	  		case Lobby.FAIL_NAME:
	  			p.sendMessage("\u00A76ZombieInvasion> " + "\u00A7cSei gia' entrato in game!");
	  			break;
	  		case Lobby.FAIL_FULL:
				p.sendMessage("\u00A76ZombieInvasion> " + "\u00A7cLa lobby e' piena!");
				break;
	  		default:
				p.sendMessage("\u00A76ZombieInvasion> " + "\u00A7aSei entrato in game!");
				break;
	  	}
	}    
}
