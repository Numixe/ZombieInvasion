package me.numixe.zombieinvasion.listeners;


import me.numixe.zombieinvasion.ZombieInvasion;
import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.Lobby;
import me.numixe.zombieinvasion.timing.StartTimer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import static me.numixe.zombieinvasion.ZombieInvasion.*;
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
		
		Disguiser.setNull(p, plugin.getScoreboard());
		plugin.getLobby().removePlayer(p);
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		  
		if (e.getLine(0).equalsIgnoreCase("[ZombieInvasion]") && e.getLine(1).equalsIgnoreCase("join")) {    		
			e.setLine(0, "\u00A71\u00A7l[ZombieInv]");
		  	e.setLine(1, "");
		  	e.setLine(2, "\u00A76\u00A7l\u00BB \u00A72\u00A7lJoin \u00A76\u00A7l\u00AB");
		}
		if (e.getLine(0).equalsIgnoreCase("[ZombieInvasion]") && e.getLine(1).equalsIgnoreCase("left")) {
				e.setLine(0, "\u00A71\u00A7l[ZombieInv]");
			  	e.setLine(1, "");
			  	e.setLine(2, "\u00A76\u00A7l\u00BB \u00A74\u00A7lLeft \u00A76\u00A7l\u00AB");
			  	e.setLine(3, " ");
			}
	  	
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
	  		
	  	if (sign.getLine(0).equalsIgnoreCase("\u00A71\u00A7l[ZombieInv]") 
	  			&& (sign.getLine(2).equalsIgnoreCase("\u00A76\u00A7l\u00BB \u00A72\u00A7lJoin \u00A76\u00A7l\u00AB"))) {
				
	  	switch (plugin.getLobby().addPlayer(p)) {
				
	  		case Lobby.FAIL_NAME:
	  			p.sendMessage(pl.prefix + m.getMessage().getString("alreadyJoin").replace("&", "§"));
	  			p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 10, 10);
	  			break;
	  		case Lobby.FAIL_FULL:
	  			p.sendMessage(pl.prefix + m.getMessage().getString("full").replace("&", "§"));
				p.playSound(p.getLocation(), Sound.CREEPER_DEATH, 10, 10);
				break;
	  		default:
	  			p.sendMessage(pl.prefix + m.getMessage().getString("join").replace("&", "§"));
				p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 10, 10);
				p.playSound(p.getLocation(), Sound.FIREWORK_TWINKLE2, 20, 20);
				break;
	  	}
	  	
	  	
	  	} else if (sign.getLine(0).equalsIgnoreCase("\u00A71\u00A7l[ZombieInv]") 
	  			&& (sign.getLine(2).equalsIgnoreCase("\u00A76\u00A7l\u00BB \u00A74\u00A7lLeft \u00A76\u00A7l\u00AB"))) { 		
	  		plugin.getLobby().removePlayer(p);
	  	}
	  	
	  	if (plugin.getLobby().isFull()) {
	  		
	  		new StartTimer(plugin);
	  	}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!event.getPlayer().isOp()) {
		event.setCancelled(true);
		event.getPlayer().sendMessage("\u00a7c\u00a7lHEY! \u00a77You cannot break a block!");
	 }
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!event.getPlayer().isOp()) {
		event.setCancelled(true);
		event.getPlayer().sendMessage("\u00a7c\u00a7lHEY! \u00a77You cannot place a block!");
	 }
}
		
}
