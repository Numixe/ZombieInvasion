package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardAPI {
	
	private Scoreboard board;
	private Objective obj;
	private Score villagers, zombies;
	
	public static final String TITLE = "�d�lZombie Invasion";
	public static final String VillagerKey = "�aVillagers";
	public static final String ZombieKey = "�cZombies";
	
	public ScoreboardAPI() { // funzione da caricare all'avvio del server
		
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("zombieinvasion", "dummy");
		obj.setDisplayName(TITLE);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		villagers = obj.getScore(VillagerKey);
		zombies = obj.getScore(ZombieKey);
	}
	
	public Scoreboard getKillBoard() {
		return board;
	}
	
	public void refresh() { // 
		
		Map<PlayerID, Integer> count = plugin.lobby.getCount();
		
		villagers.setScore(count.get(PlayerID.VILLAGER));
		zombies.setScore(count.get(PlayerID.ZOMBIE));
	}
}
