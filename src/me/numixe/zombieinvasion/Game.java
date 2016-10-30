package me.numixe.zombieinvasion;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class Game {
	
	public void start() {
		
		plugin.lobby.randomAssignID();	
		for (Player p :  plugin.lobby.getPlayers()) {		
			updatePlayerForm(p);
			p.setScoreboard(plugin.scoreboard.getKillBoard());
			plugin.scoreboard.refresh();
			plugin.teleport.toRandomSpawn(p);
		}
	}
	
	public void stop() {
		
	}
	
	public void onDeathPlayer(Player player) {
		
		PlayerID id = plugin.lobby.getPlayerID(player);
		
		if (id == null)
			return;
		
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
		
		switch(plugin.lobby.getPlayerID(player)) {
			
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
