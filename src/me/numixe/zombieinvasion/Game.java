package me.numixe.zombieinvasion;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class Game {
	
	private boolean running;
	
	public Game() {
		
		running = false;
	}
	
	public void start() {
		
		if (running)
			return;
		
		plugin.lobby.randomAssignID();	
		
		for (Player p :  plugin.lobby.getPlayers()) {	
			
			updatePlayerForm(p);
			plugin.scoreboard.showBoard(p);
			plugin.teleport.toRandomSpawn(p);
		}
		
		plugin.scoreboard.refresh();
		running = true;
	}
	
	public boolean isRunning() {
		
		return running;
	}
	
	public void stop() {
		
		if (!running)
			return;
		
		for (Player p :  plugin.lobby.getPlayers()) {	
			
			plugin.lobby.setPlayerID(p, PlayerID.NONE);
			updatePlayerForm(p);
			plugin.scoreboard.hideBoard(p);
			plugin.teleport.toHub(p);
		}
		
		plugin.lobby.clear();
		plugin.scoreboard.refresh();
		running = false;
	}
	
	public void onDeathPlayer(Player player) {
		
		PlayerID id = plugin.lobby.getPlayerID(player);
		
		if (id == null) {
			return;
		}
		
		switch (id) {
		
		case VILLAGER:
			plugin.lobby.setPlayerID(player, PlayerID.ZOMBIE);
			Disguiser.setZombie(player);
			break;
		case ZOMBIE:
			plugin.lobby.setPlayerID(player, PlayerID.NONE);
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
		
		PlayerID id = plugin.lobby.getPlayerID(player);
		
		if (id == null)
			Disguiser.setNull(player);
		
		switch(id) {
			
		case VILLAGER:
			Disguiser.setVillager(player);
			break;
		case ZOMBIE:
			Disguiser.setZombie(player);
			break;
		case NONE:
			Disguiser.setNull(player);
		default:
			break;
			}
		}
	}
