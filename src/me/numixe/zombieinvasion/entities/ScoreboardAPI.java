package me.numixe.zombieinvasion.entities;

import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboardAPI {
	
	private Scoreboard board;
	private Objective obj;
	private Score villagers, zombies;
	
	public ScoreboardManager manager = Bukkit.getScoreboardManager();
	public Team vill;
	public Team zomb;
	
	public static final String TITLE = "\u00A7d\u00A7lZombie Invasion";
	public static final String VillagerKey = "\u00A7aVillagers";
	public static final String ZombieKey = "\u00A7cZombies";
	
	public ScoreboardAPI() { // funzione da caricare all'avvio del server
		
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("zombieinvasion", "dummy");
		obj.setDisplayName(TITLE);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		villagers = obj.getScore(VillagerKey);
		zombies = obj.getScore(ZombieKey);
		
		/** Team **/		
		
		vill = board.registerNewTeam("villager");
		zomb = board.registerNewTeam("zombie");
		vill.setAllowFriendlyFire(false);
		zomb.setAllowFriendlyFire(false);
		/**********/
	}
	
	public void showBoard(Player player) {
		
		player.setScoreboard(board);
	}
	
	public void hideBoard(Player player) {
		
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	public Scoreboard getKillBoard() {
		
		return board;
	}
	
	public void refresh(Map<PlayerID, Integer> count) {		// questa funzione aggiorna automaticamente TUTTI i player
		
		villagers.setScore(count.get(PlayerID.VILLAGER));	
		zombies.setScore(count.get(PlayerID.ZOMBIE));
	}
}
