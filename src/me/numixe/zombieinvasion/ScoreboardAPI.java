package me.numixe.zombieinvasion;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardAPI {
	
	public Scoreboard board;
	public Objective obj;
	
	public static final String TITLE = "§d§lZombie Invasion";
	public static final String PlayerColor = "§7";
	
	public ScoreboardAPI() { // funzione da caricare all'avvio del server
		
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("zombieinvasion", "dummy");
		obj.setDisplayName(TITLE);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public Scoreboard getKillBoard() {
		return board;
	}
	
	public void refresh() { // funzione che (TEORICAMENTE) dovrebbe settare i players con le kill °-°
			
		// lista e itera i player presenti nel config
		
		
	}
}
