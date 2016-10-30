package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardAPI {
	
	public Scoreboard board;
	public Objective obj;
	
	public static final String TITLE = "§d§lZombie Invasion";
	public static final String VillagerColor = "§a";
	public static final String ZombieColor = "§c";
	
	public ScoreboardAPI() { // funzione da caricare all'avvio del server
		
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("zombieinvasion", "dummy");
		obj.setDisplayName(TITLE);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public Scoreboard getKillBoard() {
		return board;
	}
	
	@SuppressWarnings("unused")
	public void refresh() { // 
		int villager = 0, zombie = 0;
		for (String name : plugin.lobby.getPlayerNames()) {
			switch(plugin.lobby.getPlayerID(name)) {
			case VILLAGER:
				villager++;
				break;
			case ZOMBIE:
				zombie++;
				break;
			default:
				break;
			
			}
		}
		
		
		
	}
}
