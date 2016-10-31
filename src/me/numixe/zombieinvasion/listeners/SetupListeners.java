package me.numixe.zombieinvasion.listeners;

import me.numixe.zombieinvasion.ZombieInvasion;
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
		
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		  
		if (!e.getLine(0).equalsIgnoreCase("[ZombieInvasion]") || !e.getLine(1).equalsIgnoreCase("lobby"))
			return;
	    		
		e.setLine(0, "�1�l[Invasion]");
	  	e.setLine(1, "");
	  	e.setLine(2, "�6�l� �2�lJoin �6�l�");   
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
	  		
	  	if (!sign.getLine(0).equalsIgnoreCase("�1�l[Invasion]") || !(sign.getLine(2).equalsIgnoreCase("�6�l� �2�lJoin �6�l�")))
	  		return;
				
	  	switch (plugin.getLobby().addPlayer(p)) {
				
	  		case Lobby.FAIL_NAME:
	  			p.sendMessage("�6ZombieInvasion> " + "�cSei gia' entrato in game!");
	  			break;
	  		case Lobby.FAIL_FULL:
				p.sendMessage("�6ZombieInvasion> " + "�cLa lobby e' piena!");
				break;
	  		default:
				p.sendMessage("�6ZombieInvasion> " + "�aSei entrato in game!");
				break;
	  	}
	}    
}
