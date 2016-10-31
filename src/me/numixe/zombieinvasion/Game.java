package me.numixe.zombieinvasion;

import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.PlayerID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Game {
	
	private boolean running;
	private ZombieInvasion plugin;
	
	public Game(ZombieInvasion plugin) {
		
		running = false;
		this.plugin = plugin;
	}
	
	public void start() {
		
		if (running)
			return;
		
		plugin.getLobby().randomAssignID();	
		
		for (Player p :  plugin.getLobby().getPlayers()) {	
			
			updatePlayerForm(p);
			plugin.getScoreboard().showBoard(p);
			plugin.getTeleportManager().toRandomSpawn(p);
		}
		
		plugin.getScoreboard().refresh();
		running = true;
	}
	
	public boolean isRunning() {
		
		return running;
	}
	
	public void stop() {
		
		if (!running)
			return;
		
		for (Player p :  plugin.getLobby().getPlayers()) {	
			
			plugin.getLobby().setPlayerID(p, PlayerID.NONE);
			updatePlayerForm(p);
			plugin.getScoreboard().hideBoard(p);
			plugin.getTeleportManager().toHub(p);
		}
		
		plugin.getLobby().clear();
		plugin.getScoreboard().refresh();
		running = false;
	}
	
	public void onDeathPlayer(Player player) {
		
		PlayerID id = plugin.getLobby().getPlayerID(player);
		
		if (id == null) {
			return;
		}
		
		switch (id) {
		
		case VILLAGER:
			plugin.getLobby().setPlayerID(player, PlayerID.ZOMBIE);
			Disguiser.setZombie(plugin.getActionBar(), player);
			break;
		case ZOMBIE:
			plugin.getLobby().setPlayerID(player, PlayerID.NONE);
			Disguiser.setNull(player);
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("Sei morto");
			break;
		default:
			// do nothing
			break;
		}
	}
	
	public void updatePlayerForm(Player player) {
		
		PlayerID id = plugin.getLobby().getPlayerID(player);
		
		if (id == null)
			Disguiser.setNull(player);
		
		switch(id) {
			
		case VILLAGER:
			Disguiser.setVillager(plugin.getActionBar(), player);
			break;
		case ZOMBIE:
			Disguiser.setZombie(plugin.getActionBar(), player);
			break;
		case NONE:
			Disguiser.setNull(player);
		default:
			break;
			}
		}
	}
